package com.killiangodet.recette.user.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserChangeMembershipDTO {

    @Min(value = 1)
    @Max(value = 2)
    Integer membershipId;

}
