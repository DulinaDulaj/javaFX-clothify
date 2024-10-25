package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.Item;

import entity.ItemEntity;
import entity.SupplierEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import repository.DaoFactory;
import repository.supplier.SupplierDao;
import service.ServiceFactory;
import service.item.ItemService;
import util.DaoType;
import util.ServiceType;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ItemDetailsFormController implements Initializable {
    public AnchorPane itemDetailPane;
    public JFXTextField txtItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtSize;
    public JFXTextField txtUnitPrice;
    public JFXComboBox cmbSupID;
    public JFXTextField txtQtyOnHand;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colSize;
    public TableColumn colUnitPrice;
    public TableColumn colQtyOnHand;
    public TableColumn colSupId;
    public TableView tblitems;

    ItemService service = ServiceFactory.getInstance().getServiceType(ServiceType.ITEM);

    public void backOnAction(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Admin_Dashboard_Form.fxml"));
        AnchorPane dashboardPane = null;
        try {
            dashboardPane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        itemDetailPane.getChildren().setAll(dashboardPane);
    }

    public void clearOnAction(ActionEvent actionEvent) {
        txtItemCode.setText("");
        txtDescription.setText("");
        txtSize.setText("");
        txtUnitPrice.setText("");
        txtQtyOnHand.setText("");
        cmbSupID.setValue("");
    }

    public void reloadOnAction(ActionEvent actionEvent) {
        loadTable();
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        String id = txtItemCode.getText();
        if (service.deleteByID(id)) {
            new Alert(Alert.AlertType.INFORMATION, id + " Item Deleted Successfully !").show();

        }
    }

    public void updateOnAction(ActionEvent actionEvent) {
        String supID= (String) cmbSupID.getValue();
        Double unitPrice= Double.valueOf(txtUnitPrice.getText());
        Integer qtyOnHand= Integer.valueOf(txtQtyOnHand.getText());

        if(service.update(txtItemCode.getText(),txtDescription.getText(),txtSize.getText(),unitPrice,qtyOnHand,supID)){
            new Alert(Alert.AlertType.INFORMATION," Item Updated Successfully !").show();

        }
    }

    public void addItemsOnAction(ActionEvent actionEvent) {
        String supID= (String) cmbSupID.getValue();
        Double unitPrice= Double.valueOf(txtUnitPrice.getText());
        Integer qtyOnHand= Integer.valueOf(txtQtyOnHand.getText());

        Item item=new Item(txtItemCode.getText(),txtDescription.getText(),txtSize.getText(),unitPrice,qtyOnHand,supID);

       if (service.addItems(item)) {
           new Alert(Alert.AlertType.INFORMATION, "Item successfully Added !").show();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadSupIds();
        loadTable();

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colSupId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));

        tblitems.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            setTextToValues((ItemEntity) newValue);
        }));

    }

    private void setTextToValues(ItemEntity entity) {
        txtItemCode.setText(entity.getItemCode());
        txtDescription.setText(entity.getDescription());
        txtSize.setText(entity.getSize());
        txtUnitPrice.setText(String.valueOf(entity.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(entity.getQtyOnHand()));
        cmbSupID.setValue(entity.getSupplierId());
    }

    public void loadSupIds(){
        cmbSupID.setItems(getSupplierIds());
    }

    public ObservableList<String> getSupplierIds() {
        SupplierDao supplierDao = DaoFactory.getInstance().getDaoType(DaoType.SUPPLIER);
        ObservableList<String> supplierIds = FXCollections.observableArrayList();
        ObservableList<SupplierEntity> customerObservableList = supplierDao.getAll();
        customerObservableList.forEach(supplier -> {
            supplierIds.add(supplier.getId());
        });

        return supplierIds;

    }
    private void loadTable() {
        ObservableList<ItemEntity> itemEntities = service.getAll();
        tblitems.setItems(itemEntities);
    }
}
