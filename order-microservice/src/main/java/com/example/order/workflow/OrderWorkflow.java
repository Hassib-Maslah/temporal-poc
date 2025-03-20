package com.example.order.workflow;

import com.example.order.dto.OrderDTO;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface OrderWorkflow {
    @WorkflowMethod
    void createOrder(OrderDTO dto);
}