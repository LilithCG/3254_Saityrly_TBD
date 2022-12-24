package com.delivery_service.controller;

import com.delivery_service.dto.request.CafeRequest;
import com.delivery_service.postgres.entity.Cafe;
import com.delivery_service.service.cafe.CafeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CafeController {
    private final CafeService cafeService;

    @PostMapping("/cafe")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<String> saveCafe(@Valid @RequestBody CafeRequest cafe) {
        cafeService.saveCafe(cafe);
        log.info(">>> success added cafe");
        return ResponseEntity
                .ok()
                .body("Success added cafe!");
    }

    @GetMapping("/cafe")
    public ResponseEntity<List<Cafe>> fetchCafeList(){
        var c = cafeService.fetchCafeList();
        log.info(">>> success get cafes list");
        return ResponseEntity
                .ok().body(c);
    }

    @GetMapping("/cafe/{id}")
    public ResponseEntity<Object> fetchCafeById(@Valid @PathVariable("id") long id) {
        try {
            var c = cafeService.fetchCafe(id);
            log.info(">>> success get cafe with id = " + id);
            return ResponseEntity
                    .ok().body(c);
        } catch (Exception e) {
            log.warn(">>> cafe with id = " + id + " doesn't exist");
            return ResponseEntity
                    .notFound().build();
        }
    }

    @GetMapping("/cafe/name/{name}")
    public ResponseEntity<Object> fetchCafeByName(@Valid @PathVariable("name") String name) {
        try {
            var c = cafeService.fetchCafe(name);
            log.info(">>> success get cafe with name = " + name);
            return ResponseEntity
                    .ok().body(c);
        } catch (Exception e) {
            log.warn(">>> cafe with name = " + name + " doesn't exist");
            return ResponseEntity
                    .notFound().build();
        }
    }
}
