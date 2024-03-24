package com.hotmart.api.subscription.treta.filemanager;

import com.hotmart.api.subscription.treta.vo.SubscriptionData;
import com.hotmart.api.subscription.treta.vo.SubscriptionResponseV2;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Service
public class SubscriptionResponseV2CsvReader {
    
    public List<SubscriptionResponseV2> readCsv(String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            return new CsvToBeanBuilder<SubscriptionResponseV2>(reader)
                    .withType(SubscriptionResponseV2.class)
                    .withSeparator(',')  // Delimitador é ponto e vírgula
//                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                    .parse();
        }
    }
}

