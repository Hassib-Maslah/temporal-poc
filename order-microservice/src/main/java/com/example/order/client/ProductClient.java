package com.example.order.client;

import com.example.order.client.model.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "productClient", url = "${api.product-service.url}")
public interface ProductClient {

    @PostMapping
    ProductDTO save(@RequestBody ProductDTO productDTO);

    @GetMapping("{id}")
    ProductDTO getById(@PathVariable("id") Long id);
}

