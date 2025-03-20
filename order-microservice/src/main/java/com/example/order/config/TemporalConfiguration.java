package com.example.order.config;

import com.example.order.activity.OrderActivity;
import com.example.order.activity.PaymentActivity;
import com.example.order.activity.ProductActivity;
import com.example.order.workflow.OrderWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.example.order.util.TaskQueueUtil.ORDER_TASK_QUEUE_NAME;

/**
 * This is an alternative way to configure Worker + WorkflowClient Spring beans
 */
@Slf4j
@Configuration
public class TemporalConfiguration {
//    @Bean
//    public WorkflowClient workflowClient() {
//        // Create an instance that connects to a Temporal Service running on the local
//        // machine, using the default port (7233)
//        WorkflowServiceStubs serviceStub = WorkflowServiceStubs.newLocalServiceStubs();
//        // Initialize the Temporal Client
//        return WorkflowClient.newInstance(serviceStub);
//    }
//
//    @Bean
//    public Worker worker(WorkflowClient workflowClient,
//                         ProductActivity productActivity,
//                         PaymentActivity paymentActivity,
//                         OrderActivity orderActivity) {
//
//        WorkerFactory factory = WorkerFactory.newInstance(workflowClient);
//        Worker worker = factory.newWorker(ORDER_TASK_QUEUE_NAME);
//        // Registering workflows and activities
//        worker.registerWorkflowImplementationTypes(OrderWorkflowImpl.class);
//        worker.registerActivitiesImplementations(productActivity, paymentActivity, orderActivity);
//        log.info("Worker started, listening to task queue: " + ORDER_TASK_QUEUE_NAME);
//
//        factory.start();
//
//        return worker;
//    }

}
