package service.custom;

import dto.Supplier;
import entity.SupplierEntity;
import javafx.collections.ObservableList;
import service.SuperService;

public interface SupplierService  extends SuperService {
    boolean addSupplier(Supplier supplier);
    ObservableList<SupplierEntity> getAll();
    boolean update(String id, String name,String company,String email);

}
