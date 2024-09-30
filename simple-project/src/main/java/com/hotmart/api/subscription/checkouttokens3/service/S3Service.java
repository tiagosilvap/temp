package com.hotmart.api.subscription.checkouttokens3.service;

import com.hotmart.api.subscription.checkouttokens3.feign.TokenResponse;
import com.hotmart.api.subscription.checkouttokens3.feign.TransactionResponse;
import com.hotmart.api.subscription.checkouttokens3.utils.JsonReader;
import com.hotmart.api.subscription.checkouttokens3.utils.TokenUtils;
import com.hotmart.api.subscription.checkouttokens3.vo.TransactionVO;
import com.hotmart.api.subscription.infraestructure.db2.entity.mkt.PurchaseMkt;
import com.hotmart.api.subscription.infraestructure.db2.repository.PurchaseMktRepository;
import com.hotmart.api.subscription.infraestructure.db2.repository.hp.TransactionRepository;
import com.hotmart.api.subscription.infraestructure.db2.repository.hp.TransactionRepositoryCustom;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class S3Service {
    
    public static final String CHECKOUT_4_TOKEN_PREFIX = "Ckt4TokenV1:";
    public static final String DOWNLOAD_FILE_PATH = "/Users/tiago.pereira/Desktop/csv/checkout_token";
    public static final String BUCKET_NAME = "checkout-token-fallback";
    
    private final AstroboxService astroboxService;
    private final S3Client s3Client;
    private final TokenUtils tokenUtils;
    private final PurchaseMktRepository purchaseMktRepository;
    private final JsonReader jsonReader;
    private final TransactionRepository transactionRepository;
    private final TransactionRepositoryCustom transactionRepositoryCustom;
    
    public S3Service(AstroboxService astroboxService,
                     S3Client s3Client,
                     TokenUtils tokenUtils,
                     PurchaseMktRepository purchaseMktRepository, JsonReader jsonReader, TransactionRepository transactionRepository, TransactionRepositoryCustom transactionRepositoryCustom) {
        this.astroboxService = astroboxService;
        this.s3Client = s3Client;
        this.tokenUtils = tokenUtils;
        this.purchaseMktRepository = purchaseMktRepository;
        this.jsonReader = jsonReader;
        this.transactionRepository = transactionRepository;
        this.transactionRepositoryCustom = transactionRepositoryCustom;
    }
    
    // Listar arquivos em um bucket
    public void listFilesInBucket(String bucketName) {
        ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();
        
        ListObjectsV2Response listObjectsV2Response = s3Client.listObjectsV2(listObjectsV2Request);
        
        for (S3Object object : listObjectsV2Response.contents()) {
            System.out.println("Arquivo: " + object.key() + " (tamanho: " + object.size() + ")");
        }
    }
    
    public List<TransactionResponse> downloadFile(List<String> transactions) {
        var response = new ArrayList<TransactionResponse>();
        if(CollectionUtils.isNotEmpty(transactions)) {
            transactions.forEach(t ->
                    response.add(new TransactionResponse(t, downloadFile(t)))
            );
        }
        return response;
    }
    
    public void compareTransactionValueWithLoad(List<String> transactions) {
        var response = new ArrayList<TransactionResponse>();
        if(CollectionUtils.isNotEmpty(transactions)) {
            transactions.forEach(t -> {
                String checkoutToken = downloadFile(t);
                var details = transactionRepositoryCustom.getDetailsByTransaction(t);
                BigDecimal loadValue = jsonReader.readerToken(checkoutToken, details);
                
                if(loadValue != null && loadValue.compareTo(details.getTransactionValue()) == 0) {
                    //chamar hotpay
                }
            });
        }
    }
    
    public String downloadFile(String transaction) {
        var key = getTokenByTransaction(transaction);
        if(key != null) {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(key.getToken())
                    .build();
            
//            downloadObject(getObjectRequest, transaction);
            
            try (ResponseInputStream<GetObjectResponse> responseInputStream = s3Client.getObject(getObjectRequest)) {
                String content = readStream(responseInputStream);
                return tokenUtils.unzipToken(content);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao baixar arquivo do S3: " + e.getMessage(), e);
            }
        }
        return null;
    }
    
    private TokenResponse getTokenByTransaction(String transaction) {
        return astroboxService.getCheckoutTokenByTransaction(transaction);
    }
    
    private PurchaseMkt getPurchaseMkt(String transaction) {
        try {
            PurchaseMkt purchase = purchaseMktRepository.findByTransaction(transaction);
            return purchase;
        } catch (Exception e) {
            return null;
        }
    }
    
    private String readStream(InputStream inputStream) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler o stream: " + e.getMessage(), e);
        }
        return content.toString();
    }
    
    private void downloadObject(GetObjectRequest getObjectRequest, String count) {
        s3Client.getObject(getObjectRequest, Paths.get(DOWNLOAD_FILE_PATH + "_" + count));
    }
    
    public boolean isCheckout4Token(String checkoutTokenKey) {
        try {
            return StringUtils.isNotBlank(checkoutTokenKey) &&
                    checkoutTokenKey.startsWith(CHECKOUT_4_TOKEN_PREFIX);
        } catch (Exception e) {
            System.out.println("Erro checking if it is checkout token 4 " + e);
        }
        
        return false;
    }
}
