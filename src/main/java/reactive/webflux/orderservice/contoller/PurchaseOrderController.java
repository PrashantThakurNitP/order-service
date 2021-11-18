package reactive.webflux.orderservice.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactive.webflux.orderservice.dto.PurchaseOrderRequestDto;
import reactive.webflux.orderservice.dto.PurchaseOrderResponseDto;
import reactive.webflux.orderservice.service.OrderFulfillment;
import reactive.webflux.orderservice.service.OrderQueryService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("order")
public class PurchaseOrderController {
	
	@Autowired
	private OrderFulfillment orderFullfillmentService;
	
	@Autowired
	private OrderQueryService queryService;
	
	@PostMapping
	public Mono<PurchaseOrderResponseDto>order(@RequestBody Mono<PurchaseOrderRequestDto>requestDtoMono){
		return this.orderFullfillmentService.processOrder(requestDtoMono);
		
	}
	@GetMapping("user/{UserId}")
	public Flux<PurchaseOrderResponseDto>getOrdersByUserId(@PathVariable int userId){
		return this.queryService.getProductByUserId(userId);
	}
	

}
