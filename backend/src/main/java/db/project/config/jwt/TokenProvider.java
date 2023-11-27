package db.project.config.jwt;

import db.project.domain.User;
import db.project.service.UserDetailService;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenProvider {
    private final JwtProperties jwtProperties;

    private final UserDetailService userDetailService;

    private long tokenValidTime = 30 * 60 * 1000L; //30분

    private String key;
    @PostConstruct
    protected void init() {
        key = Base64.getEncoder().encodeToString(jwtProperties.getSecretKey().getBytes());
    }
   public String createToken(String id, long expiration) {
       Date now = new Date();

       return Jwts.builder()
               .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
               .setIssuer(jwtProperties.getIssuer())
               .setIssuedAt(now)
               .setExpiration(new Date(now.getTime() + expiration))
               .setSubject(id)
               .signWith(SignatureAlgorithm.HS256, key)
               .compact();
   }

   public boolean validToken(String token, HttpServletRequest request) {
       try {
           Jwts.parser()
                   .setSigningKey(key)
                   .parseClaimsJws(token);
           return true;
       } catch (ExpiredJwtException ex) {
           request.setAttribute("exception", "만료된 토큰");
           return false;
       }
   }
   public Authentication getAuthentication(String token) {
       Claims claims = getClaims(token);
       UserDetails userDetails = userDetailService.loadUserByUsername(claims.getSubject());
       return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
   }
   private Claims getClaims(String token) {
       return Jwts.parser()
               .setSigningKey(key)
               .parseClaimsJws(token)
               .getBody();
   }
}
