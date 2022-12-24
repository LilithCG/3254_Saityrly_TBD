package com.delivery_service.service.basket;

import com.delivery_service.dto.request.BasketRequest;
import com.delivery_service.dto.responce.BasketResponse;
import com.delivery_service.dto.responce.FoodResponse;
import com.delivery_service.postgres.entity.Basket;
import com.delivery_service.postgres.entity.Order;
import com.delivery_service.postgres.entity.User;
import com.delivery_service.postgres.repository.BasketRepository;
import com.delivery_service.service.food.FoodService;
import com.delivery_service.service.order.OrderService;
import com.delivery_service.service.user.UserService;
import com.delivery_service.util.mapper.BasketMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {
    private final BasketRepository basketRepository;
    private final UserService userService;
    private final OrderService orderService;
    private final FoodService foodService;
    private final BasketMapper mapper = Mappers.getMapper(BasketMapper.class);

    private Basket toBasket(BasketRequest basketReq) throws ChangeSetPersister.NotFoundException {
        return Basket.builder()
                .order(orderService.fetchOrderById(basketReq.getOrderId()))
                .user(userService.fetchUser(basketReq.getChatId()))
                .food(foodService.fetchById(basketReq.getFoodId()))
                .count(basketReq.getCount())
                .build();
    }
    @Override
    public void save(BasketRequest basketReq) throws ChangeSetPersister.NotFoundException {
        Basket basket = toBasket(basketReq);
        basketRepository.save(basket);
    }

    @Override
    public List<BasketResponse> fetchBasketByOrder(long orderId) throws ChangeSetPersister.NotFoundException {
        Order order = orderService.fetchOrderById(orderId);
        List<BasketResponse> list = new ArrayList<>();
        for (Basket basket : basketRepository.findBasketsByOrder(order)) {
            BasketResponse basketResponse = mapper.basketToResponse(basket);
            list.add(basketResponse);
        }
        return list;
    }

    @Override
    public List<BasketResponse> fetchBasketsByChatId(long userId) throws ChangeSetPersister.NotFoundException {
        User user = userService.fetchUser(userId);
        List<BasketResponse> list = new ArrayList<>();
        for (Basket basket : basketRepository.findBasketsByUserAndOrder_IsActive(user, true)) {
            BasketResponse basketResponse = mapper.basketToResponse(basket);
            list.add(basketResponse);
        }
        return list;
    }

    @Transactional
    @Override
    public void deleteBasketById(long id) throws ChangeSetPersister.NotFoundException {
        if (!exist(id)) {
            throw new ChangeSetPersister.NotFoundException();
        }
        basketRepository.deleteBasketById(id);
    }

    @Override
    public List<String> fetchProductsRefs(long orderId) throws ChangeSetPersister.NotFoundException {
        List<String> list = new ArrayList<>();
        for (BasketResponse basketResponse : fetchBasketByOrder(orderId)) {
            String foodLink = basketResponse.getFoodLink();
            list.add(foodLink);
        }
        return list;
    }

    @Override
    public boolean exist(long id) {
        return basketRepository.existsById(id);
    }

    @Override
    public List<FoodResponse> searchFoodByName(long orderId, String nameText) throws ChangeSetPersister.NotFoundException {
        if (!orderService.isActiveOrder(orderId))
            return Collections.emptyList();
        long cafeId = orderService.fetchCafeByOrder(orderId);
        return foodService.searchFood(cafeId, nameText);
    }

    @Override
    public Set<String> peoples(long orderId) throws ChangeSetPersister.NotFoundException {
        return fetchBasketByOrder(orderId).stream()
                .distinct()
                .collect(Collectors.groupingBy(BasketResponse::getChatId, Collectors.counting())).keySet();
    }
}
