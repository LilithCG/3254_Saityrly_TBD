package com.delivery_service.dto.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class FoodRequest {
    @NotNull(message = "[cafeId] cannot be empty")
    @Min(value = 1, message = "[cafeId] cannot be less the 0")
    private Long cafeId;

    @NotNull(message = "[categoryId] cannot be empty")
    @Min(value = 1, message = "[categoryId] cannot be less the 0")
    private Long categoryId;

    private String name;

    private Float price;

    private String imageLink;

    private String productLink;

    private Boolean isActive;
}
