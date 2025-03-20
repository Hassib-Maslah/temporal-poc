package com.example.payment.controller;

import com.example.payment.dto.PaymentDTO;
import com.example.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment-management")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Void> create(@RequestBody PaymentDTO dto) {
        paymentService.create(dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<PaymentDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.findById(id));
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<PaymentDTO>> findAll() {
        return ResponseEntity.ok(paymentService.findAll());
    }

}
