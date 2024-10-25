package repository.employee.Impl;

import entity.EmployeeEntity;
import entity.ItemEntity;
import entity.SupplierEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.employee.EmployeeDao;
import util.HybernateUtil;

import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {

    public boolean save(EmployeeEntity employeeEntity) {
        System.out.println("repo-"+employeeEntity);
        Session session = HybernateUtil.getSession();
        session.beginTransaction();
        session.persist(employeeEntity);
        session.getTransaction().commit();
        session.close();
        return true;
    }
    public ObservableList<EmployeeEntity> getAll() {
        ObservableList<EmployeeEntity> employeeList = FXCollections.observableArrayList();
        Session session = HybernateUtil.getSession();
        try {
            session.beginTransaction();
            Query<EmployeeEntity> query = session.createQuery("FROM EmployeeEntity", EmployeeEntity.class);
            List<EmployeeEntity> employees = query.getResultList();
            employeeList.addAll(employees);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return employeeList;
    }

    public boolean validateLogin(String email, String enteredPassword) {
        Session session = HybernateUtil.getSession();
        boolean isValid = false;
        try {
            session.beginTransaction();
            Query<EmployeeEntity> query = session.createQuery("FROM EmployeeEntity WHERE email = :email", EmployeeEntity.class);
            query.setParameter("email", email);
            EmployeeEntity user = query.uniqueResult();
            if (user != null) {
                if (user.getPassword().equals(enteredPassword)) {
                    isValid = true;
                }
            }
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return isValid;
    }

    @Override
    public boolean deleteById(String id) {
        Session session = HybernateUtil.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            EmployeeEntity entity = session.get(EmployeeEntity.class, id);
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


    public boolean update(String id, String name, String email,String password) {
        Session session = HybernateUtil.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            EmployeeEntity entity = session.get(EmployeeEntity.class, id);
            if (entity != null) {

                entity.setName(name);
                entity.setEmail(email);
                entity.setPassword(password);

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
    public EmployeeEntity searchEmployee(String id) {

        Session session = HybernateUtil.getSession();
        Transaction transaction = null;
        EmployeeEntity entity= null;

        try {
            transaction = session.beginTransaction();
            String hql = "FROM EmployeeEntity WHERE id = :id";
            Query<EmployeeEntity> query = session.createQuery(hql, EmployeeEntity.class);
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

}
