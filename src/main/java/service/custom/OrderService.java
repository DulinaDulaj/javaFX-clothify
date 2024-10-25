package service.custom;

import entity.OrderDetailEntity;
import entity.OrderEntity;
import service.SuperService;

import java.util.List;

public interface OrderService extends SuperService {
    boolean placeOrder(OrderEntity orderEntity, List<OrderDetailEntity> orderDetails);
    OrderEntity searchOrder(String orderID);
}
