package service.item.Impl;

import dto.Item;
import entity.EmployeeEntity;
import entity.ItemEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.item.ItemDao;
import service.item.ItemService;
import util.DaoType;

public class ItemServiceImpl implements ItemService {

    ItemDao itemDao= DaoFactory.getInstance().getDaoType(DaoType.ITEM);

    public boolean addItems(Item item){
        ItemEntity entity = new ModelMapper().map(item, ItemEntity.class);
        itemDao.save(entity);
        return true;
    }

    public ObservableList<ItemEntity> getAll(){
        ObservableList<ItemEntity> itemList = FXCollections.observableArrayList();
        itemList=itemDao.getAll();
        return itemList;
    }

    @Override
    public boolean deleteByID(String id) {
        return itemDao.deleteById(id);
    }
    public boolean update(String itemCode,String description,String size,Double unitPrice,Integer qtyOnHand,String supId){
        return itemDao.update(itemCode,description,size,unitPrice,qtyOnHand,supId);
    }
    public ItemEntity searchItemByCode(String itemCode){
        return itemDao.searchItemByCode(itemCode);
    }
}
