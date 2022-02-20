package com.example.catalogservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.catalogservice.jpa.CatalogEntity;
import com.example.catalogservice.jpa.CatalogRepository;

@Configuration
public class AppConfig {

	@Bean
	public ApplicationRunner applicationRunner() {
		return new ApplicationRunner() {

			@Autowired
			CatalogRepository catalogRepository;

			@Override
			public void run(ApplicationArguments args) throws Exception {
				CatalogEntity catalogEntity = new CatalogEntity();
				catalogEntity.setProductId("CATALOG-001");
				catalogEntity.setProductName("Berlin");
				catalogEntity.setStock(100);
				catalogEntity.setUnitPrice(1500);
				catalogRepository.save(catalogEntity);

				catalogEntity = new CatalogEntity();
				catalogEntity.setProductId("CATALOG-002");
				catalogEntity.setProductName("Tokyo");
				catalogEntity.setStock(110);
				catalogEntity.setUnitPrice(1000);
				catalogRepository.save(catalogEntity);

				catalogEntity = new CatalogEntity();
				catalogEntity.setProductId("CATALOG-003");
				catalogEntity.setProductName("Stockholm");
				catalogEntity.setStock(120);
				catalogEntity.setUnitPrice(2000);
				catalogRepository.save(catalogEntity);
			}
		};
	}
}
