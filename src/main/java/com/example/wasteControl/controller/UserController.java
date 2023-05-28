package com.example.wasteControl.controller;

import com.example.wasteControl.controller.api.UserApi;
import com.example.wasteControl.dto.LoginDto;
import com.example.wasteControl.dto.UserProfileDto;
import com.example.wasteControl.dto.UserReqDto;
import com.example.wasteControl.service.UserService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
public class UserController implements UserApi {
    private final UserService userService;
    public ResponseEntity addUser(UserReqDto userReqDto) {
        return ResponseEntity.ok().body(userService.add(userReqDto));
    }
    public ResponseEntity addUserProfile(UserProfileDto userProfileDto) {
        return ResponseEntity.ok().body(userService.addProfile(userProfileDto));
    }
    public ResponseEntity getUsers(int page, int size) {
        return ResponseEntity.ok().body(userService.getAll(page, size));
    }

    public ResponseEntity getUserById(String id) {

        return ResponseEntity.ok().body(userService.getById(id));
    }

    public ResponseEntity editUser(String id, UserReqDto userReqDto) {
        return ResponseEntity.ok().body(userService.edit(id, userReqDto));
    }

    public ResponseEntity<Boolean> auth(LoginDto loginDto) {
        return ResponseEntity.ok().body(userService.doLogin(loginDto));

    }
}
