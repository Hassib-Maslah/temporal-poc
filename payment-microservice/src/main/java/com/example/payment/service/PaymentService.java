package com.example.payment.service;

import com.example.payment.dto.PaymentDTO;
import com.example.payment.entity.Payment;
import com.example.payment.mapper.PaymentMapper;
import com.example.payment.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository productRepository;
    private final PaymentMapper paymentMapper;

    public void create(PaymentDTO dto) {
        Payment payment = paymentMapper.toEntity(dto);
        productRepository.save(payment);
    }

    public PaymentDTO findById(Long id) {
        Payment payment = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Payment not found in DB"));
        return paymentMapper.toDto(payment);
    }

    public List<PaymentDTO> findAll() {
        return productRepository.findAll().stream().map(paymentMapper::toDto).toList();
    }

}
