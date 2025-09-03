package com.maovares.ms_products.product.application.dto;

import java.util.List;

import lombok.Data;

@Data
public class PaginatedProductsResponse {
    private final List<ProductResponseDto> content;
    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;

    public PaginatedProductsResponse(List<ProductResponseDto> content, int page, int size, long totalElements) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = (int) Math.ceil(totalElements / (double) size);
    }
}
