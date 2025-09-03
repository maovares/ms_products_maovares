package com.maovares.ms_products.product.domain.port;

import java.util.List;

import com.maovares.ms_products.product.domain.model.Product;

public interface ProductRepository {

    List<Product> findAll(int page, int size);

    long count();
}
