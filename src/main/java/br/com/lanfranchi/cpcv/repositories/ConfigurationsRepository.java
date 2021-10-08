package br.com.lanfranchi.cpcv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lanfranchi.cpcv.model.Configurations;

@Repository
public interface ConfigurationsRepository extends JpaRepository<Configurations, Integer> {
	
}
