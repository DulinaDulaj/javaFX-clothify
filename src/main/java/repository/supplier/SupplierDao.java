package repository.supplier;


import entity.SupplierEntity;
import repository.CrudRepository;

public interface SupplierDao extends CrudRepository<SupplierEntity> {
    boolean update(String id, String name,String company,String email);
}
