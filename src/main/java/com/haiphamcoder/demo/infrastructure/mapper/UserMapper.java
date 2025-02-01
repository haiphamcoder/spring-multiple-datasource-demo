package com.haiphamcoder.demo.infrastructure.mapper;

import com.haiphamcoder.demo.domain.entity.primary.User;
import com.haiphamcoder.demo.infrastructure.mapper.dto.UserDto;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {
    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setRole(user.getRole());
        userDto.setCreatedTime(user.getCreatedTime());
        userDto.setModifiedTime(user.getModifiedTime());
        return userDto;
    }

    public static User toEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        user.setCreatedTime(userDto.getCreatedTime());
        user.setModifiedTime(userDto.getModifiedTime());
        return user;
    }
}
