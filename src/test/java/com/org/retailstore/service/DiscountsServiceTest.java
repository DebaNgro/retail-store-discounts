package com.org.retailstore.service;

import com.org.retailstore.dto.BillDto;
import com.org.retailstore.dto.OrderDto;
import com.org.retailstore.entity.DiscountRule;
import com.org.retailstore.entity.Product;
import com.org.retailstore.entity.User;
import com.org.retailstore.enums.DiscountKey;
import com.org.retailstore.enums.ProductType;
import com.org.retailstore.enums.UserType;
import com.org.retailstore.repository.DiscountRulesRepository;
import com.org.retailstore.repository.ProductRepository;
import com.org.retailstore.repository.UserRepository;
import com.org.retailstore.service.impl.DiscountsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class DiscountsServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private DiscountRulesRepository discountRulesRepository;

    @InjectMocks
    private DiscountsServiceImpl discountsService;

    @BeforeEach
    public void init() {
        discountsService = new DiscountsServiceImpl(userRepository, productRepository, discountRulesRepository);
    }

    @ParameterizedTest
    @CsvSource(value = {"1001, 49560.08", "1002, 44635.47", "1005, 47097.775", "1006, 34786.25"})
    public void testCalculateBill(long userId, double billAmt) {
        final User user = getUser(userId);
        final List<Product> products = getProducts();
        final List<DiscountRule> rules = getDiscountRules();
        final OrderDto orderDto = OrderDto.builder()
                .userId(userId)
                .productIds(List.of(101L, 102L, 103L, 104L, 105L))
                .build();

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user));
        Mockito.when(productRepository.findAllById(Mockito.anyCollection())).thenReturn(products);
        Mockito.when(discountRulesRepository.findAll()).thenReturn(rules);

        final BillDto calculatedBill = discountsService.calculateBill(orderDto);

        assertNotNull(calculatedBill);
        assertEquals(billAmt, calculatedBill.getBillAmount());

        Mockito.verify(userRepository, Mockito.times(1)).findById(ArgumentMatchers.isA(Long.class));
        Mockito.verify(productRepository, Mockito.times(1)).findAllById(ArgumentMatchers.anyList());
        Mockito.verify(discountRulesRepository, Mockito.times(1)).findAll();
        Mockito.reset(userRepository, productRepository, discountRulesRepository);
    }

    private List<DiscountRule> getDiscountRules() {
        DiscountRule rule1 = DiscountRule.builder()
                .discountRuleId(201L)
                .discountAmt(0)
                .discountPercentage(30)
                .isDiscount(true)
                .retentionYears(0)
                .typeKey(DiscountKey.USERTYPE)
                .typeValue("EMPLOYEE")
                .build();
        DiscountRule rule2 = DiscountRule.builder()
                .discountRuleId(202L)
                .discountAmt(0)
                .discountPercentage(10)
                .isDiscount(true)
                .retentionYears(0)
                .typeKey(DiscountKey.USERTYPE)
                .typeValue("AFFILIATE")
                .build();
        DiscountRule rule3 = DiscountRule.builder()
                .discountRuleId(203L)
                .discountAmt(0)
                .discountPercentage(5)
                .isDiscount(true)
                .retentionYears(2)
                .typeKey(DiscountKey.USERTYPE)
                .typeValue("CUSTOMER")
                .build();
        DiscountRule rule4 = DiscountRule.builder()
                .discountRuleId(204L)
                .discountAmt(0)
                .discountPercentage(0)
                .isDiscount(false)
                .retentionYears(0)
                .typeKey(DiscountKey.PRODUCTTYPE)
                .typeValue("GROCERY")
                .build();
        DiscountRule rule5 = DiscountRule.builder()
                .discountRuleId(205L)
                .discountAmt(5)
                .discountPercentage(0)
                .isDiscount(true)
                .retentionYears(0)
                .typeKey(DiscountKey.AMOUNT)
                .typeValue("100")
                .build();
        return List.of(rule1, rule2, rule3, rule4, rule5);
    }

    private User getUser(long userId) {
        return switch ((int) userId) {
            case 1001 -> User.builder()
                    .userId(userId)
                    .userType(UserType.CUSTOMER)
                    .emailId("test1@test.com")
                    .name("test1 demo")
                    .createdDate(LocalDate.of(2024, 6, 29))
                    .build();
            case 1002 -> User.builder()
                    .userId(userId)
                    .userType(UserType.AFFILIATE)
                    .emailId("test2@test.com")
                    .name("test2 demo")
                    .createdDate(LocalDate.of(2023, 12, 22))
                    .build();
            case 1005 -> User.builder()
                    .userId(userId)
                    .userType(UserType.CUSTOMER)
                    .emailId("test3@test.com")
                    .name("test3 demo")
                    .createdDate(LocalDate.of(2019, 6, 19))
                    .build();
            case 1006 -> User.builder()
                    .userId(userId)
                    .userType(UserType.EMPLOYEE)
                    .emailId("test4@test.com")
                    .name("test4 demo")
                    .createdDate(LocalDate.of(2012, 2, 3))
                    .build();
            default -> throw new IllegalStateException("Unexpected value: " + (int) userId);
        };
    }

    private List<Product> getProducts() {
        Product product1 = Product.builder()
                .productId(101L)
                .productName("Mango")
                .productType(ProductType.GROCERY)
                .price(79.6)
                .build();
        Product product2 = Product.builder()
                .productId(102L)
                .productName("Biryani")
                .productType(ProductType.MISC)
                .price(699.2)
                .build();
        Product product3 = Product.builder()
                .productId(103L)
                .productName("AC")
                .productType(ProductType.ELECTRONICS)
                .price(48990.0)
                .build();
        Product product4 = Product.builder()
                .productId(104L)
                .productName("Curd")
                .productType(ProductType.DAIRY)
                .price(56.9)
                .build();
        Product product5 = Product.builder()
                .productId(105L)
                .productName("Avacado")
                .productType(ProductType.GROCERY)
                .price(235.38)
                .build();
        return List.of(product1, product2, product3, product4, product5);
    }
}
