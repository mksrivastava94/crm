package com.crm.erp.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.crm.erp.config.security.UserPrincipal;
import com.crm.erp.util.BasicUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.crm.erp.constant.SecurityConstant.*;


@Component
public class JwtTokenProvider {

    @Value("${JWT_SECRET:defaultSecretKeyForLocalDevelopment}")
    private String secret;

    public String generateToken(UserPrincipal userPrincipal){
        String[] claims = getClaimsFromUser(userPrincipal);
        return JWT.create().withIssuer(CRM).withAudience(CRM_ADMINISTRATION)
                .withIssuedAt(new Date()).withSubject(userPrincipal.getUsername()).withArrayClaim(AUTHORITIES,claims)
                .withExpiresAt(new Date(System.currentTimeMillis()+ EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }

    public List<GrantedAuthority> getAuthorities(String token){
        String[] claims = getClaimsFromToken(token);
        return Arrays.stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public Authentication getAuthentication(String username, List<GrantedAuthority> authorities, HttpServletRequest request){
        UsernamePasswordAuthenticationToken userPasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,null,authorities);
        userPasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return userPasswordAuthenticationToken;
    }

    public boolean isTokenValid(String username,String token){
        JWTVerifier verifier = getJWTVerifier();
        return BasicUtil.notEmptyNotNull(username) && !isTokenExpired(verifier,token);
    }

    public String getSubject(String token){
        return getJWTVerifier().verify(token).getSubject();
    }

    private boolean isTokenExpired(JWTVerifier verifier, String token) {
        Date expiration = verifier.verify(token).getExpiresAt();
        return expiration.before(new Date());
    }

    private String[] getClaimsFromToken(String token) {
        JWTVerifier verifier =  getJWTVerifier();
        return verifier.verify(token).getClaim(AUTHORITIES).asArray(String.class);
    }

    private JWTVerifier getJWTVerifier() {
        JWTVerifier verifier;
        try {
            Algorithm algorithm = Algorithm.HMAC512(secret);
            verifier = JWT.require(algorithm).withIssuer(CRM).build();
        }catch (JWTVerificationException ex){
            throw new JWTVerificationException(TOKEN_CANNOT_BE_VERIFIED);
        }
        return  verifier;
    }

    private String[] getClaimsFromUser(UserPrincipal userPrincipal) {
        List<String> authorities = new ArrayList<>();
        for (GrantedAuthority grantedAuthority: userPrincipal.getAuthorities()) {
            authorities.add(grantedAuthority.getAuthority());
        }
        return authorities.toArray(new String[0]);
    }

}
