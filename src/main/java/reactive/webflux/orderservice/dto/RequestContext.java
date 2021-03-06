package reactive.webflux.orderservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RequestContext {
	//it store references of dto
	private PurchaseOrderRequestDto purchaseOrderRequestDto;
	private ProductDto productDto;
	private TransactionRequestDto transactionRequestDto;
	private TransactionResponseDto transactionResponseDto;
	public RequestContext(PurchaseOrderRequestDto purchaseOrderRequestDto) {
	
		this.purchaseOrderRequestDto = purchaseOrderRequestDto;
	}
	
	
}
