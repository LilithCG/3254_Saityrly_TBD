package com.delivery_service.service.basket;

import com.delivery_service.dto.request.BasketRequest;
import com.delivery_service.dto.responce.BasketResponse;
import org.springframework.data.crossstore.ChangeSetPersister;
import com.delivery_service.dto.responce.FoodResponse;

import java.util.List;
import java.util.Set;

public interface BasketService {
    void save(BasketRequest basketReq) throws ChangeSetPersister.NotFoundException;

    List<BasketResponse> fetchBasketByOrder(long orderId) throws ChangeSetPersister.NotFoundException;

    List<BasketResponse> fetchBasketsByChatId(long userId) throws ChangeSetPersister.NotFoundException;

    void deleteBasketById(long id) throws ChangeSetPersister.NotFoundException;

    List<String> fetchProductsRefs(long orderId) throws ChangeSetPersister.NotFoundException;
    boolean exist(long id);

    Set<String> peoples(long orderId) throws ChangeSetPersister.NotFoundException;

    List<FoodResponse> searchFoodByName(long orderId, String nameText) throws ChangeSetPersister.NotFoundException;
}
