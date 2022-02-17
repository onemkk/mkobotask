package com.mkobotask.dto.responses;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class EditUserResponseDto {
    private boolean userUpdated;
    private String username;
    private String name;
    private String email;
}
