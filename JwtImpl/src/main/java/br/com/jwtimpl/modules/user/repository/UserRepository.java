package br.com.jwtimpl.modules.user.repository;

import br.com.jwtimpl.modules.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> {

    User findByEmail(String email);
}
