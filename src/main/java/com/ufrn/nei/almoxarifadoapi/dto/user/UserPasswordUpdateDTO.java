package com.ufrn.nei.almoxarifadoapi.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordUpdateDTO {
    @Size(min = 6, max = 6)
    private String recoveryToken;
    @Size(min = 5)
    private String currentPassword;
    @NotBlank
    @Size(min = 5)
    private String newPassword;
    @NotBlank
    @Size(min = 5)
    private String confirmPassword;
}
