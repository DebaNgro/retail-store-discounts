package com.org.retailstore.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {

    @NotNull(message = "UserId is mandatory")
    private Long userId;

    @NotEmpty(message = "Product Ids cannot be Empty")
    private List<Long> productIds;
}
