package reactive.webflux.orderservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.ToString;
import reactive.webflux.orderservice.dto.OrderStatus;

@Entity
@Data
@ToString
public class PurchaseOrder {
	@Id
	@GeneratedValue
	private Integer id;
	private String productId;
	private Integer userId;
	private Integer amount;
	private OrderStatus status;

}
