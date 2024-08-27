package com.org.retailstore.service.impl;

import com.org.retailstore.dto.BillDto;
import com.org.retailstore.dto.OrderDto;
import com.org.retailstore.entity.DiscountRule;
import com.org.retailstore.entity.Product;
import com.org.retailstore.entity.User;
import com.org.retailstore.enums.DiscountKey;
import com.org.retailstore.repository.DiscountRulesRepository;
import com.org.retailstore.repository.ProductRepository;
import com.org.retailstore.repository.UserRepository;
import com.org.retailstore.service.DiscountsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class DiscountsServiceImpl implements DiscountsService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final DiscountRulesRepository discountRulesRepository;

    @Override
    public BillDto calculateBill(OrderDto orderDto) {
        AtomicReference<Double> finalBillAmt = new AtomicReference<>((double) 0);
        final Optional<User> optionalUser = userRepository.findById(orderDto.getUserId());
        optionalUser.ifPresent(user -> calculateFinalBillAmt(orderDto, user, finalBillAmt));

        final BillDto billDto = new BillDto();
        billDto.setBillAmount(finalBillAmt.get());
        return billDto;
    }

    private void calculateFinalBillAmt(OrderDto orderDto, User user, AtomicReference<Double> finalBillAmt) {
        final List<Product> productList = productRepository.findAllById(orderDto.getProductIds());
        final List<DiscountRule> discountRules = discountRulesRepository.findAll();

        final List<String> nonDiscountableProducts = getNonDiscountableProducts(discountRules);
        final double discountableAmt = getDiscountableAmt(productList, nonDiscountableProducts);
        final double nonDiscountableAmt = getNonDiscountableAmt(productList, nonDiscountableProducts);
        final AtomicReference<Double> percentageDiscountedAmt = evaluatePercentageDiscountAmt(user, discountRules, discountableAmt);

        discountRules.stream()
                .filter(DiscountRule::isDiscount)
                .filter(rule -> rule.getTypeKey().equals(DiscountKey.AMOUNT))
                .findFirst()
                .ifPresent(rule -> {
                    double totalAmt = discountableAmt + nonDiscountableAmt - percentageDiscountedAmt.get();
                    int discountOnTot = (int) Math.round(totalAmt / Double.parseDouble(rule.getTypeValue()));
                    finalBillAmt.set(totalAmt - (double) (discountOnTot));
                });
    }

    private static List<String> getNonDiscountableProducts(List<DiscountRule> discountRules) {
        return discountRules.stream()
                .filter(rule -> !rule.isDiscount())
                .filter(rule -> rule.getTypeKey().equals(DiscountKey.PRODUCTTYPE))
                .map(DiscountRule::getTypeValue)
                .toList();
    }

    private static AtomicReference<Double> evaluatePercentageDiscountAmt(final User user,
                                                                         final List<DiscountRule> discountRules, final double discountableAmt) {
        final AtomicReference<Double> percentageDiscountedAmt = new AtomicReference<>((double) 0);
        discountRules.stream()
                .filter(DiscountRule::isDiscount)
                .filter(rule -> rule.getTypeKey().equals(DiscountKey.USERTYPE))
                .filter(rule -> rule.getTypeValue().equals(user.getUserType().name()))
                .filter(rule -> user.getCreatedDate().isBefore(LocalDate.now().minusYears(rule.getRetentionYears())))
                .findFirst()
                .ifPresent(rule -> {
                    final int discountPercentage = rule.getDiscountPercentage();
                    percentageDiscountedAmt.set((discountableAmt * discountPercentage) / 100);
                });
        return percentageDiscountedAmt;
    }

    private static double getNonDiscountableAmt(List<Product> productList, List<String> nonDiscountableProducts) {
        return productList.stream()
                .filter(product -> nonDiscountableProducts.contains(product.getProductType().name()))
                .mapToDouble(Product::getPrice)
                .sum();
    }

    private static double getDiscountableAmt(List<Product> productList, List<String> nonDiscountableProducts) {
        return productList.stream()
                .filter(product -> !nonDiscountableProducts.contains(product.getProductType().name()))
                .mapToDouble(Product::getPrice)
                .sum();
    }
}
