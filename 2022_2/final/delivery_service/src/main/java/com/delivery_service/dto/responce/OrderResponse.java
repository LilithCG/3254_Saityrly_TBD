package com.delivery_service.dto.responce;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderResponse {

    private Long id;

    private Long cafeId;

    private String cafeName;

    private Long ownerId;

    private String ownerRef;
}
