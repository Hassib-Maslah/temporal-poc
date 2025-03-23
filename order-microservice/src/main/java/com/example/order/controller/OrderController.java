package com.example.order.controller;

import com.example.order.dto.OrderDTO;
import com.example.order.enums.Status;
import com.example.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-management")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> createOrder(@RequestBody OrderDTO dto) {
        return ResponseEntity.ok(orderService.createOrder(dto));
    }

    @PutMapping(value = "/{workflowId}", consumes = "application/json")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable String workflowId, @RequestBody Status status) {
        orderService.updateStatus(status, workflowId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

}
