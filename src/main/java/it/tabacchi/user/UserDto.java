package it.tabacchi.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import it.tabacchi.enums.auth.Role;
import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    
    private Long id;
    private String email;
    private String nome;
    private String cognome;

    //@formatter:off
    public UserDto() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
     //@formatter:on
    
}
