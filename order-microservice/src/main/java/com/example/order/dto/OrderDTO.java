package com.example.order.dto;

import com.example.order.enums.Status;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private Long userId;
    private Long productId;
    private Integer quantity;
    private Float totalAmount;
    private Status status;
}
