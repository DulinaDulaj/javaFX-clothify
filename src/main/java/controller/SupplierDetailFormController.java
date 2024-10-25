package controller;

import com.jfoenix.controls.JFXTextField;
import dto.Supplier;
import entity.EmployeeEntity;
import entity.SupplierEntity;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import service.Employee.EmployeeService;
import service.ServiceFactory;
import service.SuperService;
import service.supplier.SupplierService;
import util.ServiceType;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SupplierDetailFormController implements Initializable {

    public AnchorPane supplierDetailPane;
    public TableView tblSuppliers;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colCompany;
    public TableColumn colEmail;
    public JFXTextField txtSupID;
    public JFXTextField txtSupCompany;
    public JFXTextField txtSupName;
    public JFXTextField txtSupEmail;

    SupplierService service = ServiceFactory.getInstance().getServiceType(ServiceType.SUPPLIER);

    public void backOnAction(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Admin_Dashboard_Form.fxml"));
        AnchorPane dashboardPane = null;
        try {
            dashboardPane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        supplierDetailPane.getChildren().setAll(dashboardPane);
    }

    public void AddSupplierOnAction(ActionEvent actionEvent) {
        Supplier supplier=new Supplier(txtSupID.getText(),txtSupName.getText(),txtSupCompany.getText(),txtSupEmail.getText());

        if(service.addSupplier(supplier)){
            new Alert(Alert.AlertType.INFORMATION,"Supplier successfully Added !").show();

        }
    }

    public void reloadTableOnAction(ActionEvent actionEvent) {
        loadTable();
    }

    public void clearfieldsOnAction(ActionEvent actionEvent) {
        clear();
    }
    public void clear(){
        txtSupID.setText("");
        txtSupName.setText("");
        txtSupCompany.setText("");
        txtSupEmail.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        tblSuppliers.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            setTextToValues((SupplierEntity) newValue);
        }));
        loadTable();
    }

    private void setTextToValues(SupplierEntity entity){
        txtSupID.setText(entity.getId());
        txtSupName.setText(entity.getName());
        txtSupCompany.setText(entity.getCompany());
        txtSupEmail.setText(entity.getEmail());
    }

    private void loadTable(){
        ObservableList<SupplierEntity> supplierEntities=service.getAll();
        tblSuppliers.setItems(supplierEntities);
    }

    public void deleteSupplierOnAction(ActionEvent actionEvent) {
        String id=txtSupID.getText();
        if(service.deleteByID(id)){
            new Alert(Alert.AlertType.INFORMATION,id+" Supplier Deleted Successfully !").show();
        }
    }

    public void updateSupplierOnAction(ActionEvent actionEvent) {
        if(service.update(txtSupID.getText(),txtSupName.getText(),txtSupCompany.getText(),txtSupEmail.getText())){
            new Alert(Alert.AlertType.INFORMATION," Supplier Updated Successfully !").show();
        }
    }
}
