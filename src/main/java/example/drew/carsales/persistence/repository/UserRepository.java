package example.drew.carsales.persistence.repository;

import example.drew.carsales.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    void deleteByUsername(String username);
    Boolean existsByUsernameOrEmail(String username, String email);
    Optional<User> findByActivationCode(String code);

}
