package reactive.webflux.orderservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactive.webflux.orderservice.client.ProductClient;
import reactive.webflux.orderservice.client.UserClient;
import reactive.webflux.orderservice.dto.PurchaseOrderRequestDto;
import reactive.webflux.orderservice.dto.PurchaseOrderResponseDto;
import reactive.webflux.orderservice.dto.RequestContext;
import reactive.webflux.orderservice.repository.PurchaseOrderRepository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactive.webflux.orderservice.util.EntityDtoUtil;

@Service
public class OrderFulfillment {
	@Autowired
	private ProductClient productClient;
	
	@Autowired
	private PurchaseOrderRepository orderRepository;
	
	@Autowired
	private UserClient userClient;
	
	public Mono<PurchaseOrderResponseDto>processOrder(Mono<PurchaseOrderRequestDto> requestDtoMono){
		return requestDtoMono.map(RequestContext::new)
		//we have product id and userid we can call product service to get details
		//once we get request context we will use flatmap
		.flatMap(this::productRequestResponse)
		.doOnNext(EntityDtoUtil::setTransactionRequestDto)
		.flatMap(this::userRequestResponse)
		.map(EntityDtoUtil::getPurchaseOrder)//to get purchase order entity object
		.map(po->this.orderRepository.save(po))// saved in database and we have purchase order entity with
		
		// or .map(this.orderRepository::save) by method refernce
		//this is blocking
		//now we need purchase order response dto
		.map(EntityDtoUtil::getPurchaseOrderResponseDto)
		.subscribeOn(Schedulers.boundedElastic());// we use this as . save is blocking
		//it will not effect event loop thread , ie it will not be blocked
		
		//send transaction request to user service to  deduct amount
		//so we have to build transaction request dto
		
		
	//	so far we have built request context and made request to product service to get product information 
	//then we have sent request to user service to deduct amount
		
		//then if amount deducted then we can give the product , if amount is not enough then cannot deliver
		//by using request context object we are going to create purchase order entity
	
	}
	private Mono<RequestContext> productRequestResponse(RequestContext rc) {
		return this.productClient.getProductById(rc.getPurchaseOrderRequestDto().getProductId())
		.doOnNext(rc::setProductDto)//when it emit product dto we will set product dto in context
		.thenReturn(rc);
	}
	
	private Mono<RequestContext>userRequestResponse(RequestContext rc){
		 return this.userClient.authorizeTransaction(rc.getTransactionRequestDto())
		.doOnNext(rc::setTransactionResponseDto)
		.thenReturn(rc);
	}
	
	

}
