package it.tabacchi.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserCreationRequest {

    @Email(message = "Email non valida")
    @NotBlank(message = "L'email è obbligatoria")
    private String email;

     // @formatter:off
    public UserCreationRequest() {}
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
     // @formatter:on

}
