package com.maovares.ms_products.product.infraestructure.persistence.mongo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.maovares.ms_products.product.application.port.out.ProductRepository;
import com.maovares.ms_products.product.domain.model.Product;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class ProductMongoAdapter implements ProductRepository {

        private final MongoTemplate mongoTemplate;

        public ProductMongoAdapter(MongoTemplate mongoTemplate) {
                this.mongoTemplate = mongoTemplate;
        }

        @Override
        public Page<Product> findAll(Pageable pageable) {
                long pageSize = pageable.getPageSize();
                long pageNumber = pageable.getPageNumber();
                long skip = pageNumber * pageSize;

                Query query = new Query()
                                .with(pageable)
                                .skip(skip)
                                .limit((int) pageSize);

                List<ProductDocument> productDocuments = mongoTemplate.find(query, ProductDocument.class);
                List<Product> products = productDocuments.stream()
                                .map(doc -> new Product(doc.getId(), doc.getPrice(), doc.getDescription(),
                                                doc.getImage(),
                                                doc.getTitle()))
                                .toList();

                long totalProducts = mongoTemplate.count(new Query(), ProductDocument.class);

                return new PageImpl<>(products, pageable, totalProducts);
        }

        @Override
        public Optional<Product> findById(String id) {
                Query query = new Query(Criteria.where("id").is(id));
                ProductDocument productDocument = mongoTemplate.findOne(query, ProductDocument.class);
                return Optional.ofNullable(productDocument)
                                .map(doc -> new Product(doc.getId(), doc.getPrice(), doc.getDescription(),
                                                doc.getImage(),
                                                doc.getTitle()));
        }

        @Override
        public Product save(Product product) {
                ProductDocument productDocument = new ProductDocument(product.getId(), product.getDescription(),
                                product.getPrice(),
                                product.getImage(), product.getTitle());
                ProductDocument savedDocument = mongoTemplate.save(productDocument);
                return new Product(savedDocument.getId(), savedDocument.getPrice(), savedDocument.getDescription(),
                                savedDocument.getImage(), savedDocument.getTitle());
        }
}
