package com.example.wsb.security.config;
import com.example.wsb.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "6E3272357538782F413F4428472B4B6250645367566B59703373367639792442";

    public String extractUsername(String jwt)
    {
        return extractClaim(jwt,Claims::getSubject);
    }

    public <T> T extractClaim(String jwt, Function<Claims,T> claimsResolver){
        final  Claims claims = extractAllClaims(jwt);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails){
        Map<String, Object> extraClaims = new HashMap<>();

        extraClaims.put("scopes", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList());

        if (userDetails instanceof User) {
            Integer userId = ((User) userDetails).getUserId();
            extraClaims.put("userId", userId);
        }

        return generateToken(extraClaims,userDetails);
    }

    public String generateToken(Map<String,Object> extraClaims, UserDetails userDetails){
         return Jwts.builder()
                 .setClaims(extraClaims)
                 .setSubject(userDetails.getUsername())
                 .setIssuedAt(new Date(System.currentTimeMillis()))
                 .setExpiration(new Date(System.currentTimeMillis() + 10000000 * 60 * 24))
                 .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                 .compact();
    }

    public boolean isTokenValid(String jwt,UserDetails userDetails){
        final String username = extractUsername(jwt);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(jwt);
    }

    private boolean isTokenExpired(String jwt){
        return extractExpiration(jwt).before(new Date());
    }

    private Date extractExpiration(String jwt){
         return extractClaim(jwt,Claims::getExpiration);
    }
    private Claims extractAllClaims(String jwt){
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(jwt).getBody();
    }

    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
