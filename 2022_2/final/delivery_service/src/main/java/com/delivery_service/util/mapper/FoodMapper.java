package com.delivery_service.util.mapper;

import com.delivery_service.postgres.entity.Food;
import com.delivery_service.dto.responce.FoodResponse;
import org.mapstruct.Mapper;

@Mapper
public interface FoodMapper {
    FoodResponse foodToResponse(Food food);
}
