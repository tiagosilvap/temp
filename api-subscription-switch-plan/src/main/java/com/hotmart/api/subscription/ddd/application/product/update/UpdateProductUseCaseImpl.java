package com.hotmart.api.subscription.ddd.application.product.update;

import com.hotmart.api.subscription.ddd.domain.product.ProductGateway;

public class UpdateProductUseCaseImpl extends UpdateProductUseCase {
    
    private final ProductGateway productGateway;
    
    public UpdateProductUseCaseImpl(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }
    
    @Override
    public UpdateProductOutput execute(UpdateProductCommand productCommand) {
        var product = this.productGateway.findById(productCommand.id())
                .orElseThrow(() -> new RuntimeException("Error when updating"));
        
        product.update(productCommand.name(), productCommand.price());
        
        return UpdateProductOutput.from(productGateway.update(product));
    }
}
