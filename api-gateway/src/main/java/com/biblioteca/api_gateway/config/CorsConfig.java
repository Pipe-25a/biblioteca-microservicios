package com.biblioteca.api_gateway.config;

import org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions;
import org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class GatewayConfig {

    @Bean
    public RouterFunction<ServerResponse> msProductosRoute() {
        return GatewayRouterFunctions.route("ms-productos-route")
                .route(RequestPredicates.path("/api/productos/**"),
                        HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("ms-productos"))
                .filter(FilterFunctions.addRequestHeader("X-Gateway-Source", "api-gateway"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> msPedidosRoute() {
        return GatewayRouterFunctions.route("ms-pedidos-route")
                .route(RequestPredicates.path("/api/pedidos/**"),
                        HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("ms-pedidos"))
                .filter(FilterFunctions.addRequestHeader("X-Gateway-Source", "api-gateway"))
                .build();
    }
}