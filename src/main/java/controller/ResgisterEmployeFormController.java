package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ResgisterEmployeFormController {



    public AnchorPane employeePane;
    @FXML
    private JFXButton btnEmplloyeSignin;

    @FXML
    private JFXTextField txtEmployeeEmail;

    @FXML
    private JFXPasswordField txtEmployeePassword;

    @FXML
    private JFXPasswordField txtEmployeePassword1;


    public void loadEmployeePageOnAction(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Employee Form.fxml"));
        AnchorPane dashboardPane = null;
        try {
            dashboardPane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        employeePane.getChildren().setAll(dashboardPane);
    }


}
