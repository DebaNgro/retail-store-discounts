package com.org.retailstore.entity;

import com.org.retailstore.enums.DiscountKey;
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
@Table(name = "discount_rules")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiscountRule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="DISCOUNT_RULE_ID")
    private Long discountRuleId;

    @Column(name="TYPE_KEY", nullable=false)
    @Enumerated(EnumType.STRING)
    private DiscountKey typeKey;

    @Column(name="TYPE_VALUE", nullable=false)
    private String typeValue;

    @Column(name="DISCOUNT_AMT")
    private int discountAmt;

    @Column(name="DISCOUNT_PERCENTAGE")
    private int discountPercentage;

    @Column(name="RETENTION_YEARS")
    private int retentionYears;

    @Column(name="IS_DISCOUNT")
    private boolean isDiscount;
}
