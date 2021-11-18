package reactive.webflux.orderservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactive.webflux.orderservice.dto.PurchaseOrderResponseDto;
import reactive.webflux.orderservice.entity.PurchaseOrder;
import reactive.webflux.orderservice.repository.PurchaseOrderRepository;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactive.webflux.orderservice.util.EntityDtoUtil;

@Service
public class OrderQueryService {
	
	@Autowired 
	PurchaseOrderRepository orderRepository;
	
	public Flux<PurchaseOrderResponseDto> getProductByUserId(int userId) {
		// List<PurchaseOrder> purchaseOrders = this.orderRepository.findByUserId(userId);// it will bw executed immediately
		//we want to do thing lazily
	return Flux.fromStream(() -> this.orderRepository.findByUserId(userId).stream()/* we will get purchase order for usrer id*/)
		//we are building pipeline , only when it is subscribed it will be executed
		//we are getting flux of purchase order
		.map(EntityDtoUtil::getPurchaseOrderResponseDto)//we get purchase order response dto
		//as first line is blocking we add below line 
		.subscribeOn(Schedulers.boundedElastic());
	}
}
