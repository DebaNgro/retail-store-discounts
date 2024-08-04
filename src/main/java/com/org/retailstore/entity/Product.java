package com.org.retailstore.entity;

import com.org.retailstore.enums.ProductType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="PRODUCT_ID")
    private Long productId;

    @Column(name="PRODUCT_NAME", nullable=false)
    private String productName;

    @Column(name="PRODUCT_TYPE", nullable=false)
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @Column(name="PRICE", nullable=false)
    private Double price;
}
