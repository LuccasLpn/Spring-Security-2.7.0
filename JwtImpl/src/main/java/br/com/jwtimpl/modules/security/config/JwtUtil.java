package br.com.jwtimpl.modules.security.config;

import br.com.jwtimpl.modules.security.dto.GenerationToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    private final HashMap<String, Object> hashMap = new HashMap<>();

    public String generationToken(Long id, String username, String email){
        hashMap.put("id", id.toString());
        hashMap.put("username", username);
        hashMap.put("email", email);
        return Jwts
                .builder()
                .setClaims(hashMap)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
    }

    public Claims getClaims(String token){
        try {
            return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Boolean isTokenValid(String token) throws JsonProcessingException {
        Claims claims = getClaims(token);
        ObjectMapper objectMapper = new ObjectMapper();
        String extrationClaims = objectMapper.writeValueAsString(claims);
        GenerationToken generationToken = objectMapper.readValue(extrationClaims, GenerationToken.class);
        Date now = new Date(System.currentTimeMillis());
        if (generationToken == null) return false;
        assert claims != null;
        return now.before(claims.getExpiration());
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);
        assert claims != null;
        return claims.get("username").toString();
    }
}
