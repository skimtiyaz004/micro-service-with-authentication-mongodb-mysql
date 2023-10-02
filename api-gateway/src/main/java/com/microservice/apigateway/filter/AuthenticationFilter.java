package com.microservice.apigateway.filter;

import com.microservice.apigateway.config.WebClientConfig;
import com.microservice.apigateway.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AddHeaderConfig> {

    @Autowired
    private RouteValidator routeValidator;
    @Autowired
    private RestTemplate template;
    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(AddHeaderConfig.class);
    }
    @Override
    public GatewayFilter apply(AddHeaderConfig config) {
        return (exchange, chain) -> {
            if(routeValidator.isSecured.test(exchange.getRequest())){
                // header contains token or not
                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                    throw new RuntimeException("Missing Authorization Header");
                }
                String authHeader=exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if(authHeader!=null && authHeader.startsWith("Bearer ")){
                    authHeader=authHeader.substring(7);
                }
                try{
//                    template.getForObject("http://identity-service//validate?token="+authHeader,String.class);
                    jwtUtil.verifyJwtToken(authHeader);
                    System.out.println("Working In AuthFilter");

                }catch (Exception e){
                    System.out.println(e.getMessage());
                    throw new RuntimeException(e.getMessage());
                }
            }

            return chain.filter(exchange);
        };
    }


}
