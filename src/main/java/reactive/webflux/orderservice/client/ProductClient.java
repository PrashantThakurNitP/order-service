package reactive.webflux.orderservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactive.webflux.orderservice.dto.ProductDto;
import reactor.core.publisher.Mono;



@Service
public class ProductClient {
	//this product Client is response for sending request to product service and getting response
	
	private final WebClient webClient;
	//via constructor we will inject property
	public ProductClient(@Value("${product.service.url}") String url) {
		//fetch url from application.property in url
		this.webClient= WebClient.builder()
		.baseUrl(url)
		.build();
	}

	//this client is responsible for sending request and getting response
	public Mono<ProductDto> getProductById(final String productId) {
		//someone give id we will send info to product service to get product information
		return this.webClient
		.get()//send get request
		.uri("{id}",productId)//url is  base url + product id
		.retrieve()
		.bodyToMono(ProductDto.class);// we are expecting response product dto
		
	}


}
