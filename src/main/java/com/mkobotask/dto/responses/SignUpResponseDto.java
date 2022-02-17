package com.mkobotask.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpResponseDto {
    private String response;
    private String username;
    private String userId;
    private String name;
    private String email;
    private LocalDateTime createdAt;
}
