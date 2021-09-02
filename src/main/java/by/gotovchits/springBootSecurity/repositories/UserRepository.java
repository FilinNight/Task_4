package by.gotovchits.springBootSecurity.repositories;

import by.gotovchits.springBootSecurity.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
