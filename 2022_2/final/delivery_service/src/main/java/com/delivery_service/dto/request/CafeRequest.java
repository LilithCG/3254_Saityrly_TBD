package com.delivery_service.dto.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class CafeRequest {
    @NotNull(message = "[orderId] cannot be empty")
    @Min(value = 1, message = "[orderId] cannot be less the 0")
    private Long cafeId;

    @NotNull(message = "[name] cannot be empty")
    private String name;
}
