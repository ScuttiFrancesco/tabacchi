package it.tabacchi.user;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import it.tabacchi.enums.auth.Role;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByRole(Role role);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

}
