package org.example.tradingcompany.repositories;

import org.example.tradingcompany.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
