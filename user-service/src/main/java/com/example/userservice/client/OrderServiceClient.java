package com.example.userservice.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.userservice.vo.ResponseOrder;

@FeignClient(name = "order-service") // Eureka 에 등록된 이름
public interface OrderServiceClient {

	@GetMapping("/order-service/{userId}/orders_ng") // 실제 요청 url
	List<ResponseOrder> getOrders(@PathVariable String userId);
}
