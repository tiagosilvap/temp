package com.hotmart.api.subscription.ddd.application.product.retrieve.list;

import com.hotmart.api.subscription.ddd.domain.product.ProductGateway;

import java.util.List;
import java.util.stream.Collectors;

public class RetrieveListProductUseCaseImpl extends RetrieveListProductUseCase {
    
    private final ProductGateway productGateway;
    
    public RetrieveListProductUseCaseImpl(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }
    
    @Override
    public List<RetrieveListProductOutput> execute() {
        return productGateway.findAll().stream()
                .map(RetrieveListProductOutput::from)
                .collect(Collectors.toList());
    }
}
