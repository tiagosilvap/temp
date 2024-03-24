package com.hotmart.api.subscription.treta.filemanager;

import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

@Service
public class CsvWriter<T> {
    
    public void writeObjectsToCsv(List<T> objects, String filePath) throws IOException, IllegalAccessException {
        if (objects == null || objects.isEmpty()) {
            return;
        }
        
        // Obter a classe do primeiro objeto para obter os campos
        Class<?> clazz = objects.get(0).getClass();
        Field[] fields = clazz.getDeclaredFields();  // Obter todos os campos da classe
        
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            // Escrever cabeçalho (nomes dos campos)
            String[] header = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                header[i] = fields[i].getName();
            }
            writer.writeNext(header);
            
            // Escrever dados dos objetos
            for (T obj : objects) {
                String[] data = new String[fields.length];
                for (int i = 0; i < fields.length; i++) {
                    fields[i].setAccessible(true);  // Acessar campo privado, se necessário
                    Object value = fields[i].get(obj);
                    data[i] = value != null ? value.toString() : "";  // Converter valor em string ou vazio
                }
                writer.writeNext(data);
            }
        }
    }
}

