package service.custom.Impl;

import dto.Supplier;
import entity.SupplierEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.SupplierDao;
import service.custom.SupplierService;
import util.DaoType;

public class SupplierServiceImpl implements SupplierService {

    SupplierDao supplierDao = DaoFactory.getInstance().getDaoType(DaoType.SUPPLIER);
    public boolean addSupplier(Supplier supplier) {
        System.out.println("service -"+supplier);
        SupplierEntity entity = new ModelMapper().map(supplier, SupplierEntity.class);


        supplierDao.save(entity);

        return true;
    }

    @Override
    public ObservableList<SupplierEntity> getAll() {
        ObservableList<SupplierEntity> supplierList = FXCollections.observableArrayList();
        supplierList=supplierDao.getAll();
        return supplierList;
    }

    public boolean deleteByID(String id){
        return supplierDao.deleteById(id);
    }
    public boolean update(String id, String name,String company,String email){
        return supplierDao.update(id,name,company,email);
    }
}
