package edu.cfp.core.persistence.repositories;

import edu.cfp.core.persistence.entities.ProductoProveedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoProveedorRepository extends JpaRepository<ProductoProveedorEntity, Integer> {
}
