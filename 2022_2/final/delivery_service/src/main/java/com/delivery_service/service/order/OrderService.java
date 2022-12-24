package com.delivery_service.service.order;

import com.delivery_service.postgres.entity.Order;
import com.delivery_service.dto.request.OrderRequest;
import com.delivery_service.dto.responce.OrderResponse;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface OrderService {
    void saveOrder(OrderRequest order) throws ChangeSetPersister.NotFoundException;

    void updateOrder(long id) throws ChangeSetPersister.NotFoundException;

    Order fetchOrderById(long id) throws ChangeSetPersister.NotFoundException;

    List<OrderResponse> fetchOrders(boolean isActive) throws ChangeSetPersister.NotFoundException;

    Long fetchCafeByOrder(long orderId) throws ChangeSetPersister.NotFoundException;

    boolean isActiveOrder(long id) throws ChangeSetPersister.NotFoundException;

    boolean exist(long id);
}
