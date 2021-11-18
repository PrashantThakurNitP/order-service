package reactive.webflux.orderservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import reactive.webflux.orderservice.entity.PurchaseOrder;
@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {
	//since this is jpa it will not return flux or mono
	//it will return list
	List<PurchaseOrder>findByUserId(int userId);

}
