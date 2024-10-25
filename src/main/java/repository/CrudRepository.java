package repository;

import entity.EmployeeEntity;
import javafx.collections.ObservableList;

import java.util.List;

public interface CrudRepository <T> extends SuperDao{
    boolean save(T t);
    ObservableList<T> getAll();
    boolean validateLogin(String email, String enteredPassword);
    boolean deleteById(String id);

}
