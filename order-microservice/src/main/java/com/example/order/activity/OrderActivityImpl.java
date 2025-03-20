package com.example.order.activity;

import com.example.order.dto.OrderDTO;
import com.example.order.entity.Order;
import com.example.order.mapper.OrderMapper;
import com.example.order.repository.OrderRepository;
import io.temporal.spring.boot.ActivityImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.example.order.util.TaskQueueUtil.ORDER_TASK_QUEUE_NAME;

@Slf4j
@Component
@ActivityImpl(taskQueues = ORDER_TASK_QUEUE_NAME)
@RequiredArgsConstructor
public class OrderActivityImpl implements OrderActivity {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public void saveOrder(OrderDTO orderDTO) {
        log.info("OrderActivityImpl.saveOrder(..) started with total amount paid = {}", orderDTO.getTotalAmount());
        Order order = orderMapper.toEntity(orderDTO);
        orderRepository.save(order);
    }
}
