package com.hotmart.api.subscription.treta.filemanager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class FileWriterService {
    
    public void writeToConsoleAndFile(List<?> objects, String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filePath))) {
            for (Object obj : objects) {
                String line =  obj instanceof String
                        ? (String) obj
                        : objectMapper.writeValueAsString(obj);
                System.out.println(line);
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
