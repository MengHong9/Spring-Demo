package org.example.damo.repository;

import org.example.damo.entity.Stock;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import jakarta.persistence.LockModeType;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    // SELECT * FROM stocks WHERE product_id IN (1,3) ORDER BY createdAT
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Stock> findByProductIdIn(List<Long> productIds , Sort createdAt);
}
