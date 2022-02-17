package com.mkobotask.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mkobotask.dto.requests.EditUserRequestDto;
import com.mkobotask.dto.requests.LoginRequestDto;
import com.mkobotask.dto.requests.SignUpRequestDto;
import com.mkobotask.dto.responses.EditUserResponseDto;
import com.mkobotask.dto.responses.LoginResponseDto;
import com.mkobotask.dto.responses.SignUpResponseDto;
import com.mkobotask.exception.UserServiceException;
import com.mkobotask.models.MyUserDetails;
import com.mkobotask.models.User;
import com.mkobotask.repository.UserRepository;
import com.mkobotask.security.jwt.JwUtil;
import com.mkobotask.utils.Utility;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserServiceImplementation.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplementationTest {
    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwUtil jwUtil;

    @MockBean
    private MyUserDetailsService myUserDetailsService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImplementation userServiceImplementation;

    @MockBean
    private Utility utility;




    @Test
    void testSignUpUser() {
        when(this.utility.generateUserId(anyInt())).thenReturn("42");

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(123L);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setUserId("42");
        user.setUsername("janedoe");
        when(this.userRepository.save((User) any())).thenReturn(user);
        when(this.userRepository.existsByEmail((String) any())).thenReturn(false);
        when(this.passwordEncoder.encode((CharSequence) any())).thenReturn("secret");
        SignUpResponseDto actualSignUpUserResult = this.userServiceImplementation
                .signUpUser(new SignUpRequestDto("jane.doe@example.org", "janedoe", "Name", "iloveyou"));
        assertEquals("janedoe", actualSignUpUserResult.getUsername());
        assertEquals("42", actualSignUpUserResult.getUserId());
        assertEquals("Registratioon Successful for Name", actualSignUpUserResult.getResponse());
        assertEquals("Name", actualSignUpUserResult.getName());
        assertEquals("jane.doe@example.org", actualSignUpUserResult.getEmail());
        verify(this.utility).generateUserId(anyInt());
        verify(this.userRepository).existsByEmail((String) any());
        verify(this.userRepository).save((User) any());
        verify(this.passwordEncoder).encode((CharSequence) any());
    }


    @Test
    void testLoginUser() throws AuthenticationException {
        when(this.myUserDetailsService.loadUserByUsername((String) any())).thenReturn(new MyUserDetails(new User()));
        when(this.jwUtil.generateToken((UserDetails) any())).thenReturn("ABC123");
        when(this.authenticationManager.authenticate((Authentication) any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        LoginResponseDto actualLoginUserResult = this.userServiceImplementation
                .loginUser(new LoginRequestDto("janedoe", "iloveyou"));
        assertEquals("ABC123", actualLoginUserResult.getJwt());
        assertEquals("login Successful", actualLoginUserResult.getLoginmessage());
        verify(this.myUserDetailsService).loadUserByUsername((String) any());
        verify(this.jwUtil).generateToken((UserDetails) any());
        verify(this.authenticationManager).authenticate((Authentication) any());
    }




    @Test
    void testUpdateUser() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(123L);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setUserId("42");
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(123L);
        user1.setName("Name");
        user1.setPassword("iloveyou");
        user1.setUserId("42");
        user1.setUsername("janedoe");
        when(this.userRepository.save((User) any())).thenReturn(user1);
        when(this.userRepository.findUserByUserId((String) any())).thenReturn(ofResult);
        when(this.passwordEncoder.encode((CharSequence) any())).thenReturn("secret");
        EditUserResponseDto actualUpdateUserResult = this.userServiceImplementation
                .updateUser(new EditUserRequestDto("jane.doe@example.org", "janedoe", "iloveyou", "Name"), "42");
        assertEquals("jane.doe@example.org", actualUpdateUserResult.getEmail());
        assertTrue(actualUpdateUserResult.isUserUpdated());
        assertEquals("janedoe", actualUpdateUserResult.getUsername());
        assertEquals("Name", actualUpdateUserResult.getName());
        verify(this.userRepository).findUserByUserId((String) any());
        verify(this.userRepository).save((User) any());
        verify(this.passwordEncoder).encode((CharSequence) any());
    }



    @Test
    void testCheckLoginStatus2() {
        when(this.userRepository.findUserByUserId((String) any())).thenReturn(Optional.empty());
        assertThrows(UserServiceException.class, () -> this.userServiceImplementation.checkLoginStatus("42"));
        verify(this.userRepository).findUserByUserId((String) any());
    }
}

