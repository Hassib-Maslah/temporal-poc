package com.example.order.workflow;

import com.example.order.activity.OrderActivity;
import com.example.order.activity.PaymentActivity;
import com.example.order.activity.ProductActivity;
import com.example.order.dto.OrderDTO;
import com.example.order.enums.Status;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

import static com.example.order.util.TaskQueueUtil.ORDER_TASK_QUEUE_NAME;

@Slf4j
@WorkflowImpl(taskQueues = ORDER_TASK_QUEUE_NAME)
public class OrderWorkflowAsyncImpl implements OrderWorkflowAsync {

    private Status status = Status.INITIALIZED;

    private final ActivityOptions activityOptions =
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofSeconds(10)) // The maximum time allowed for a single Activity task execution
                    .setTaskQueue(ORDER_TASK_QUEUE_NAME)
                    .setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(2).build())
                    .build();

    private final ProductActivity productActivity =
            Workflow.newActivityStub(ProductActivity.class, activityOptions);

    private final PaymentActivity paymentActivity =
            Workflow.newActivityStub(PaymentActivity.class, activityOptions);

    private final OrderActivity orderActivity =
            Workflow.newActivityStub(OrderActivity.class, activityOptions);

    @Override
    public OrderDTO createOrder(OrderDTO dto) {
        log.info("OrderWorkflow started");
        // Handle product's stock and determine total price
        Integer totalPrice = productActivity.handleStock(dto.getProductId(), dto.getQuantity());
        // Do the payment and return total paid amount
        Float totalAmountPaid = paymentActivity.processPayment(dto.getUserId(), totalPrice);
        Workflow.await(Duration.ofDays(30), () -> !Status.INITIALIZED.equals(status));
        // Save the order
        dto.setStatus(status);
        dto.setTotalAmount(totalAmountPaid);
        OrderDTO result = orderActivity.saveOrder(dto);
        log.info("OrderWorkflow finished with success");
        return result;
    }

    @Override
    public void updateStatus(Status status) {
        this.status = status;
        log.info("OrderWorkflow signal received with status {}", status);
    }

}
