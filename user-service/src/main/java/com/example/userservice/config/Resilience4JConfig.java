package com.example.userservice.config;

import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration
public class Resilience4JConfig {

	@Bean
	public Customizer<Resilience4JCircuitBreakerFactory> globalCustomConfiguration() {
		CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
			.failureRateThreshold(4) // 실패율
			.waitDurationInOpenState(Duration.ofMillis(1000)) // CircuitBreaker Open 유지 시간
			.slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED) // 닫힐 때 결과를 기록하는 기반 (시간 or 갯수(default))
			.slidingWindowSize(2) // 시간 or 갯수
			.build();

		TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
			.timeoutDuration(Duration.ofSeconds(4)) // 오류로 간주하는 응답대기 시간
			.build();

		return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
			.timeLimiterConfig(timeLimiterConfig)
			.circuitBreakerConfig(circuitBreakerConfig)
			.build());
	}
}
