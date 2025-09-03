package com.maovares.ms_products.product.infraestructure.persistence.mongo;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.maovares.ms_products.product.domain.model.Product;
import com.maovares.ms_products.product.domain.port.ProductRepository;

@Repository
public class ProductMongoAdapter implements ProductRepository {

    private final MongoTemplate mongoTemplate;

    public ProductMongoAdapter(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Product> findAll(int page, int size) {
        int skip = page * size;
        Query query = new Query().skip(skip).limit(size);
        List<ProductDocument> docs = mongoTemplate.find(query, ProductDocument.class);
        return docs.stream()
                .map(doc -> new Product(doc.getId(), doc.getPrice(), doc.getDescription()))
                .toList();
    }

    @Override
    public long count() {
        return mongoTemplate.count(new Query(), ProductDocument.class);
    }
}
