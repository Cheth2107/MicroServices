package com.example.ProductService.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ProductService.model.Product;
import com.example.ProductService.Service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/products")
@Tag(name = "Products Management", description = "Endpoints for managing products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
   
    public ResponseEntity<List<Product>> getProducts() {
        try {
            List<Product> products = productService.getProducts();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{prodId}")
  
    public ResponseEntity<Product> getProductById(
            @Parameter(description = "ID of the product to be retrieved", required = true)
            @PathVariable("prodId") Long prodId) {
        try {
            Optional<Product> product = productService.getProductById(prodId);
            return product.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
  
    public ResponseEntity<Product> addProduct(
            @Parameter(description = "Product object to be created", required = true)
            @RequestBody Product product) {
        try {
            Product savedProduct = productService.addProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{prodId}")
    
    public ResponseEntity<Product> updateProduct(
            @Parameter(description = "ID of the product to be updated", required = true)
            @PathVariable("prodId") Long prodId,
            @Parameter(description = "Updated product object", required = true)
            @RequestBody Product product) {
        try {
            Product updatedProduct = productService.updateProduct(prodId, product);
            if (updatedProduct != null) {
                return ResponseEntity.ok(updatedProduct);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{prodId}")
   
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "ID of the product to be deleted", required = true)
            @PathVariable("prodId") Long prodId) {
        try {
            boolean isDeleted = productService.deleteProduct(prodId);
            if (isDeleted) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/filter")
   
    public ResponseEntity<List<Product>> filterDataByPrice(
            @Parameter(description = "Maximum price threshold", required = true)
            @RequestParam("price") Double price) {
        try {
            List<Product> products = productService.getProductsByPriceLessThan(price);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/search")
    
    public ResponseEntity<List<Product>> searchProductsByName(
            @Parameter(description = "Name pattern to search for", required = true)
            @RequestParam("name") String name) {
        try {
            List<Product> products = productService.getProductsByNameContaining(name);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}