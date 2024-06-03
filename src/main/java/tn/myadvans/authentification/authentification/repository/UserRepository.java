package tn.myadvans.authentification.authentification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.myadvans.authentification.authentification.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

}
