package com.killiangodet.recette.user.model.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"token"})
public class UserLoginResponseDTO {
    String token;

    public UserLoginResponseDTO(String token){
        this.token = token;
    }
}
