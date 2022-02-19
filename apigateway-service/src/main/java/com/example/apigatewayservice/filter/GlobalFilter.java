package com.example.apigatewayservice.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

// yml 파일에 등록하지 않아도 적용됨
// GlobalFilter는 가장 먼저 실행되고 가장 마지막에 끝남
@Component
@Slf4j
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {
	public GlobalFilter() { super(Config.class);}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			ServerHttpResponse response = exchange.getResponse();

			log.info("Global Filter baseMessage: {}", config.getBaseMessage());

			if (config.isPreLogger()) {
				log.info("Global Filter Start: request id -> {}", request.getId());
			}

			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				if (config.isPostLogger()) {
					log.info("Global Filter End: response code -> {}", response.getStatusCode());
				}
			}));
		};
	}

	@Data
	public static class Config {
		// 아래의 변수들은 yml 파일에서 설정
		private String baseMessage;
		private boolean preLogger;
		private boolean postLogger;
	}
}
