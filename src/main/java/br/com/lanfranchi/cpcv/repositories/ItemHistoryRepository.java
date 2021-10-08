package br.com.lanfranchi.cpcv.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lanfranchi.cpcv.model.ItemHistory;
import br.com.lanfranchi.cpcv.model.Order;

@Repository
public interface ItemHistoryRepository extends JpaRepository<ItemHistory, Integer> {

	List<ItemHistory> findByOrder(Order order);
	
}
