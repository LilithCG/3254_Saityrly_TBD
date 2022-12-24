package com.delivery_service.dto.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class CategoryRequest {
    @NotNull(message = "[id] cannot be empty")
    @Min(value = 1, message = "[orderId] cannot be less the 0")
    private Long id;

    private String name;
}
