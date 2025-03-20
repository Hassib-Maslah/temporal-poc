package com.example.product.service;

import com.example.product.dto.ProductDTO;
import com.example.product.entity.Product;
import com.example.product.mapper.ProductMapper;
import com.example.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public void create(ProductDTO dto) {
        Product product = productMapper.toEntity(dto);
        productRepository.save(product);
    }

    public ProductDTO findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found in DB"));
        return productMapper.toDto(product);
    }

    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream().map(productMapper::toDto).toList();
    }

}
