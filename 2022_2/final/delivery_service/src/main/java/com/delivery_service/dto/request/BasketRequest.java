package com.delivery_service.dto.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class BasketRequest {
    @NotNull(message = "[orderId] cannot be empty")
    @Min(value = 1, message = "[orderId] cannot be less the 0")
    private Long orderId;

    @NotNull(message = "[chatId] cannot be empty")
    @Min(value = 1, message = "[chatId] cannot be less the 0")
    private Long chatId;

    @NotNull(message = "[foodId] cannot be empty")
    @Min(value = 1, message = "[foodId] cannot be less the 0")
    private Long foodId;

    @NotNull(message = "[count] cannot be empty")
    @Min(value = 1, message = "[count] cannot be less the 0")
    private Integer count;
}
