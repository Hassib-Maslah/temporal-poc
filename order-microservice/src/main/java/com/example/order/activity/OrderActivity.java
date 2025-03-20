package com.example.order.activity;

import com.example.order.dto.OrderDTO;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface OrderActivity {
    @ActivityMethod
    void saveOrder(OrderDTO orderDTO);
}