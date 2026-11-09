package com.technu.erp.filter;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.technu.erp.jwt.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.technu.erp.constant.SecurityConstant.TOKEN_PREFIX;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private final Logger logger= LoggerFactory.getLogger(AuthorizationHeaderFilter.class);

    public AuthorizationHeaderFilter(){
        super(Config.class);
    }

    public static class Config{

        // TODO: 19/03/22  put configuration properties here
    }

    @Override
    public GatewayFilter apply(Config config) {

        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                return onError(exchange,HttpStatus.UNAUTHORIZED.getReasonPhrase(), HttpStatus.UNAUTHORIZED);
            }
            String authHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwtToken = authHeader.replace(TOKEN_PREFIX,"");

            if(!isJwtValid(jwtToken)){
                return onError(exchange,"JWT Token is not valid",HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        });
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        logger.error(err);
        return response.setComplete();
    }

    private boolean isJwtValid(String jwtToken){
        try {
            String subject = jwtTokenProvider.getSubject(jwtToken);
            if (subject == null || subject.isEmpty())
                return false;
            return jwtTokenProvider.isTokenValid(jwtToken);
        }catch(JWTDecodeException exception){
            logger.error(exception.getMessage());
            return false;
        }
    }
}
