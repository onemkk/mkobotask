package com.mkobotask.services;

import com.mkobotask.dto.requests.EditUserRequestDto;
import com.mkobotask.dto.requests.LoginRequestDto;
import com.mkobotask.dto.requests.SignUpRequestDto;
import com.mkobotask.dto.responses.EditUserResponseDto;
import com.mkobotask.dto.responses.LoginResponseDto;
import com.mkobotask.dto.responses.SignUpResponseDto;
import com.mkobotask.exception.ExceptionMessage;
import com.mkobotask.exception.UserServiceException;
import com.mkobotask.models.User;
import com.mkobotask.repository.UserRepository;
import com.mkobotask.security.jwt.JwUtil;
import com.mkobotask.services.serviceInterFaces.UserService;
import com.mkobotask.utils.Utility;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Locale;

@Transactional
@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService userDetailsService;
    private final JwUtil jwUtil;
    private final Utility util;

    public UserServiceImplementation(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, MyUserDetailsService userDetailsService, JwUtil jwUtil, Utility util) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwUtil = jwUtil;
        this.util = util;
    }

    @Override
    public SignUpResponseDto signUpUser(SignUpRequestDto signUpRequests){
        SignUpResponseDto messageResponse = new SignUpResponseDto();
        if (userRepository.existsByEmail(signUpRequests.getUsername().toLowerCase(Locale.ROOT))) {
            messageResponse.setResponse(ExceptionMessage.RECORD_ALREADY_EXISTS.getMessage());
            return messageResponse;
        }

        User user = new User(signUpRequests.getEmail(), signUpRequests.getUsername(), signUpRequests.getName(),
                passwordEncoder.encode(signUpRequests.getPassword()));

        user.setUserId(util.generateUserId(8));
        userRepository.save(user);
        messageResponse.setResponse("Registratioon Successful for " + user.getName());
        messageResponse.setCreatedAt(LocalDateTime.now());
        messageResponse.setUsername(user.getUsername());
        messageResponse.setEmail(user.getEmail());
        messageResponse.setName(user.getName());
        messageResponse.setUserId(user.getUserId());

        return messageResponse;

    }

    @Override
    public LoginResponseDto loginUser(LoginRequestDto loginRequestDto) throws BadCredentialsException{

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),
                    loginRequestDto.getPassword()));
        }
        catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestDto.getUsername());
        final String jwt = jwUtil.generateToken(userDetails);

        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setJwt(jwt);
        loginResponseDto.setLoginmessage("login Successful");
        return loginResponseDto;
    }


    @Override
    public EditUserResponseDto updateUser(EditUserRequestDto editUserRequestDto, String id){

        User oldUserDetails = userRepository.findUserByUserId(id).get();
         oldUserDetails.setName(editUserRequestDto.getName());
         oldUserDetails.setUsername(editUserRequestDto.getUsername());
         oldUserDetails.setEmail(editUserRequestDto.getEmail());
         oldUserDetails.setPassword(passwordEncoder.encode(editUserRequestDto.getPassword()));

        userRepository.save(oldUserDetails);

        EditUserResponseDto editUserResponseDto = new EditUserResponseDto();
        editUserResponseDto.setUserUpdated(true);
        editUserResponseDto.setUsername(oldUserDetails.getUsername());
        editUserResponseDto.setEmail(oldUserDetails.getEmail());
        editUserResponseDto.setName(oldUserDetails.getName());



        return editUserResponseDto;
    }

    @Override
    public void checkLoginStatus(String userId){
        User user = userRepository.findUserByUserId(userId).orElseThrow(() -> new UserServiceException(ExceptionMessage.USER_ALREADY_EXISTS.getMessage()));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(user.getUsername().equals(authentication.getName()))){
            throw new UserServiceException(ExceptionMessage.USER_NOT_AUTHOURIZED.getMessage());
        }
    }



}
