package com.example.order.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TaskQueueUtil {
    public static final String ORDER_TASK_QUEUE_NAME = "order-tasks";
}
