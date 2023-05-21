package com.killiangodet.recette.user.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserLoginDTO {
    @Email
    private String username;

    @Size(min = 8)
    private String password;

    public UserLoginDTO(String email, String password){
        this.username = email;
        this.password = password;
    }
}
