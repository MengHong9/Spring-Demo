package org.example.damo.repository;

import org.example.damo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE "+
            "(:name IS NULL OR LOWER(p.productName) LIKE %:name%) AND"+
            "(:maxPrice IS NULL OR p.price <= :maxPrice) AND"+
            "(:minPrice IS NULL OR p.price >= :minPrice)"
    )
    List<Product> findProductwithFilter(
            @Param("name") String name,
            @Param("maxPrice") Double maxPrice,
            @Param("minPrice") Double minPrice
    );
}
