package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class RegisterAdminFormController {

    public AnchorPane resgisterAdminPane;

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
}