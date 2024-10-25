package service.custom;

import dto.Item;
import entity.ItemEntity;
import javafx.collections.ObservableList;
import service.SuperService;

public interface ItemService extends SuperService {
    boolean addItems(Item item);
    ObservableList<ItemEntity> getAll();
    boolean update(String itemCode,String description,String size,Double unitPrice,Integer qtyOnHand,String supId);
    ItemEntity searchItemByCode(String itemCode);
}
