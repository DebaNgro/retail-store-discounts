package com.org.retailstore.controller;

import com.org.retailstore.dto.BillDto;
import com.org.retailstore.dto.OrderDto;
import com.org.retailstore.service.DiscountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/calculate")
@RequiredArgsConstructor
@Slf4j
public class DiscountsController {

    private final DiscountsService discountsService;

    @Tag(name = "post", description = "POST methods of Discounts APIs")
    @Operation(summary = "Calculate Bill Amount",
            description = "Calculate the total bill amount based on the order details, including user and product information added to the cart.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = BillDto.class))})
    })
    @PostMapping
    public ResponseEntity<BillDto> calculateBill(@Valid @RequestBody OrderDto orderDto) {
        log.info("Calculate Bill Amount.");
        return ResponseEntity.ok().body(discountsService.calculateBill(orderDto));
    }

    @GetMapping
    public ResponseEntity<?> getTest() {
        log.info("Test API.");
        return ResponseEntity.ok("Default Response");
    }
}
