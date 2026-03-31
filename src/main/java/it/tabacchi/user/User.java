package it.tabacchi.user;

import java.util.Objects;

import it.tabacchi.config.StringAttributeConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(name = "email", unique = true, nullable = false)
    private String email; // Non criptato per le ricerche

    @Column(nullable = false)
    @Convert(converter = StringAttributeConverter.class)
    private String password;

    @Column(name = "is_temporary_password", nullable = false)
    private Boolean isTemporaryPassword;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cognome")
    private String cognome;

    //@formatter:off
    public User() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Boolean getIsTemporaryPassword() { return isTemporaryPassword; }
    public void setIsTemporaryPassword(Boolean isTemporaryPassword) { this.isTemporaryPassword = isTemporaryPassword; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    //@formatter:on

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User that = (User) o;
        if (this.id != null && that.id != null) return Objects.equals(id, that.id);
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        if (id != null) return id.hashCode();
        if (email != null) return email.hashCode();
        return 31;
    }


}

