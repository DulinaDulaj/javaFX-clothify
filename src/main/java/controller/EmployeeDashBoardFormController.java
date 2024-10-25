package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployeeDashBoardFormController {
    public AnchorPane homePagePane;

    public void placeOrderOnAction(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Employee_Place_Order_Form.fxml"));
        AnchorPane dashboardPane = null;
        try {
            dashboardPane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        homePagePane.getChildren().setAll(dashboardPane);

        Stage stage = (Stage) homePagePane.getScene().getWindow();
        stage.setTitle("Place Orders");
        stage.setX(110);
        stage.setY(150);

        Stage stage1=new Stage();
        stage1.setTitle("ItemList");
        try {
            stage1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/PlaceOrder_ItemList.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage1.setX(845);
        stage1.setY(150);
        stage1.show();
    }

    public void orderDetailsOnAction(ActionEvent actionEvent) {
    }

    public void backOnAction(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Employee Form.fxml"));
        AnchorPane dashboardPane = null;
        try {
            dashboardPane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        homePagePane.getChildren().setAll(dashboardPane);
    }
}
