package com.freelanceplatform.backend.dto.request;

import com.freelanceplatform.backend.enums.ClientType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;

    @NotNull
    private String role; // "FREELANCER" or "CLIENT"

    // for freelancers
    private String profileTitle;
    private String bio;

    // for clients
    private ClientType clientType;
    private String companyName;
    private String taxId;
}
