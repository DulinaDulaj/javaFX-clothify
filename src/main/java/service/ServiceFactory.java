package service;

import service.custom.Impl.EmployeeServiceImpl;
import service.custom.Impl.ItemServiceImpl;
import service.custom.Impl.OrderServiceImpl;
import service.custom.Impl.SupplierServiceImpl;
import util.ServiceType;

public class ServiceFactory {
    private static ServiceFactory instance;
    private ServiceFactory(){}

    public static ServiceFactory getInstance() {
        return instance==null?instance=new ServiceFactory():instance;
    }

    public <T extends SuperService>T getServiceType(ServiceType type){
        switch (type){
            case EMPLOYEE:return (T) new EmployeeServiceImpl();
            case SUPPLIER:return (T) new SupplierServiceImpl();
            case ITEM:return (T) new ItemServiceImpl();
            case ORDER:return (T) new OrderServiceImpl();
        }
        return null;
    }
}
