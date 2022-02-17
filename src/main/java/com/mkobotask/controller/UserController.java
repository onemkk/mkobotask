package com.mkobotask.controller;

import com.mkobotask.dto.requests.EditUserRequestDto;
import com.mkobotask.dto.requests.LoginRequestDto;
import com.mkobotask.dto.requests.SignUpRequestDto;
import com.mkobotask.services.serviceInterFaces.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userServiceInterface;

    public UserController(UserService userServiceInterface) {
        this.userServiceInterface = userServiceInterface;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser (@Valid @RequestBody SignUpRequestDto signUpRequestDto){
        return new ResponseEntity<>(userServiceInterface.signUpUser(signUpRequestDto), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequestDto loginRequestDto){
        return new ResponseEntity<>(userServiceInterface.loginUser(loginRequestDto), HttpStatus.OK);
    }

    @PostMapping("/edituserdetails/{userid}")
    public ResponseEntity<?> editUser (@Valid @RequestBody EditUserRequestDto editUserRequestDto, @PathVariable String userid){
        userServiceInterface.checkLoginStatus(userid);
        return new ResponseEntity<>(userServiceInterface.updateUser(editUserRequestDto, userid),HttpStatus.OK);
    }

}
