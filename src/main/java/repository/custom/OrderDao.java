package repository.custom;

import entity.OrderDetailEntity;
import entity.OrderEntity;
import repository.CrudRepository;

import java.util.List;

public interface OrderDao extends CrudRepository<OrderEntity> {
    boolean placeOrder(OrderEntity orderEntity, List<OrderDetailEntity> orderDetails);
    OrderEntity searchOrder(String id);
}
