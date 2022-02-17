package com.mkobotask.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditUserRequestDto {

    @Email(message = "Enter a valid Email")
    private String email;

    @Size(min = 2, message = "Username must have a minimum of 2 characters and above")
    private String username;

    @Size(min = 8, message = "Password must be upto 8 characters long and must contain at " +
            "least one capital letter, one small letter and special character")
    private String password;

    @Size(min = 2, message = "Name must have a minimum of 2 characters and above")
    private String name;
}
