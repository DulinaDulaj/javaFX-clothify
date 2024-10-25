package controller;


import entity.ItemEntity;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import repository.DaoFactory;
import repository.item.ItemDao;
import util.DaoType;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemListController implements Initializable {
    public TableView tblItem;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colSize;

    ItemDao itemDao= DaoFactory.getInstance().getDaoType(DaoType.ITEM);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("Size"));

        loadTable();
    }
    public void loadTable() {
        ObservableList<ItemEntity> itemEntities = itemDao.getAll();
        tblItem.setItems(itemEntities);
    }
}
