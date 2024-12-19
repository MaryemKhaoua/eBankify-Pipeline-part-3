package com.example.ebankify_security.controller;

import com.example.ebankify_security.dto.UserDto;
import com.example.ebankify_security.domain.requests.UserRequest;
import com.example.ebankify_security.vm.UserVM;
import com.example.ebankify_security.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<UserVM> saveUser(@Valid @RequestBody UserRequest userRequest) {
        UserDto userDto = userService.save(userRequest);
        UserVM response = UserVM.builder()
                .user(userDto)
                .message("user saved successfuly")
                .statusCode(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserVM> getUserById(@PathVariable Long id) {
        UserDto userDto = userService.findById(id);
        UserVM response = UserVM.builder()
                .user(userDto)
                .message("user found successfuly")
                .statusCode(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserVM> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
        UserDto userDto = userService.update(id, userRequest);
        UserVM response = UserVM.builder()
                .user(userDto)
                .message("user updated successfully")
                .statusCode(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }
}