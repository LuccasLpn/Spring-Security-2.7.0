package br.com.jwtimpl.modules.security.service;

import br.com.jwtimpl.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email){
      return Optional.ofNullable(repository.findByEmail(email))
              .orElseThrow(() -> new UsernameNotFoundException("Email Not Found " + email));
    }
}
