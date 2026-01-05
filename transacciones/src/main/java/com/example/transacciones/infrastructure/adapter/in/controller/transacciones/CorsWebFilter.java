package com.example.transacciones.infrastructure.adapter.in.controller.transacciones;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        System.out.println("🔥 CORS Filter aplicado: " + exchange.getRequest().getMethod() + " " + exchange.getRequest().getPath());

        exchange.getResponse().getHeaders().add("Access-Control-Allow-Origin", "http://localhost:4200");
        exchange.getResponse().getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        exchange.getResponse().getHeaders().add("Access-Control-Allow-Headers", "*");
        exchange.getResponse().getHeaders().add("Access-Control-Max-Age", "3600");

        if (exchange.getRequest().getMethod().toString().equals("OPTIONS")) {
            System.out.println("✅ OPTIONS - Respondiendo 200");
            exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.OK);
            return Mono.empty();
        }

        return chain.filter(exchange);
    }
}
