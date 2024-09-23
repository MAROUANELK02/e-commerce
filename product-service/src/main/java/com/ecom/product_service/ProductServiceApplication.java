package com.ecom.product_service;

import com.ecom.product_service.entities.Category;
import com.ecom.product_service.entities.Product;
import com.ecom.product_service.enums.ECategory;
import com.ecom.product_service.repositories.CategoryRepository;
import com.ecom.product_service.services.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@EnableFeignClients
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

	//@Bean
	CommandLineRunner commandLineRunner(ProductService productService,
										CategoryRepository categoryRepository) {
		return args -> {
			List<String> words = List.of(
					"TELEVISIONS", "COMPUTERS", "MOBILE_PHONES",
					"HOME_APPLIANCES", "KITCHEN_APPLIANCES", "AUDIO_SYSTEMS",
					"CAMERAS", "GAMING_CONSOLES", "PERSONAL_CARE",
					"ACCESSORIES", "SMART_HOME", "AIR_CONDITIONERS",
					"WASHING_MACHINES", "REFRIGERATORS", "VACUUM_CLEANERS"
			);
			for (String word : words) {
				categoryRepository.save(new Category((long) (words.indexOf(word)+1),
						ECategory.valueOf(word)));
			}

			Optional<Category> category1 = categoryRepository.findByCategoryName(ECategory.TELEVISIONS);
			Optional<Category> category2 = categoryRepository.findByCategoryName(ECategory.REFRIGERATORS);
			Optional<Category> category3 = categoryRepository.findByCategoryName(ECategory.COMPUTERS);

			List<Product> products = List.of(
					new Product(1L, "Samsung TV 32", "Smart TV 4K",
							BigDecimal.valueOf(160.00), category1.orElse(null)),
					new Product(2L, "Siera 40L", "Garantie 2ans, Couleur Blanche",
							BigDecimal.valueOf(350.00), category2.orElse(null)),
					new Product(3L, "Lenovo i5-11ème 12Go 256Go SSD",
							"Avec Cartable et souris gratuitement", BigDecimal.valueOf(550.00),
							category3.orElse(null))
			);
			for (Product product : products) {
				productService.addProduct(product);
			}
		};
	}
}
