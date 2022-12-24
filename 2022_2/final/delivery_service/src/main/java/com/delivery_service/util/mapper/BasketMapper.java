package com.delivery_service.util.mapper;

import com.delivery_service.postgres.entity.Basket;
import com.delivery_service.dto.responce.BasketResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BasketMapper {
    @Mapping(target = "foodId", source = "basket.food.id")
    @Mapping(target = "foodName", source = "basket.food.name")
    @Mapping(target = "foodPrice", source = "basket.food.price")
    @Mapping(target = "foodLink", source = "basket.food.productLink")
    @Mapping(target = "orderId", source = "basket.order.id")
    @Mapping(target = "categoryId", source = "basket.food.category.id")
    BasketResponse basketToResponse(Basket basket);
}
