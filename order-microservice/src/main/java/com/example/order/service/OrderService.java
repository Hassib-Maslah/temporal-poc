package com.example.order.service;

import com.example.order.dto.OrderDTO;
import com.example.order.entity.Order;
import com.example.order.mapper.OrderMapper;
import com.example.order.repository.OrderRepository;
import com.example.order.util.TaskQueueUtil;
import com.example.order.workflow.OrderWorkflow;
import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.order.util.TaskQueueUtil.ORDER_TASK_QUEUE_NAME;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final WorkflowClient workflowClient;

    public void createOrder(OrderDTO dto) {
        var orderWorkflow =
                workflowClient.newWorkflowStub(
                        OrderWorkflow.class,
                        WorkflowOptions.newBuilder()
                                .setWorkflowId("createOrder-" + RandomUtils.secure().randomInt())
                                .setTaskQueue(ORDER_TASK_QUEUE_NAME)
                                .build());
        WorkflowExecution execution = WorkflowClient.start(orderWorkflow::createOrder, dto);
        // The workflow is now running asynchronously, and the execution ID is available
        System.out.println("Workflow started with ID: " + execution.getWorkflowId());
    }

    public OrderDTO getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order not found in DB"));
        return orderMapper.toDto(order);
    }

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(orderMapper::toDto).toList();
    }

}
