package com.delivery_service.controller;

import com.delivery_service.dto.request.OrderRequest;
import com.delivery_service.service.order.OrderService;
import org.springframework.data.crossstore.ChangeSetPersister;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<Object> saveOrder(@Valid @RequestBody OrderRequest order) {
        try {
            orderService.saveOrder(order);
            log.info(">>> success added order");
            return ResponseEntity
                    .ok()
                    .body("Заказ успешно создан!");
        } catch (Exception e) {
            log.warn(">>> order wasn't added");
            return ResponseEntity
                    .badRequest()
                    .build();
        }
    }

    @PutMapping("/order/{id}")
    public ResponseEntity<Object> updateOrder(@Valid @PathVariable("id") long id) {
        try {
            orderService.updateOrder(id);
            log.info(">>> success update order with id = " + id);
            return ResponseEntity
                    .ok()
                    .body("Заказ успешно сформирован!");
        } catch (Exception e) {
            log.warn(">>> order with id" + id + "doesn't exist");
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @GetMapping("/order/all/{isActive}")
    public ResponseEntity<Object> fetchOrderList(@Valid @PathVariable("isActive") boolean isActive) {
        try {
            var ords = orderService.fetchOrders(isActive);
            log.info(">>> success get orders list");
            return ResponseEntity
                    .ok()
                    .body(ords);
        } catch (Exception e) {
            log.warn(">>> orders doesn't exist");
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @GetMapping("/order/cafe")
    public ResponseEntity<Object> fetchCafeByOrder(@Valid @RequestParam("orderId") long orderId) {
        try {
            var r = orderService.fetchCafeByOrder(orderId);
            log.info(">>> success get cafe by order " + orderId);
            return ResponseEntity
                    .ok()
                    .body(r);
        } catch (ChangeSetPersister.NotFoundException e) {
            log.warn(">>> order with id = " + orderId + " doesn't exist");
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<Object> isActiveOrder(@Valid @PathVariable("id") long id) {
        try {
            var r = orderService.isActiveOrder(id);
            log.info(">>> order with id = " + id + " isActive = " + r);
            return ResponseEntity
                    .ok()
                    .body(r);
        } catch (ChangeSetPersister.NotFoundException e) {
            log.warn(">>> order with id = " + id + " doesn't exist");
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }
}
