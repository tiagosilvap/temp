package com.hotmart.api.subscription.ddd.application.product.delete;

import com.hotmart.api.subscription.ddd.domain.product.ProductGateway;

public class DeleteProductUseCaseImpl extends DeleteProductUseCase {
    
    private final ProductGateway productGateway;
    
    public DeleteProductUseCaseImpl(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }
    
    
    @Override
    public void execute(DeleteProductCommand deleteProductCommand) {
        this.productGateway.deleteById(deleteProductCommand.id());
    }
}
