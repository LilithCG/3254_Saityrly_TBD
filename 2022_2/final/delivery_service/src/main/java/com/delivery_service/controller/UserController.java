package com.delivery_service.controller;

import com.delivery_service.postgres.entity.User;
import com.delivery_service.dto.request.UserRequest;
import com.delivery_service.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<String> saveUser(@Valid @RequestBody UserRequest user) {
        if (userService.exist(user.getId())) {
            log.warn(">>> user with id = " + user.getId() + " is already exist");
            return ResponseEntity
                    .ok()
                    .body("Вы уже можете сделать заказ!");
        }
        userService.saveUser(user);
        log.info(">>> success added user");
        return ResponseEntity
                .ok()
                .body("Добро пожаловать!");
    }

    @GetMapping("/user")
    public ResponseEntity<List<User>> fetchUserList() {
        var c = userService.fetchUserList();
        log.info(">>> success get users list");
        return ResponseEntity
                .ok().body(c);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Object> fetchUser(@Valid @PathVariable("id") long id) {
        try {
            var c = userService.fetchUser(id);
            log.info(">>> success get user with id = " + id);
            return ResponseEntity
                    .ok()
                    .body(c);
        } catch (Exception e) {
            log.warn(">>> user with id = " + id + " doesn't exist");
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<Object> updateUser(@RequestBody UserRequest user, @PathVariable("id") long id) {
        try {
            userService.updateUser(user, id);
            log.info(">>> success update user with id = " + id);
            return ResponseEntity
                    .ok()
                    .body("Данные о пользователе успешно обновлены!");
        } catch (Exception e) {
            log.warn(">>> user with id = " + id + " doesn't exist");
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }
}
