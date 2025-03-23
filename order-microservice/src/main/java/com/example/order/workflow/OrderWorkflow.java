package com.example.order.workflow;

import com.example.order.dto.OrderDTO;
import com.example.order.enums.Status;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface OrderWorkflow {
    @WorkflowMethod
    OrderDTO createOrder(OrderDTO dto);

    @SignalMethod
    void updateStatus(Status status);

}