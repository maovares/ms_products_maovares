package com.maovares.ms_products.product.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.maovares.ms_products.product.application.port.in.CreateProductCommand;
import com.maovares.ms_products.product.application.port.out.ProductRepository;
import com.maovares.ms_products.product.domain.model.Product;

@Service
public class CreateProductService implements CreateProductCommand {

    private final ProductRepository productRepository;

    public CreateProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product execute(String description, double price, String image, String title) {
        String id = UUID.randomUUID().toString();

        Product product = new Product(id, price, description, image, title);
        return productRepository.save(product);
    }

}
