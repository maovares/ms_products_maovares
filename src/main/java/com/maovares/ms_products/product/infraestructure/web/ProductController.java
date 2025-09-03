package com.maovares.ms_products.product.infraestructure.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maovares.ms_products.product.application.dto.PaginatedProductsResponse;
import com.maovares.ms_products.product.application.service.PaginatedProductSearch;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Products", description = "API for product management")
@RestController
@RequestMapping("/products")
public class ProductController {
        private final PaginatedProductSearch paginatedProductSearch;

        public ProductController(PaginatedProductSearch paginatedProductSearch) {
                this.paginatedProductSearch = paginatedProductSearch;
        }

        @Operation(summary = "Get paginated product lists", description = "Returns a paginated product lists, includes optional params like: page and size.")
        @ApiResponse(responseCode = "200", description = "Paginated product list", content = @Content(schema = @Schema(implementation = PaginatedProductsResponse.class)))
        @ApiResponse(responseCode = "400", description = "Invalid params")
        @GetMapping
        public ResponseEntity<PaginatedProductsResponse> getProducts(
                        @RequestParam(defaultValue = "1") int page,
                        @RequestParam(defaultValue = "10") int size) {

                if (page < 1) {
                        return ResponseEntity.badRequest().build();
                }

                int adjustedPage = Math.max(page - 1, 0);

                PaginatedProductsResponse response = paginatedProductSearch.execute(adjustedPage, size);
                return ResponseEntity.ok(response);
        }

}
