package service.custom.Impl;

import dto.Employee;
import entity.EmployeeEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.EmployeeDao;
import service.custom.EmployeeService;
import util.DaoType;

public class EmployeeServiceImpl implements EmployeeService {

    EmployeeDao employeeDao = DaoFactory.getInstance().getDaoType(DaoType.EMPLOYEE);

    public boolean addCustomer(Employee employee) {
        System.out.println("service -"+employee);
        EmployeeEntity entity = new ModelMapper().map(employee, EmployeeEntity.class);
        employeeDao.save(entity);
        return true;
    }
    public ObservableList<EmployeeEntity> getAll(){
        ObservableList<EmployeeEntity> employeeList = FXCollections.observableArrayList();
        employeeList=employeeDao.getAll();
        return employeeList;
    }
    public boolean validateLogin(String email,String password){
         boolean isValid=employeeDao.validateLogin(email,password);
         return isValid;
    }

    @Override
    public boolean deleteByID(String id) {
        return employeeDao.deleteById(id);
    }

    public boolean update(String id, String name,String email,String password){
        return employeeDao.update(id,name,email,password);
    }

    public EmployeeEntity searchEmployeeById(String employeeId){
        return employeeDao.searchEmployee(employeeId);
    }
}
