package com.usuarios.controller;

import com.usuarios.dto.PageResponse;
import com.usuarios.dto.UserRequest;
import com.usuarios.dto.UserResponse;
import com.usuarios.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping()
    public UserResponse save(@Valid @RequestBody UserRequest userRequest) {
        return userService.save(userRequest);
    }

    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
        return userService.update(userRequest,id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @GetMapping("/page")
    public PageResponse page(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        return userService.findAll(page, size, sortBy, sortDirection);
    }


}
