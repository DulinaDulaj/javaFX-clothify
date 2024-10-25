package repository.supplier.Impl;

import entity.SupplierEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.supplier.SupplierDao;
import util.HybernateUtil;

import java.util.List;

public class SupplierDaoImpl implements SupplierDao {

    public boolean save(SupplierEntity entity) {
        Session session = HybernateUtil.getSession();
        session.beginTransaction();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public ObservableList<SupplierEntity> getAll() {
        ObservableList<SupplierEntity> supplierList = FXCollections.observableArrayList();
        Session session = HybernateUtil.getSession();
        try {
            session.beginTransaction();
            Query<SupplierEntity> query = session.createQuery("FROM SupplierEntity", SupplierEntity.class);
            List<SupplierEntity> employees = query.getResultList();
            supplierList.addAll(employees);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return supplierList;
    }

    @Override
    public boolean validateLogin(String email, String enteredPassword) {
        return false;
    }

    public boolean deleteById(String id) {
        Session session = HybernateUtil.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            SupplierEntity supplier = session.get(SupplierEntity.class, id);
            if (supplier != null) {
                session.delete(supplier);
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

    public boolean update(String id, String name,String company,String email) {
        Session session = HybernateUtil.getSession();
        Transaction transaction = null;
        System.out.println(id+""+name+""+company+""+email);
        try {
            transaction = session.beginTransaction();
            SupplierEntity entity = session.get(SupplierEntity.class, id);
            if (entity != null) {

                entity.setName(name);
                entity.setCompany(company);
                entity.setEmail(email);

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
}
