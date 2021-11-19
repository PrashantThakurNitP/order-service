package reactive.webflux.orderservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import reactive.webflux.orderservice.client.ProductClient;
import reactive.webflux.orderservice.client.UserClient;
import reactive.webflux.orderservice.dto.ProductDto;
import reactive.webflux.orderservice.dto.PurchaseOrderRequestDto;
import reactive.webflux.orderservice.dto.PurchaseOrderResponseDto;
import reactive.webflux.orderservice.dto.UserDto;
import reactive.webflux.orderservice.service.OrderFulfillment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class OrderServiceApplicationTests {
	@Autowired private UserClient userClient;
	
	@Autowired private ProductClient productClient;
	
	@Autowired OrderFulfillment fulfillmentService;
	
	@Test
	void contextLoads() {
		
		Flux<PurchaseOrderResponseDto> dtoFlux = Flux.zip(userClient.getAllUsers(), productClient.getAllProducts())
		//get one user and one product one by one from list of user and product
		.map(t->buildDto(t.getT1(),t.getT2()))
		//each item of flux will be a pair
		//t is tuple it contain two item user dto and product dto 
		//using which we build buildDto
		//now process order below
		.flatMap(dto->this.fulfillmentService.processOrder(Mono.just(dto)))
		//process order expect dto in mono format
		.doOnNext(System.out::println);
		
		//to block this to see output we use below
		
		StepVerifier.create(dtoFlux)
		.expectNextCount(4)//expect 4 item, on rerun it may fail due to insufficient amount to purchase
		.verifyComplete();
	}
	private PurchaseOrderRequestDto buildDto(UserDto userDto, ProductDto productDto) {
		PurchaseOrderRequestDto dto = new PurchaseOrderRequestDto();
		dto.setUserId(userDto.getId());
		dto.setProductId(productDto.getId());
		return dto;
	}

}
