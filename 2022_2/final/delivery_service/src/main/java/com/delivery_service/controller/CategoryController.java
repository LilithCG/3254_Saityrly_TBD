package com.delivery_service.controller;

import com.delivery_service.postgres.entity.Category;
import com.delivery_service.service.category.CategoryService;
import com.delivery_service.dto.request.CategoryRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/category")
    public ResponseEntity<String> saveCategory(@Valid @RequestBody CategoryRequest category) {
        categoryService.saveCategory(category);
        log.info(">>> success added category");
        return ResponseEntity
                .ok()
                .body("Success get category: " + category.getName());
    }

    @GetMapping("/category")
    public ResponseEntity<List<Category>> fetchCategoryList() {
        var c = categoryService.fetchCategoryList();
        log.info(">>> success get categories list");
        return ResponseEntity
                .ok()
                .body(c);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Object> fetchCategoryById(@Valid @PathVariable("id") long id) {
        try {
            var c = categoryService.fetchCategory(id);
            log.info(">>> success get category with id = " + id);
            return ResponseEntity
                    .ok()
                    .body(c);
        } catch (Exception e) {
            log.warn(">>> category with id = " + id + " doesn't exist");
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @GetMapping("/category/name/{name}")
    public ResponseEntity<Object> fetchCategoryByName(@Valid @PathVariable("name") String name) {
        try {
            var c = categoryService.fetchCategory(name);
            log.info(">>> success get category with name = " + name);
            return ResponseEntity
                    .ok()
                    .body(c);
        } catch (Exception e) {
            log.warn(">>> category with name = " + name + " doesn't exist");
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }
}
