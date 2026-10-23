package edu.cfp.core.persistence.repositories;

import edu.cfp.core.persistence.entities.FacturaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaRepository extends JpaRepository<FacturaEntity, Integer> {

}
