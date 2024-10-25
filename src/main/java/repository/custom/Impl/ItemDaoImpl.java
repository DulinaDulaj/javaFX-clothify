package repository.custom.Impl;

import entity.ItemEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.custom.ItemDao;
import util.HybernateUtil;

import java.util.List;

public class ItemDaoImpl implements ItemDao {

    @Override
    public boolean save(ItemEntity itemEntity) {
        Session session = HybernateUtil.getSession();
        session.beginTransaction();
        session.persist(itemEntity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public ObservableList<ItemEntity> getAll() {
        ObservableList<ItemEntity> itemList = FXCollections.observableArrayList();
        Session session = HybernateUtil.getSession();
        try {
            session.beginTransaction();
            Query<ItemEntity> query = session.createQuery("FROM ItemEntity", ItemEntity.class);
            List<ItemEntity> items = query.getResultList();
            itemList.addAll(items);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return itemList;
    }

    @Override
    public boolean validateLogin(String email, String enteredPassword) {
        return false;
    }

    @Override
    public boolean deleteById(String id) {
        Session session = HybernateUtil.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            ItemEntity entity = session.get(ItemEntity.class, id);
            if (entity != null) {
                session.delete(entity);
            } else {
                return false;
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return true;
    }

    @Override
    public boolean update(String itemCode, String description, String size, Double unitPrice, Integer qtyOnHand, String supId) {
        Session session = HybernateUtil.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            ItemEntity entity = session.get(ItemEntity.class, itemCode);
            if (entity != null) {

                entity.setDescription(description);
                entity.setSize(size);
                entity.setUnitPrice(unitPrice);
                entity.setQtyOnHand(qtyOnHand);
                entity.setSupplierId(supId);

                session.update(entity);

            }
            transaction.commit();

            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }
    public ItemEntity searchItemByCode(String itemCode) {

        Session session = HybernateUtil.getSession();
        Transaction transaction = null;
        ItemEntity item = null;

        try {
            transaction = session.beginTransaction();
            String hql = "FROM ItemEntity WHERE itemCode = :itemCode";
            Query<ItemEntity> query = session.createQuery(hql, ItemEntity.class);
            query.setParameter("itemCode", itemCode);
            item = query.uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return item;
    }

}
