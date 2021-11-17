package reactive.webflux.orderservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactive.webflux.orderservice.dto.ProductDto;
import reactor.core.publisher.Mono;



@Service
public class ProductClient {
	
	private final WebClient webClient;
	
	public ProductClient(@Value("${product.service.url}") String url) {
		this.webClient= WebClient.builder()
		.baseUrl(url)
		.build();
	}
	//via constructore we will inject property
	//this client is rewponsible for sending request and getting response
	public Mono<ProductDto> getProductById(final String productId) {
		//someone give id we will send info to product service to get product information
		return this.webClient
		.get()
		.uri("{id}",productId)//base url + product id
		.retrieve()
		.bodyToMono(ProductDto.class);
		
	}


}
