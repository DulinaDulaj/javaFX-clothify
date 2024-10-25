package repository;

import repository.custom.Impl.EmployeeDaoImpl;
import repository.custom.Impl.ItemDaoImpl;
import repository.custom.Impl.OrderDaoImpl;
import repository.custom.Impl.SupplierDaoImpl;
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
