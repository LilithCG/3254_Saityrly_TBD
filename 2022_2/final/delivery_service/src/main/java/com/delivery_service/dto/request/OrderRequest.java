package com.delivery_service.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    @NotNull(message = "[isActive] cannot be empty")
    private Boolean isActive;

    @NotNull(message = "[cafeId] cannot be empty")
    @Min(value = 1, message = "[cafeId] cannot be less the 0")
    private Long cafeId;

    @NotNull(message = "[userId] cannot be empty")
    @Min(value = 1, message = "[userId] cannot be less the 0")
    private Long userId;
}
