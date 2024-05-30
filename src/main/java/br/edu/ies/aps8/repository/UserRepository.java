package br.edu.ies.aps8.repository;

import br.edu.ies.aps8.model.Role;
import br.edu.ies.aps8.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    List<User> findByRoles(Role roles);
}
