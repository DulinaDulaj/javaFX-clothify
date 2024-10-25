package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class StartingPageFormController {

    @FXML
    private Button btnadminPage;

    @FXML
    private Button btnemloyeePage;

    @FXML
    private AnchorPane loginPane;

    @FXML
    void btnadminPageOnAction(ActionEvent event)  {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Admin_Form.fxml"));
        AnchorPane dashboardPane = null;
        try {
            dashboardPane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        loginPane.getChildren().setAll(dashboardPane);
    }

    @FXML
    void btnemployeePageOnAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Employee Form.fxml"));
        AnchorPane dashboardPane = null;
        try {
            dashboardPane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        loginPane.getChildren().setAll(dashboardPane);
    }

}
