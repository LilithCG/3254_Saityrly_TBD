package com.delivery_service.controller;

import com.delivery_service.dto.request.FoodRequest;
import com.delivery_service.service.food.FoodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;

    @PostMapping("/food")
    public ResponseEntity<Object> saveFood(@Valid@RequestBody FoodRequest foodRequest) {
        try {
            foodService.save(foodRequest);
            log.info(">>> success save food");
            return ResponseEntity
                    .ok()
                    .body("Продукт успешно сохранен!");
        } catch (Exception e) {
            log.warn(">>> food with ProductLink: " + foodRequest.getProductLink() + " failed to save");
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @GetMapping("/food/{id}")
    public ResponseEntity<Object> fetchFoodById(@Valid @PathVariable("id") long id) {
        try {
            var f = foodService.fetchById(id);
            log.info(">>> success get food with id = " + id);
            return ResponseEntity
                    .ok()
                    .body(f);
        } catch (Exception e) {
            log.warn(">>> food with id = " + id + " doesn't exist");
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @GetMapping("/food")
    public ResponseEntity<Object> fetchFoodByCafeAndCategory(@Valid @RequestParam(name = "cafeId") long cafeId,
                                                         @Valid @RequestParam(name = "categoryId") long categoryId) {
        try {
            var f = foodService.findFoodByCafeAndCategory(cafeId, categoryId);
            log.info(">>> success get list food by category and cafe");
            return ResponseEntity
                    .ok()
                    .body(f);
        } catch (Exception e) {
            log.warn(">>> food with cafeId = " + cafeId + " and categoryId = " + categoryId + " doesn't exist");
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @GetMapping("/food/categories/{cafeId}")
    public ResponseEntity<Object> fetchCategoryByCafe(@Valid @PathVariable("cafeId") long cafeId) {
        try {
            var c = foodService.findCategoryByCafe(cafeId);
            log.info(">>> success get category list by cafe with id = " + cafeId);
            return ResponseEntity
                    .ok()
                    .body(c.keySet());
        } catch (Exception e) {
           log.warn(">>> categories with cafeId = " + cafeId + " doesn't exist");
           return ResponseEntity
                   .notFound()
                   .build();
        }
    }
}
