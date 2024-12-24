package org.example.tradingcompany.repositories;

import org.example.tradingcompany.models.ProductGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductGroupRepository extends JpaRepository<ProductGroup, Integer> {
}