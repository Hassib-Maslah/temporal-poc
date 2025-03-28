package com.example.order.service;

import com.example.order.dto.OrderDTO;
import com.example.order.enums.Status;

import java.util.List;

public interface OrderService {

    String createOrder(OrderDTO dto);

    void updateStatus(Status status, String workflowId);

    OrderDTO getOrder(Long orderId);

    List<OrderDTO> getAllOrders();
}
