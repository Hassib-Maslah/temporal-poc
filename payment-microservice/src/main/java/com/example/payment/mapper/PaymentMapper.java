package com.example.payment.mapper;

import com.example.payment.dto.PaymentDTO;
import com.example.payment.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentDTO toDto(Payment payment);

    Payment toEntity(PaymentDTO dto);

}
