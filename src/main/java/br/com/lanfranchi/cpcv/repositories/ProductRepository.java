package br.com.lanfranchi.cpcv.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.lanfranchi.cpcv.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	@Query("select p from Product p where lower(p.name) like %?1%")
	List<Product> findByNameIgnoreCase(String name);
	
}
