package com.delivery_service.dto.responce;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FoodResponse {

    private Long id;

    private String name;

    private Float price;

    private String imageLink;

    private String productLink;

}
