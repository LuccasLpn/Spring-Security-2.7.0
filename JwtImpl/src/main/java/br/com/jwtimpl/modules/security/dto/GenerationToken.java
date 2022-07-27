package br.com.jwtimpl.modules.security.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class GenerationToken implements Serializable {
    private Long id;
    private Long exp;
    private String email;
    private String username;
}
