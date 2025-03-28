package com.example.order.service;

import com.example.order.dto.OrderDTO;
import com.example.order.entity.Order;
import com.example.order.enums.Status;
import com.example.order.mapper.OrderMapper;
import com.example.order.repository.OrderRepository;
import com.example.order.workflow.OrderWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.order.util.TaskQueueUtil.ORDER_TASK_QUEUE_NAME;

@Slf4j
@Service
@ConditionalOnProperty(prefix = "workflow.mode", name = "async", havingValue = "false", matchIfMissing = true)
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final WorkflowClient workflowClient;

    @Override
    public String createOrder(OrderDTO dto) {
        var orderWorkflow = workflowClient.newWorkflowStub(
                OrderWorkflow.class,
                WorkflowOptions.newBuilder()
                        .setTaskQueue(ORDER_TASK_QUEUE_NAME)
                        .build());

        OrderDTO order = orderWorkflow.createOrder(dto);

        return String.valueOf(order.getId());
    }

    @Override
    public void updateStatus(Status status, String workflowId) {
    }

    @Override
    public OrderDTO getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order not found in DB"));
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(orderMapper::toDto).toList();
    }

}
