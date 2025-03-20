package com.example.order.activity;

import com.example.order.client.PaymentClient;
import com.example.order.client.model.PaymentDTO;
import io.temporal.spring.boot.ActivityImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.example.order.util.TaskQueueUtil.ORDER_TASK_QUEUE_NAME;

@Slf4j
@Component
@ActivityImpl(taskQueues = ORDER_TASK_QUEUE_NAME)
@RequiredArgsConstructor
public class PaymentActivityImpl implements PaymentActivity {

    private final PaymentClient paymentClient;

    @Override
    public Float processPayment(Long payerId, Integer amount) {
        log.info("PaymentActivityImpl.processPayment({}, {}) started", payerId, amount);
        Float total = amount * 1.1f;
        PaymentDTO paymentDTO = PaymentDTO.builder()
                .amount(total) // total amount + TVA (10%)
                .payerId(payerId)
                .build();
        paymentClient.create(paymentDTO);
        return total;
    }
}
