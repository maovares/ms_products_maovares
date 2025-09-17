package com.maovares.ms_products.product.infraestructure.web;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maovares.ms_products.product.application.port.in.CreateProductCommand;
import com.maovares.ms_products.product.application.port.in.GetProductQuery;
import com.maovares.ms_products.product.application.port.in.GetProductsQuery;
import com.maovares.ms_products.product.domain.model.Product;
import com.maovares.ms_products.product.infraestructure.web.dto.CreateProductDto;
import com.maovares.ms_products.product.infraestructure.web.dto.PagedResponseDto;
import com.maovares.ms_products.product.infraestructure.web.dto.ProductResponseDto;
import com.maovares.ms_products.product.infraestructure.web.dto.mapper.ProductDtoMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Products", description = "API for product management")
@RestController
@RequestMapping("/products")
public class ProductController {
        private final GetProductsQuery getProductsQuery;
        private final GetProductQuery getProductQuery;
        private final CreateProductCommand createProductCommand;

        public ProductController(GetProductsQuery getProductsQuery, GetProductQuery getProductQuery,
                        CreateProductCommand createProductCommand) {
                this.getProductsQuery = getProductsQuery;
                this.getProductQuery = getProductQuery;
                this.createProductCommand = createProductCommand;
        }

        @Operation(summary = "Get paginated product lists", description = "Returns a paginated product list, supports query params like: page, size, sort.")
        @ApiResponse(responseCode = "200", description = "Paginated product list", content = @Content(schema = @Schema(implementation = PagedResponseDto.class)))
        @ApiResponse(responseCode = "400", description = "Invalid params")
        @GetMapping
        public PagedResponseDto<ProductResponseDto> getProducts(@ParameterObject Pageable pageable) {

                Page<Product> products = getProductsQuery.execute(pageable);

                List<ProductResponseDto> content = products.getContent()
                                .stream()
                                .map(ProductDtoMapper::toResponse)
                                .toList();

                return new PagedResponseDto<>(
                                content,
                                products.getNumber(),
                                products.getSize(),
                                products.getTotalElements(),
                                products.getTotalPages());
        }

        @Operation(summary = "Get product by ID", description = "Returns a single product by its ID.")
        @ApiResponse(responseCode = "200", description = "Product found", content = @Content(schema = @Schema(implementation = ProductResponseDto.class)))
        @GetMapping("/{id}")
        public ProductResponseDto getProductById(@PathVariable String id) {
                Product product = getProductQuery.execute(id);
                return ProductDtoMapper.toResponse(product);
        }

        @Operation(summary = "Create a new product", description = "Creates a new product with the provided details.")
        @ApiResponse(responseCode = "201", description = "Product created", content = @Content(schema = @Schema(implementation = ProductResponseDto.class)))
        @PostMapping()
        public ResponseEntity<ProductResponseDto> createProduct(@RequestBody @Valid CreateProductDto body) {
                Product product = createProductCommand.execute(body.description(), body.price(), body.image(),
                                body.title());
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(ProductDtoMapper.toResponse(product));
        }

}
