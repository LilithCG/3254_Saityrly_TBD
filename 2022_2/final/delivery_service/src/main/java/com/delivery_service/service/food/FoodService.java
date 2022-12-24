package com.delivery_service.service.food;

import com.delivery_service.dto.request.FoodRequest;
import com.delivery_service.dto.responce.FoodResponse;
import com.delivery_service.postgres.entity.Category;
import com.delivery_service.postgres.entity.Food;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.Map;

public interface FoodService {
    void save(FoodRequest food) throws ChangeSetPersister.NotFoundException;
    List<Food> fetchFood();
    Food fetchById(long id) throws ChangeSetPersister.NotFoundException;

    List<FoodResponse> findFoodByCafeAndCategory(long cafeId, long categoryId) throws ChangeSetPersister.NotFoundException;

    Map<Category, List<Food>> findCategoryByCafe(long cafeId) throws ChangeSetPersister.NotFoundException;

    void update(FoodRequest food) throws ChangeSetPersister.NotFoundException;

    void updateAll();

    List<FoodResponse> searchFood(long cafeId, String nameText) throws ChangeSetPersister.NotFoundException;
    boolean exist(long id);
}
