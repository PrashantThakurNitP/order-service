package reactive.webflux.orderservice.dto;
//created
//PurchaseOrderResponseDto and PurchaseOrderRequestDto are created to get request and send response for order srevice
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PurchaseOrderRequestDto {
	
	private Integer userId;
	private String productId;

}
