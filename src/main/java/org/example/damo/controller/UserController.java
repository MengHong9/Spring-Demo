package org.example.damo.controller;

import jakarta.validation.Valid;
import org.example.damo.dto.user.UserUpdateDto;
import org.example.damo.model.BaseResponeModel;
import org.example.damo.model.BaseResponseWithAdditionalDateModel;
import org.example.damo.dto.user.UserDto;
import org.example.damo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;



    @GetMapping()
    public ResponseEntity<BaseResponseWithAdditionalDateModel> listUser() {
        return userService.getUser();
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<BaseResponseWithAdditionalDateModel> getOneUser(@PathVariable("user_id") Long user_id) {
        return userService.getOneUser(user_id);
    }

    @PostMapping()
    public ResponseEntity<BaseResponeModel> createUserData(@Valid @RequestBody UserDto payload) {
        return userService.createUser(payload);
    }

    @PutMapping("/{user_id}")
    public ResponseEntity<BaseResponeModel> updateUser(@PathVariable("user_id") Long userId, @RequestBody UserUpdateDto payload) {
        return userService.updateUser(payload , userId);
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<BaseResponeModel> deleteUser(@PathVariable("user_id") Long userId) {
        return userService.deleteUser(userId);
    }

}
