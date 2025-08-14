package com.mnj.mobile.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    private String secret;

    private int jwtExpirationInMs;

    private static final String PRIVILEGE = "privilege";

    public static final String USER_NAME = "username";

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Value("${jwt.expirationDateInMs}")
    public void setJwtExpirationInMs(int jwtExpirationInMs) {
        this.jwtExpirationInMs = jwtExpirationInMs;
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    //=======================================
    public Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    /**
     * ==========================================================
     *  this method responsible token convert to data(payload)
     * ==========================================================
     * @param token
     * @param claimsResolver
     * @param <T>
     * @return
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * ===============================================================
     *  this method responsible token convert to data(payload)
     *  ==============================================================
     * @param token
     * @return
     */
    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public String generateToken(UserDetails userDetails) {
        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        claims.put(USER_NAME, userDetails.getUsername() + "");
        claims.put(PRIVILEGE, userDetails.getAuthorities());

        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String,Object> claims,String subject){ //====== subject is username
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, 24);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setExpiration(cal.getTime())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
}

