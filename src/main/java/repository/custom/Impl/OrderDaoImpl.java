package repository.custom.Impl;

import entity.EmployeeEntity;
import entity.ItemEntity;
import entity.OrderDetailEntity;
import entity.OrderEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.custom.OrderDao;
import util.HybernateUtil;

import java.util.List;
public class OrderDaoImpl implements OrderDao {

    public boolean placeOrder(OrderEntity orderEntity, List<OrderDetailEntity> orderDetails) {
       Session session = HybernateUtil.getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.persist(orderEntity);
            for(OrderDetailEntity orderDetail: orderDetails){
                session.persist(orderDetail);

                ItemEntity item = session.get( ItemEntity.class, orderDetail.getItemCode());
                item.setQtyOnHand(item.getQtyOnHand()-orderDetail.getQty());

            }

            session.getTransaction().commit();
            return true;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();  // Rollback on error
            }
            System.err.println("Order placement failed: " + e.getMessage());
            e.printStackTrace();
            return false;

        } finally {
            session.close();  // Ensure the session is closed
        }
    }



    public  OrderEntity searchOrder(String id) {

        Session session = HybernateUtil.getSession();
        Transaction transaction = null;
        OrderEntity entity= null;

        try {
            transaction = session.beginTransaction();
            String hql = "FROM  OrderEntity WHERE orderId = :id";
            Query< OrderEntity> query = session.createQuery(hql,  OrderEntity.class);
            query.setParameter("id", id);
            entity = query.uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return entity;
    }



    @Override
    public boolean save(OrderEntity orderEntity) {
        return false;
    }

    @Override
    public ObservableList<OrderEntity> getAll() {
        ObservableList<OrderEntity> orderList = FXCollections.observableArrayList();
        Session session = HybernateUtil.getSession();
        try {
            session.beginTransaction();
            Query<OrderEntity> query = session.createQuery("FROM OrderEntity", OrderEntity.class);
            List<OrderEntity> orders = query.getResultList();
            orderList.addAll(orders);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return orderList;
    }

    @Override
    public boolean validateLogin(String email, String enteredPassword) {
        return false;
    }

    @Override
    public boolean deleteById(String id) {
        return false;
    }
}
