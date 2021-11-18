package reactive.webflux.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
		//we will be developing order-service with webflux and spring data jpa
		//order service will send request to product service to find price of product
		//product service will be responding with price
		
		//then it send request to user service that this user want to purchase product and tell to deduct amount if possible
		//based on account balance order service will update or reject order and then response
	
	}

}
