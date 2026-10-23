package edu.cfp.core.persistence.repositories;

import edu.cfp.core.persistence.entities.ProveedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProveedorRepository extends JpaRepository<ProveedorEntity, Long> {
}
