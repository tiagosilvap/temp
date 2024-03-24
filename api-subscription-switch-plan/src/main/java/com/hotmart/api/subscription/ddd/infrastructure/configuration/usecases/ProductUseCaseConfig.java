package com.hotmart.api.subscription.ddd.infrastructure.configuration.usecases;

import com.hotmart.api.subscription.ddd.application.product.create.CreateProductUseCase;
import com.hotmart.api.subscription.ddd.application.product.create.CreateProductUseCaseImpl;
import com.hotmart.api.subscription.ddd.application.product.delete.DeleteProductUseCase;
import com.hotmart.api.subscription.ddd.application.product.delete.DeleteProductUseCaseImpl;
import com.hotmart.api.subscription.ddd.application.product.retrieve.get.GetProductUseCase;
import com.hotmart.api.subscription.ddd.application.product.retrieve.get.GetProductUseCaseImpl;
import com.hotmart.api.subscription.ddd.application.product.retrieve.list.RetrieveListProductUseCase;
import com.hotmart.api.subscription.ddd.application.product.retrieve.list.RetrieveListProductUseCaseImpl;
import com.hotmart.api.subscription.ddd.application.product.update.UpdateProductUseCase;
import com.hotmart.api.subscription.ddd.application.product.update.UpdateProductUseCaseImpl;
import com.hotmart.api.subscription.ddd.domain.product.ProductGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class ProductUseCaseConfig {

    private final ProductGateway productGateway;

    public ProductUseCaseConfig(final ProductGateway productGateway) {
        this.productGateway = Objects.requireNonNull(productGateway);
    }

    @Bean
    public CreateProductUseCase createProductUseCase() {
        return new CreateProductUseCaseImpl(productGateway);
    }
    
    @Bean
    public RetrieveListProductUseCase retrieveListProductUseCase() {
        return new RetrieveListProductUseCaseImpl(productGateway);
    }
    
    @Bean
    public GetProductUseCase getProductUseCase() {
        return new GetProductUseCaseImpl(productGateway);
    }
    
    @Bean
    public UpdateProductUseCase updateProductUseCase() {
        return new UpdateProductUseCaseImpl(productGateway);
    }
    
    @Bean
    public DeleteProductUseCase deleteProductUseCase() {
        return new DeleteProductUseCaseImpl(productGateway);
    }
}
