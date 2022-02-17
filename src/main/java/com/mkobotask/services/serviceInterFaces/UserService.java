package com.mkobotask.services.serviceInterFaces;

import com.mkobotask.dto.requests.EditUserRequestDto;
import com.mkobotask.dto.requests.LoginRequestDto;
import com.mkobotask.dto.requests.SignUpRequestDto;
import com.mkobotask.dto.responses.EditUserResponseDto;
import com.mkobotask.dto.responses.LoginResponseDto;
import com.mkobotask.dto.responses.SignUpResponseDto;
import org.springframework.security.authentication.BadCredentialsException;

public interface UserService {

    SignUpResponseDto signUpUser(SignUpRequestDto signUpRequests);
    LoginResponseDto loginUser(LoginRequestDto loginRequestDto) throws BadCredentialsException;
    EditUserResponseDto updateUser(EditUserRequestDto editUserRequestDto, String id);
    void checkLoginStatus(String userId);
}
