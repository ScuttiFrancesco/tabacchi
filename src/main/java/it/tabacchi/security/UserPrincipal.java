package it.tabacchi.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserPrincipal extends User {
    private final Long id;

    public UserPrincipal(it.tabacchi.user.User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getEmail(), user.getPassword(), true, true, true, true, authorities);
        this.id = user.getId();
    }

    public Long getId() { return id; }
}
