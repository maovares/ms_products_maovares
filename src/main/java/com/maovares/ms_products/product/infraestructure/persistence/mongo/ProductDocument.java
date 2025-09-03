package com.maovares.ms_products.product.infraestructure.persistence.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "products")
public class ProductDocument {
    @Id
    private String id;
    private String description;
    private double price;

    public ProductDocument() {
    }

    public ProductDocument(String id, String description, double price) {
        this.id = id;
        this.description = description;
        this.price = price;
    }

}
