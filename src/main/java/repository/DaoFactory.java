package repository;

import repository.employee.Impl.EmployeeDaoImpl;
import repository.item.Impl.ItemDaoImpl;
import repository.order.Impl.OrderDaoImpl;
import repository.supplier.Impl.SupplierDaoImpl;
import service.supplier.Impl.SupplierServiceImpl;
import util.DaoType;

public class DaoFactory {
    private static DaoFactory instance;
    private DaoFactory(){}

    public static DaoFactory getInstance() {
        return instance==null?instance=new DaoFactory():instance;
    }

    public <T extends SuperDao>T getDaoType(DaoType type){
        switch (type){
            case EMPLOYEE:return (T) new EmployeeDaoImpl();
            case SUPPLIER:return (T) new SupplierDaoImpl();
            case ITEM:return (T) new ItemDaoImpl();
            case ORDER:return (T) new OrderDaoImpl();
        }
        return null;

    }
}