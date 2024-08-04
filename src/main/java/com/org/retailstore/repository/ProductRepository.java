package com.org.retailstore.repository;

import com.org.retailstore.entity.Product;
import com.org.retailstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
