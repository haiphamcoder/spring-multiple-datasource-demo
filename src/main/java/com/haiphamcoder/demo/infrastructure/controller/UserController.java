package com.haiphamcoder.demo.infrastructure.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haiphamcoder.demo.application.service.UserService;
import com.haiphamcoder.demo.domain.entity.primary.User;
import com.haiphamcoder.demo.infrastructure.mapper.UserMapper;
import com.haiphamcoder.demo.infrastructure.mapper.dto.UserDto;
import com.haiphamcoder.demo.shared.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController extends BaseController {
    private final UserService userService;

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            List<UserDto> userDtos = users.stream().map(UserMapper::toDto).toList();
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(HttpStatus.OK.value(), "Get all users successfully", userDtos, null, null));
        } catch (Exception e) {
            log.error("Error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null, null, null));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<UserDto>> createUser(@RequestBody UserDto userDto) {
        try {
            User user = UserMapper.toEntity(userDto);
            User createdUser = userService.createUser(user);
            UserDto createdUserDto = UserMapper.toDto(createdUser);
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(HttpStatus.OK.value(), "Create user successfully", createdUserDto, null, null));
        } catch (Exception e) {
            log.error("Error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null, null, null));
        }
    }
}
