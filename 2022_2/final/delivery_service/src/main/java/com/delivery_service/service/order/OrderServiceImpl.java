package com.delivery_service.service.order;

import com.delivery_service.postgres.repository.OrderRepository;
import com.delivery_service.postgres.entity.Order;
import com.delivery_service.dto.request.OrderRequest;
import com.delivery_service.dto.responce.OrderResponse;
import com.delivery_service.service.cafe.CafeService;
import com.delivery_service.service.user.UserService;
import com.delivery_service.util.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CafeService cafeService;
    private final OrderMapper mapper = Mappers.getMapper(OrderMapper.class);

    private Order toOrder(OrderRequest orderReq) throws ChangeSetPersister.NotFoundException {
        return Order.builder()
                .isActive(orderReq.getIsActive())
                .user(userService.fetchUser(orderReq.getUserId()))
                .cafe(cafeService.fetchCafe(orderReq.getCafeId()))
                .build();
    }

    @Override
    public void saveOrder(OrderRequest orderReq) throws ChangeSetPersister.NotFoundException {
        Order order = toOrder(orderReq);
        orderRepository.save(order);
    }

    @Override
    public void updateOrder(long id) throws ChangeSetPersister.NotFoundException {
        Order orderDB = fetchOrderById(id);
        orderDB.setIsActive(false);

        orderRepository.save(orderDB);
    }

    @Override
    public Order fetchOrderById(long id) throws ChangeSetPersister.NotFoundException {
        return Optional.ofNullable(orderRepository.findOrderById(id)).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    @Override
    public List<OrderResponse> fetchOrders(boolean isActive) throws ChangeSetPersister.NotFoundException {
        List<OrderResponse> list = new ArrayList<>();
        for (Order order : orderRepository.findOrdersByIsActive(isActive)) {
            OrderResponse orderResponse = mapper.orderToResponse(order);
            list.add(orderResponse);
        }
        return Optional.of(list)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    @Override
    public boolean exist(long id) {
        return orderRepository.existsById(id);
    }

    @Override
    public Long fetchCafeByOrder(long orderId) throws ChangeSetPersister.NotFoundException {
        return fetchOrderById(orderId).getCafe().getId();
    }

    @Override
    public boolean isActiveOrder(long id) throws ChangeSetPersister.NotFoundException {
        Order res = fetchOrderById(id);
        return res.getIsActive();
    }


}
