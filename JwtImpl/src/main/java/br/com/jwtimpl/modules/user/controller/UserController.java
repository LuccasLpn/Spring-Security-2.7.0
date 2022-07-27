package br.com.jwtimpl.modules.user.controller;

import br.com.jwtimpl.modules.security.config.JwtUtil;
import br.com.jwtimpl.modules.user.dto.UserDtoLogin;
import br.com.jwtimpl.modules.user.entity.User;
import br.com.jwtimpl.modules.user.service.UserService;
import br.com.jwtimpl.modules.util.Extration;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    public final UserService service;

    public final JwtUtil jwtUtil;

    @PostMapping("/save")
    public ResponseEntity<User> save(@RequestBody User user){
        User save = service.save(user);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody UserDtoLogin user, HttpServletResponse request){
        Boolean byUser = service.findByUser(user);
        User byEmail = service.findByEmail(user.getEmail());
        String token = jwtUtil.generationToken(byEmail.getId(),
                byEmail.getUsername(),
                byEmail.getEmail());
        if (byUser){
            request.addHeader("Authorization", "Bearer "+token);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/findById")
    public ResponseEntity<Optional<User>> findById(@RequestHeader(value = "Authorization") String token) throws JsonProcessingException {
        Claims claims = jwtUtil.getClaims(token.replace("Bearer ", ""));
        Extration extration = new Extration();
        Long id = extration.extrationId(claims);
        Optional<User> byId = service.findById(id);
        return new ResponseEntity<>(byId,HttpStatus.OK);
    }
}
