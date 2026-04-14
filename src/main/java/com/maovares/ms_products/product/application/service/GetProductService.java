package com.maovares.ms_products.product.application.service;

import org.springframework.stereotype.Service;

import com.maovares.ms_products.product.application.port.in.GetProductQuery;
import com.maovares.ms_products.product.application.port.out.ProductRepository;
import com.maovares.ms_products.product.domain.exception.ProductNotFoundException;
import com.maovares.ms_products.product.domain.model.Product;

@Service
public class GetProductService implements GetProductQuery {

    private final ProductRepository productRepository;

    public GetProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product execute(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product " + id + " not found"));
    }
}
