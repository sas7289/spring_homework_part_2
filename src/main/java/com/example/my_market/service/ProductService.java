package com.example.my_market.service;

import com.example.my_market.entity.Product;
import com.example.my_market.repository.ProductRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }


    public Product getById(UUID id) {
        return productRepository.getById(id);
    }

    public List<Product> findAll(Specification<Product> totalSpecifications) {
        return productRepository.findAll(totalSpecifications);
    }
}
