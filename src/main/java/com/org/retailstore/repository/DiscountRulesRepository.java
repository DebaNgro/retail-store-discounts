package com.org.retailstore.repository;

import com.org.retailstore.entity.DiscountRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRulesRepository extends JpaRepository<DiscountRule, Long> {
}
