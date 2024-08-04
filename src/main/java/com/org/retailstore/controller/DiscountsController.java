package com.org.retailstore.controller;

import com.org.retailstore.dto.BillDto;
import com.org.retailstore.dto.OrderDto;
import com.org.retailstore.service.DiscountsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/calculate")
@AllArgsConstructor
@Slf4j
public class DiscountsController {

    private DiscountsService discountsService;

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
