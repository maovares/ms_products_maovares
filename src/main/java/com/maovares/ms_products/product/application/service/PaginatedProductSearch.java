package com.maovares.ms_products.product.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.maovares.ms_products.product.application.dto.PaginatedProductsResponse;
import com.maovares.ms_products.product.application.dto.ProductResponseDto;
import com.maovares.ms_products.product.domain.model.Product;
import com.maovares.ms_products.product.domain.port.ProductRepository;

@Service
public class PaginatedProductSearch {
    private final ProductRepository productRepository;

    public PaginatedProductSearch(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public PaginatedProductsResponse execute(int page, int size) {
        List<Product> products = productRepository.findAll(page, size);
        long totalElements = productRepository.count();

        List<ProductResponseDto> productResponseDtoList = products.stream()
                .map(product -> new ProductResponseDto(product.getId(), product.getDescription(), product.getPrice()))
                .toList();

        return new PaginatedProductsResponse(productResponseDtoList, page, size, totalElements);
    }
}
