package com.hotmart.api.subscription.ddd.infrastructure.api.controllers;

import com.hotmart.api.subscription.ddd.application.product.create.CreateProductCommand;
import com.hotmart.api.subscription.ddd.application.product.create.CreateProductOutput;
import com.hotmart.api.subscription.ddd.application.product.create.CreateProductUseCase;
import com.hotmart.api.subscription.ddd.application.product.delete.DeleteProductCommand;
import com.hotmart.api.subscription.ddd.application.product.delete.DeleteProductUseCase;
import com.hotmart.api.subscription.ddd.application.product.retrieve.get.GetProductCommand;
import com.hotmart.api.subscription.ddd.application.product.retrieve.get.GetProductOutput;
import com.hotmart.api.subscription.ddd.application.product.retrieve.get.GetProductUseCase;
import com.hotmart.api.subscription.ddd.application.product.retrieve.list.RetrieveListProductOutput;
import com.hotmart.api.subscription.ddd.application.product.retrieve.list.RetrieveListProductUseCase;
import com.hotmart.api.subscription.ddd.application.product.update.UpdateProductCommand;
import com.hotmart.api.subscription.ddd.application.product.update.UpdateProductOutput;
import com.hotmart.api.subscription.ddd.application.product.update.UpdateProductUseCase;
import com.hotmart.api.subscription.ddd.infrastructure.product.model.UpdateProductRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    
    private final CreateProductUseCase createProductUseCase;
    private final RetrieveListProductUseCase retrieveListProductUseCase;
    private final GetProductUseCase getProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    
    private final DeleteProductUseCase deleteProductUseCase;
    
    public ProductController(final CreateProductUseCase createProductUseCase,
                             final RetrieveListProductUseCase retrieveListProductUseCase,
                             final GetProductUseCase getProductUseCase,
                             UpdateProductUseCase updateProductUseCase,
                             DeleteProductUseCase deleteProductUseCase) {
        this.createProductUseCase = createProductUseCase;
        this.retrieveListProductUseCase = retrieveListProductUseCase;
        this.getProductUseCase = getProductUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
    }
    
    @GetMapping
    public List<RetrieveListProductOutput> list() {
        return retrieveListProductUseCase.execute();
    }
    
    @PostMapping
    public CreateProductOutput add(@RequestBody CreateProductCommand createProductCommand) {
        return createProductUseCase.execute(createProductCommand);
    }
    
    @GetMapping("/{id}")
    public GetProductOutput get(@PathVariable String id) {
        return getProductUseCase.execute(GetProductCommand.from(id));
    }
    
    @PutMapping("/{id}")
    public UpdateProductOutput update(@PathVariable String id,
                                      @RequestBody UpdateProductRequest request) {
        return updateProductUseCase.execute(UpdateProductCommand.with(id, request.name(), request.price()));
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        deleteProductUseCase.execute(DeleteProductCommand.with(id));
    }
}
