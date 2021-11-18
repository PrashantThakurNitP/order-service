package reactive.webflux.orderservice.util;

import org.springframework.beans.BeanUtils;

import reactive.webflux.orderservice.dto.OrderStatus;
import reactive.webflux.orderservice.dto.PurchaseOrderResponseDto;
import reactive.webflux.orderservice.dto.RequestContext;
import reactive.webflux.orderservice.dto.TransactionRequestDto;
import reactive.webflux.orderservice.dto.TransactionStatus;
import reactive.webflux.orderservice.entity.PurchaseOrder;

public class EntityDtoUtil {
	
	public static PurchaseOrderResponseDto getPurchaseOrderResponseDto(PurchaseOrder purchaseOrder) {
		PurchaseOrderResponseDto dto = new PurchaseOrderResponseDto();
		BeanUtils.copyProperties(purchaseOrder, dto);//first argument source second argument target
		//to set field with different name
		dto.setOrderId(purchaseOrder.getId());
		return dto;
	}
	
	public static  void setTransactionRequestDto(RequestContext requestContext) {
		TransactionRequestDto dto = new TransactionRequestDto();
		dto.setUserId(requestContext.getPurchaseOrderRequestDto().getUserId());
		dto.setAmount(requestContext.getProductDto().getPrice());
		requestContext.setTransactionRequestDto(dto);
		//built object and store reference in request context
	}
	
	public static PurchaseOrder getPurchaseOrder(RequestContext requestContext) {
		//by request context we will create purchase order entity
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		purchaseOrder.setUserId(requestContext.getPurchaseOrderRequestDto().getUserId());
		purchaseOrder.setProductId(requestContext.getPurchaseOrderRequestDto().getProductId());
		purchaseOrder.setAmount(requestContext.getProductDto().getPrice());
		//id is auto generate
		//based on transaction response we will set status
		TransactionStatus status = requestContext.getTransactionResponseDto().getStatus();
		OrderStatus orderStatus = TransactionStatus.APPROVED.equals(status) ? OrderStatus.COMPLETED: OrderStatus.FAILED;
		purchaseOrder.setStatus(orderStatus);
		return purchaseOrder;
	
	}
}
