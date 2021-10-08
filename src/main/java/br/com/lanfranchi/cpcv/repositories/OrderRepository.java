package br.com.lanfranchi.cpcv.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lanfranchi.cpcv.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
	
	List<Order> findByCreatedAtBetween(Date inicio, Date fim);
	
}
