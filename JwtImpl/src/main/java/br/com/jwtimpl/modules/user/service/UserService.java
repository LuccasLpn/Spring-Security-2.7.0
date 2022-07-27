package br.com.jwtimpl.modules.user.service;

import br.com.jwtimpl.modules.user.dto.UserDtoLogin;
import br.com.jwtimpl.modules.user.entity.User;
import br.com.jwtimpl.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    public final UserRepository repository;
    private final PasswordEncoder encoder;

    public User save(User user){
        user.setPassword(encoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public Boolean findByUser(UserDtoLogin user){
        User findByEmail = Optional.ofNullable(repository.findByEmail(user.getEmail()))
                .orElseThrow(() -> new UsernameNotFoundException("Email Not Found " + user.getEmail()));
        return encoder.matches(user.getPassword(), findByEmail.getPassword());
    }

    public User findByEmail(String email){
        return Optional.ofNullable(repository.findByEmail(email))
                .orElseThrow(() -> new UsernameNotFoundException("Email Not Found " + email));
    }

    public Optional<User> findById(Long id){
        return repository.findById(id);
    }

}
