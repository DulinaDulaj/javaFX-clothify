package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import service.custom.EmployeeService;
import service.ServiceFactory;
import util.ServiceType;

import java.io.IOException;

public class EmployeeFormController {

    public AnchorPane employeePane;
    public JFXTextField txtEmpEmail;
    public JFXPasswordField txtPassword;
    EmployeeService employeeService= ServiceFactory.getInstance().getServiceType(ServiceType.EMPLOYEE);

    @FXML
    void backOnAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Starting_Form.fxml"));
        AnchorPane dashboardPane = null;
        try {
            dashboardPane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        employeePane.getChildren().setAll(dashboardPane);

    }

    public void EmployeeSignInOnAction(ActionEvent actionEvent) {
        boolean isValid=employeeService.validateLogin(txtEmpEmail.getText(),txtPassword.getText());
    if(isValid){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Employee_Dash_Board.fxml"));
        AnchorPane dashboardPane = null;
        try {
            dashboardPane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        employeePane.getChildren().setAll(dashboardPane);



    }else{
        new Alert(Alert.AlertType.ERROR,"Incorrect Email or Password !").show();
    }

    }
}
