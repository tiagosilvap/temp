package com.hotmart.api.subscription.treta.filemanager;

import com.hotmart.api.subscription.treta.vo.SubscriptionData;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Service
public class SubscriptionDataCsvReader {
    
    public List<SubscriptionData> readCsv(String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            return new CsvToBeanBuilder<SubscriptionData>(reader)
                    .withType(SubscriptionData.class)
                    .withSeparator(';')  // Delimitador é ponto e vírgula
                    .build()
                    .parse();
        }
    }
}

