package br.com.jwtimpl.modules.user.dto;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class UserDtoLogin {
    private String email;
    private String password;
}
