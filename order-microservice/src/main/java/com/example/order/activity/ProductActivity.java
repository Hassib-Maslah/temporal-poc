package com.example.order.activity;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface ProductActivity {
    @ActivityMethod
    Integer handleStock(Long productId, Integer quantity);
}
