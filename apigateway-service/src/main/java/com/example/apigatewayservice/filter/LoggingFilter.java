package com.example.apigatewayservice.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {
	public LoggingFilter() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		// return (exchange, chain) -> {
		// 	ServerHttpRequest request = exchange.getRequest();
		// 	ServerHttpResponse response = exchange.getResponse();
		//
		// 	log.info("Logging Filter baseMessage: {}", config.getBaseMessage());
		//
		// 	if (config.isPreLogger()) {
		// 		log.info("Logging Filter Start: request id -> {}", request.getId());
		// 	}
		//
		// 	return chain.filter(exchange).then(Mono.fromRunnable(() -> {
		// 		if (config.isPostLogger()) {
		// 			log.info("Logging Filter End: response code -> {}", response.getStatusCode());
		// 		}
		// 	}));
		// };
		GatewayFilter filter = new OrderedGatewayFilter((exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			ServerHttpResponse response = exchange.getResponse();

			log.info("Logging Filter baseMessage: {}", config.getBaseMessage());

			if (config.isPreLogger()) {
				log.info("Logging PRE Filter: request id -> {}", request.getId());
			}

			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				if (config.isPostLogger()) {
					log.info("Logging POST Filter: response code -> {}", response.getStatusCode());
				}
			}));
		}, Ordered.LOWEST_PRECEDENCE); // 이 Ordered.HIGHEST_PRECEDENCE 옵션으로 로깅 순서를 가장 먼저로 설정 가능

		return filter;
	}

	@Data
	public static class Config {
		// 아래의 변수들은 yml 파일에서 설정
		private String baseMessage;
		private boolean preLogger;
		private boolean postLogger;
	}
}
