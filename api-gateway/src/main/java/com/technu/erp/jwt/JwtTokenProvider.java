package com.technu.erp.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.technu.erp.constant.SecurityConstant.TECHNUZONE;
import static com.technu.erp.constant.SecurityConstant.TOKEN_CANNOT_BE_VERIFIED;


@Component
public class JwtTokenProvider {
    @Value("${JWT_SECRET:defaultSecretKeyForLocalDevelopment}")
    private String secret;

    public boolean isTokenValid(String token){
        JWTVerifier verifier = getJWTVerifier();
        return !isTokenExpired(verifier,token);
    }


    private boolean isTokenExpired(JWTVerifier verifier, String token) {
        Date expiration = verifier.verify(token).getExpiresAt();
        return expiration.before(new Date());
    }

    private JWTVerifier getJWTVerifier() {
        JWTVerifier verifier;
        try {
            Algorithm algorithm = Algorithm.HMAC512(secret);
            verifier = JWT.require(algorithm).withIssuer(TECHNUZONE).build();
        }catch (JWTVerificationException ex){
            throw new JWTVerificationException(TOKEN_CANNOT_BE_VERIFIED);
        }
        return  verifier;
    }

    public String getSubject(String token) throws JWTDecodeException {
        return getJWTVerifier().verify(token).getSubject();
    }
}
