package repository.employee;

import entity.EmployeeEntity;
import repository.CrudRepository;

public interface EmployeeDao extends CrudRepository<EmployeeEntity> {
    boolean update(String id, String name, String email,String password);
    EmployeeEntity searchEmployee(String id);
}
