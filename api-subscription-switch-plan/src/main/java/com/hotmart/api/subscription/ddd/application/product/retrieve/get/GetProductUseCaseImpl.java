package com.hotmart.api.subscription.ddd.application.product.retrieve.get;

import com.hotmart.api.subscription.ddd.domain.product.ProductGateway;

public class GetProductUseCaseImpl extends GetProductUseCase {
    
    private final ProductGateway productGateway;
    
    public GetProductUseCaseImpl(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }
    
    @Override
    public GetProductOutput execute(GetProductCommand getProductCommand) {
        return productGateway.findById(getProductCommand.id())
                .map(GetProductOutput::from)
                .orElseThrow(() -> new RuntimeException("Product Not found"));
    }
}
