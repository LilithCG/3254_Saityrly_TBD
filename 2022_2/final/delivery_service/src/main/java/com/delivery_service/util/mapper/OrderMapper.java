package com.delivery_service.util.mapper;

import com.delivery_service.postgres.entity.Order;
import com.delivery_service.dto.responce.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OrderMapper {
    @Mapping(target = "id", source = "order.id")
    @Mapping(target = "cafeId", source = "order.cafe.id")
    @Mapping(target = "cafeName", source = "order.cafe.name")
    @Mapping(target = "ownerId", source = "order.user.id")
    @Mapping(target = "ownerRef", source = "order.user.ref")
    OrderResponse orderToResponse(Order order);
}
