package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dto.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import service.ServiceFactory;
import service.custom.AdminService;
import util.ServiceType;

import java.io.IOException;

public class RegisterAdminFormController {

    public AnchorPane resgisterAdminPane;

    public JFXTextField txtemail;

    public JFXPasswordField txtpassword;

    public JFXPasswordField txtconformPass;

    public void loadAdminLoginPageOnAction(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Admin_Form.fxml"));
        AnchorPane dashboardPane = null;
        try {
            dashboardPane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        resgisterAdminPane.getChildren().setAll(dashboardPane);
    }

    public void registerOnAction(ActionEvent actionEvent) {
        AdminService adminService= ServiceFactory.getInstance().getServiceType(ServiceType.ADMIN);
        if(txtpassword.getText().equals(txtconformPass.getText())){
            adminService.addAdmin(new Admin(txtemail.getText(),txtpassword.getText()));
        }
    }
}
