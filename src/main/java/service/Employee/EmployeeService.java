package service.Employee;

import dto.Employee;
import entity.EmployeeEntity;
import javafx.collections.ObservableList;
import service.SuperService;

import java.util.List;

public interface EmployeeService extends SuperService {
    boolean addCustomer(Employee employee);
    ObservableList<EmployeeEntity> getAll();
    boolean validateLogin(String email,String password);
    boolean update(String id, String name,String email,String password);
    EmployeeEntity searchEmployeeById(String employeeId);
}
