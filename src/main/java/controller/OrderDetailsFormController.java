package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import service.ServiceFactory;
import service.custom.OrderService;
import util.ServiceType;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OrderDetailsFormController implements Initializable {

    public AnchorPane OrderDetailPane;

    public TableColumn colOrId;

    public TableColumn colDateTime;

    public JFXTextField txtOrId;

    public JFXTextField txtDate;

    public TableView tblOrders;

    OrderService orderService = ServiceFactory.getInstance().getServiceType(ServiceType.ORDER);

    public void backOnAction(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Admin_Dashboard_Form.fxml"));
        AnchorPane dashboardPane = null;
        try {
            dashboardPane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        OrderDetailPane.getChildren().setAll(dashboardPane);    }

    public void deleteOnAction(ActionEvent actionEvent) {
    }

    public void reloadOnAction(ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colOrId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colDateTime.setCellValueFactory(new PropertyValueFactory<>("orderDate"));

        tblOrders.setItems(orderService.getAll());

    }
}
