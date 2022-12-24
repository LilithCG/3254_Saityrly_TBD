package com.delivery_service.controller;

import com.delivery_service.dto.request.BasketRequest;
import com.delivery_service.service.basket.BasketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BasketController {
    private final BasketService basketService;

    @PostMapping("/basket")
    public ResponseEntity<String> saveBasket(@Valid @RequestBody BasketRequest basketRequest) {
        try {
            basketService.save(basketRequest);
            log.info(">>> success added basket");
            return ResponseEntity
                    .ok()
                    .body("Успешно добавлено!");
        } catch (Exception e) {
            log.warn(">>> basket wasn't added");
            return ResponseEntity
                    .badRequest()
                    .body("Ошибка при добавлении в корзину!");
        }
    }

    @GetMapping("/basket/order/{orderId}")
    public ResponseEntity<Object> fetchBasketsByOrder(@Valid @PathVariable("orderId") long orderId) {
        try {
            var b = basketService.fetchBasketByOrder(orderId);
            log.info(">>> success get baskets with orderId = " + orderId);
            return ResponseEntity
                    .ok()
                    .body(b);
        } catch (Exception e) {
            log.warn(">>> order with id = " + orderId +" doesn't exist");
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @GetMapping("/basket/user/{userId}")
    public ResponseEntity<Object> fetchActiveBasketsByUser(@Valid @PathVariable("userId") long userId) {
        try {
            var b = basketService.fetchBasketsByChatId(userId);
            log.info(">>> success get baskets with userId = " + userId);
            return ResponseEntity
                    .ok()
                    .body(b);
        } catch (Exception e) {
            log.warn(">>> user with id = " + userId +" doesn't exist");
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @DeleteMapping("/basket/{id}")
    public ResponseEntity<Object> deleteBasket(@Valid @PathVariable("id") long id) {
        try {
            basketService.deleteBasketById(id);
            log.info(">>> success delete basket with id = " + id);
            return ResponseEntity
                    .ok()
                    .body("Успешно удалено");
        } catch (ChangeSetPersister.NotFoundException e) {
            log.warn(">>> basket with id = " + id +" doesn't exist");
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @GetMapping("/basket/refs")
    public ResponseEntity<Object> fetchFoodRefs(@Valid @RequestParam(name = "orderId") long orderId) {
        try {
            var r = basketService.fetchProductsRefs(orderId);
            log.info(">>> success get food refs by orderId = " + orderId);
            return ResponseEntity
                    .ok()
                    .body(r);
        } catch (ChangeSetPersister.NotFoundException e) {
            log.warn(">>> order with id = " + orderId +" doesn't exist");
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @GetMapping("/basket/price")
    public ResponseEntity<Object> fetchPrice(@Valid @RequestParam(name = "orderId") long orderId) {
        log.info(">>> success get user price by orderId = " + orderId);
        return ResponseEntity
                .ok()
                .body("123");
    }

    @GetMapping("/search/food")
    public ResponseEntity<Object> fetchFoodByName(@Valid @RequestParam(name = "name") String name,
                                              @Valid @RequestParam(name = "orderId") long orderId) {
        try {
            var r = basketService.searchFoodByName(orderId, name);
            log.info(">>> success get food list ");
            return ResponseEntity
                    .ok()
                    .body(r);
        } catch (ChangeSetPersister.NotFoundException e) {
            log.warn(">>> food with name like %" + name +"% and orderId = " + orderId + " doesn't exist");
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @GetMapping("/count_people")
    public ResponseEntity<Object> fetchPeopleCount(@Valid @RequestParam(name = "orderId") long orderId) {
        try {
            var r = basketService.peoples(orderId).size();
            log.info(">>> success get users count = " + r);
            return ResponseEntity
                    .ok()
                    .body(r);
        } catch (ChangeSetPersister.NotFoundException e) {
            log.warn(">>> order with id = " + orderId +" doesn't exist");
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }
}