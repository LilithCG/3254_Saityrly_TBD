package com.delivery_service.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasketResponse {

    private Long id;

    private Long foodId;

    private String foodName;

    private Float foodPrice;

    private String foodLink;

    private Long orderId;

    private String chatId;

    private String userRef;

    private Long categoryId;
}
