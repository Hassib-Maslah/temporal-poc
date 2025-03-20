package com.example.order.client;

import com.example.order.client.model.PaymentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "paymentClient", url = "${api.payment-service.url}")
public interface PaymentClient {

    @PostMapping
    PaymentDTO create(@RequestBody PaymentDTO paymentDTO);

    @GetMapping("{id}")
    PaymentDTO getById(@PathVariable("id") Long id);
}

