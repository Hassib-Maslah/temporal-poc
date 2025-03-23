package com.example.order.service;

import com.example.order.dto.OrderDTO;
import com.example.order.entity.Order;
import com.example.order.enums.Status;
import com.example.order.mapper.OrderMapper;
import com.example.order.repository.OrderRepository;
import com.example.order.workflow.OrderWorkflow;
import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.workflow.Workflow;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.order.util.TaskQueueUtil.ORDER_TASK_QUEUE_NAME;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final WorkflowClient workflowClient;

    public String createOrder(OrderDTO dto) {
        var orderWorkflow = workflowClient.newWorkflowStub(
                OrderWorkflow.class,
                WorkflowOptions.newBuilder()
                        .setTaskQueue(ORDER_TASK_QUEUE_NAME)
                        .build());

//        return orderWorkflow.createOrder(dto);

        // this is another alternative to run the workflow asynchronously
         WorkflowExecution execution = WorkflowClient.start(orderWorkflow::createOrder, dto);
         log.info("Workflow started with ID: {}", execution.getWorkflowId());
         return execution.getWorkflowId();
    }

    public void updateStatus(Status status, String workflowId) {
        var orderWorkflow = workflowClient.newWorkflowStub(OrderWorkflow.class, workflowId);
        orderWorkflow.updateStatus(status);
    }

    public OrderDTO getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order not found in DB"));
        return orderMapper.toDto(order);
    }

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(orderMapper::toDto).toList();
    }

}
