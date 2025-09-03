package com.maovares.ms_products.product.infraestructure.persistence.mongo;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.maovares.ms_products.product.domain.model.Product;
import com.maovares.ms_products.product.domain.port.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        query.with(Sort.by(Sort.Direction.ASC, "_id"));
        List<ProductDocument> docs = mongoTemplate.find(query, ProductDocument.class);

        log.info("Docs encontrados: {}", docs.size());

        return docs.stream()
                .map(doc -> new Product(doc.getId(), doc.getPrice(), doc.getDescription()))
                .toList();
    }

    @Override
    public long count() {
        return mongoTemplate.count(new Query(), ProductDocument.class);
    }
}
