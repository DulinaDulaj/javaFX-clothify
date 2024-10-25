package controller;

import com.jfoenix.controls.JFXTextField;
import dto.Employee;
import entity.EmployeeEntity;
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
import util.ServiceType;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeDetailsFormController implements Initializable {

    public AnchorPane employeeDetailPane;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colEmail;
    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtEmail;
    public TableView tblEmployee;
    public JFXTextField txtPassword;

    EmployeeService employeeService = ServiceFactory.getInstance().getServiceType(ServiceType.EMPLOYEE);

    public void backOnAction(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Admin_Dashboard_Form.fxml"));
        AnchorPane dashboardPane = null;
        try {
            dashboardPane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        employeeDetailPane.getChildren().setAll(dashboardPane);
    }

    public void employeeAddOnAction(ActionEvent actionEvent) {
        Employee employee = new Employee(txtId.getText(), txtName.getText(), txtEmail.getText(), txtPassword.getText());

        if (employeeService.addCustomer(employee)) {
            new Alert(Alert.AlertType.INFORMATION, "Employee successfully Added !").show();

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        tblEmployee.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            setTextToValues((EmployeeEntity) newValue);
        }));
        loadTable();
    }

    private void setTextToValues(EmployeeEntity employee) {
        txtId.setText(employee.getId());
        txtName.setText(employee.getName());
        txtEmail.setText(employee.getEmail());
        txtPassword.setText(employee.getPassword());
    }

    private void loadTable() {
        ObservableList<EmployeeEntity> employeeEntities = employeeService.getAll();
        tblEmployee.setItems(employeeEntities);
    }

    public void btnReloadOnAction(ActionEvent actionEvent) {
        loadTable();
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clear();
    }

    public void clear() {
        txtId.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
    }

    public void employeeUpdateOnAction(ActionEvent actionEvent) {
        if(employeeService.update(txtId.getText(),txtName.getText(),txtEmail.getText(),txtPassword.getText())){
            new Alert(Alert.AlertType.INFORMATION," Employee Updated Successfully !").show();

        }
    }

    public void employeeDeleteOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        if (employeeService.deleteByID(id)) {
            new Alert(Alert.AlertType.INFORMATION, id + " Employee Deleted Successfully !").show();

        }
    }
}