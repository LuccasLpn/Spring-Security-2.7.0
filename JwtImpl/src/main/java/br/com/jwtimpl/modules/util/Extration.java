package br.com.jwtimpl.modules.util;

import br.com.jwtimpl.modules.security.dto.GenerationToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;


public class Extration {

    public Long extrationId(Claims claims) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String extrationClaims = objectMapper.writeValueAsString(claims);
        GenerationToken generationToken = objectMapper.readValue(extrationClaims, GenerationToken.class);
        return generationToken.getId();
    }
}
