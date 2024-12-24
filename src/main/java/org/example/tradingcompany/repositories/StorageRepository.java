package org.example.tradingcompany.repositories;

import org.example.tradingcompany.models.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface StorageRepository extends JpaRepository<Storage, Integer> {
}
