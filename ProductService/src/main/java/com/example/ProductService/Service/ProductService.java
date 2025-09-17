package com.example.ProductService.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ProductService.model.Product;
import com.example.ProductService.model.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    
    public List<Product> getProducts(){
        return productRepository.findAll();
    }
    
    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }
    
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }
    
    public Product updateProduct(Long prodId, Product newProduct) {
        Optional<Product> optionalProduct = productRepository.findById(prodId);
        if(optionalProduct.isPresent()) {
            Product oldProduct = optionalProduct.get();
            oldProduct.setName(newProduct.getName());
            oldProduct.setPrice(newProduct.getPrice());
            return productRepository.save(oldProduct);
        }
        return null;
    }
    
    public List<Product> getProductsByPriceLessThan(Double price){
        return productRepository.filterByPrice(price);
    }

    public boolean deleteProduct(Long prodId) {
        if (productRepository.existsById(prodId)) {
            productRepository.deleteById(prodId);
            return true;
        }
        return false;
    }

    public List<Product> getProductsByNameContaining(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }
}