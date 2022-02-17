package com.mkobotask.dto.requests;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SignUpRequestDto {

    @Email(message = "Enter a valid Email")
    private String email;

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 2, message = "Username must have a minimum of 2 characters and above")
    private String username;

    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, message = "Name must have a minimum of 2 characters and above")
    private String name;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must be upto 8 characters long and must contain at " +
            "least one capital letter, one small letter and special character")
    private String password;

}
