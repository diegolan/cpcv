package br.com.lanfranchi.cpcv.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.lanfranchi.cpcv.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
	
	@Query("select c from Client c where lower(c.name) like %?1% or lower(c.phone) like %?1%")
	List<Client> findByNameIgnoreCaseOrPhoneIgnoreCase(String name);
	
}
