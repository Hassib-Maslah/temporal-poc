package com.example.order.activity;

import com.example.order.client.ProductClient;
import com.example.order.client.model.ProductDTO;
import io.temporal.spring.boot.ActivityImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.example.order.util.TaskQueueUtil.ORDER_TASK_QUEUE_NAME;

@Slf4j
@Component
@ActivityImpl(taskQueues = ORDER_TASK_QUEUE_NAME)
@RequiredArgsConstructor
public class ProductActivityImpl implements ProductActivity {

    private final ProductClient productClient;

    @Override
    public Integer handleStock(Long productId, Integer quantity) {
        log.info("ProductActivityImpl.checkAndUpdateStock({}, {}) started", productId, quantity);
        ProductDTO productDTO = productClient.getById(productId);
        if (productDTO.getStock() < quantity) {
            throw new RuntimeException("Product stock is not enough");
        }
        productDTO.setStock(productDTO.getStock() - quantity);
        productClient.save(productDTO);
        return productDTO.getPrice() * quantity;
    }
}
