package com.org.retailstore.service;

import com.org.retailstore.dto.BillDto;
import com.org.retailstore.dto.OrderDto;

public interface DiscountsService {

    BillDto calculateBill(OrderDto orderDto);
}
