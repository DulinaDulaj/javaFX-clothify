package service.order.Impl;

import entity.EmployeeEntity;
import entity.OrderDetailEntity;
import entity.OrderEntity;
import repository.DaoFactory;
import repository.order.OrderDao;
import service.order.OrderService;
import util.DaoType;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    OrderDao orderDao= DaoFactory.getInstance().getDaoType(DaoType.ORDER);
   public boolean placeOrder(OrderEntity orderEntity, List<OrderDetailEntity> orderDetails){
       orderEntity.setOrderDetails(orderDetails);
       return orderDao.placeOrder(orderEntity,orderDetails);
    }

    public OrderEntity searchOrder(String orderID){
        return orderDao.searchOrder(orderID);
    }

    @Override
    public boolean deleteByID(String id) {
       return false;
    }
}
