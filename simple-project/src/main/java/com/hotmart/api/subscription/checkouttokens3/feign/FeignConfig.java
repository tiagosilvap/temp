package com.hotmart.api.subscription.checkouttokens3.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.Decoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.stream.Collectors;

@Configuration
public class FeignConfig {

    @Bean
    public Decoder ndjsonDecoder() {
        return new Decoder() {
            private final ObjectMapper objectMapper = new ObjectMapper();

            @Override
            public Object decode(Response response, Type type) throws IOException {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.body().asInputStream()))) {
                    String responseBody = reader.lines().collect(Collectors.joining("\n"));

                    // Lógica para lidar com múltiplas linhas NDJSON ou converter cada linha em um objeto
                    return objectMapper.readValue(responseBody, objectMapper.constructType(type));
                }
            }
        };
    }
}
