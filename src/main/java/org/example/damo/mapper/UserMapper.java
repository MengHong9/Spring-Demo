package org.example.damo.mapper;

import org.example.damo.dto.user.UserDto;
import org.example.damo.dto.user.UserResponseDto;
import org.example.damo.entity.User;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class UserMapper {

    public User toEntity(UserDto dto) {
        User entity = new User();

        entity.setName(dto.getName());
        entity.setPassword(dto.getPassword());
        entity.setAge(dto.getAge());
        entity.setEmail(dto.getEmail());
        entity.setRole(dto.getRole());
        entity.setAddress(dto.getAddress());

        return entity;
    }

    public UserResponseDto toDto(User entity) {
        UserResponseDto userDto = new UserResponseDto();
        userDto.setId(entity.getId());
        userDto.setName(entity.getName());
        userDto.setEmail(entity.getEmail());
        userDto.setAge(entity.getAge());
        userDto.setAddress(entity.getAddress());
        userDto.setRole(entity.getRole());
        userDto.setCreatedAt(entity.getCreatedAt());
        userDto.setUpdatedAt(entity.getUpdatedAt());


        return userDto;
    }

    public List<UserResponseDto> toDtoList(List<User> entityList) {
        if (entityList == null || entityList.isEmpty()) {
            return new ArrayList<>();
        }
        return entityList.stream().map(user -> this.toDto(user)).collect(Collectors.toList());
    }


    public void  updateEntityFromDto(User entity , UserDto dto) {
        if (entity == null || dto == null) {
            return;
        }
        entity.setName(dto.getName());
        entity.setPassword(dto.getPassword());
        entity.setEmail(dto.getEmail());
        entity.setAddress(dto.getAddress());
        entity.setRole(dto.getRole());
        entity.setAge(dto.getAge());
    }

}
