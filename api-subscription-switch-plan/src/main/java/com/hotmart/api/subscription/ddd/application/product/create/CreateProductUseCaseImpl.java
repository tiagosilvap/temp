package com.hotmart.api.subscription.ddd.application.product.create;

import com.hotmart.api.subscription.ddd.domain.product.Product;
import com.hotmart.api.subscription.ddd.domain.product.ProductGateway;

public class CreateProductUseCaseImpl extends CreateProductUseCase {
    
    private final ProductGateway productGateway;
    
    public CreateProductUseCaseImpl(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }
    
    @Override
    public CreateProductOutput execute(CreateProductCommand createProductCommand) {
        var product = Product.newProduct(createProductCommand.name(), createProductCommand.price());
        return CreateProductOutput.from(productGateway.save(product));
    }
}
