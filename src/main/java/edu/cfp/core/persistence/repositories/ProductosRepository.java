package edu.cfp.core.persistence.repositories;

import edu.cfp.core.persistence.entities.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductosRepository extends JpaRepository<ProductoEntity,Integer> {
}

