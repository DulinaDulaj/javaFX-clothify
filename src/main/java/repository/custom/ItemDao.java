package repository.custom;

import entity.ItemEntity;
import repository.CrudRepository;

public interface ItemDao extends CrudRepository<ItemEntity> {
    boolean update(String itemCode,String description,String size,Double unitPrice,Integer qtyOnHand,String supId);
    ItemEntity searchItemByCode(String itemCode);
}
