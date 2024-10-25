package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import repository.DaoFactory;
import repository.custom.AdminDao;
import service.ServiceFactory;
import service.custom.AdminService;
import service.custom.EmployeeService;
import util.DaoType;
import util.ServiceType;

import java.io.IOException;

public class AdminFormController {

    public JFXTextField txtemail;

    public JFXPasswordField txtpassword;
    @FXML
    private AnchorPane adminLoginPane;

    @FXML
    void loadRegisterPageOnAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Register_Admin_Form.fxml"));
        AnchorPane dashboardPane = null;
        try {
            dashboardPane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        adminLoginPane.getChildren().setAll(dashboardPane);
    }

    public void backOnAction(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Starting_Form.fxml"));
        AnchorPane dashboardPane = null;
        try {
            dashboardPane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        adminLoginPane.getChildren().setAll(dashboardPane);
    }

    public void signInOnAction(ActionEvent actionEvent) {
        AdminService adminService= ServiceFactory.getInstance().getServiceType(ServiceType.ADMIN);

        boolean isValid=adminService.validateLogin(txtemail.getText(),txtpassword.getText());
        if(isValid){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Admin_Dashboard_Form.fxml"));
            AnchorPane dashboardPane = null;
            try {
                dashboardPane = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            adminLoginPane.getChildren().setAll(dashboardPane);



        }else{
            new Alert(Alert.AlertType.ERROR,"Incorrect Email or Password !").show();
        }
    }
}
