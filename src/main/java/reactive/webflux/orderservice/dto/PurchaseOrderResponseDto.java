package reactive.webflux.orderservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PurchaseOrderResponseDto {
	
	private Integer orderId;
	private Integer userId;
	private String productId;//userid and product id is common to purchase order request dto
	private Integer amount;
	private OrderStatus status;

}
