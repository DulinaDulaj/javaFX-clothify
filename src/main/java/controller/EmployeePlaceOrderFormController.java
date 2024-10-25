package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.CartTM;
import dto.Item;
import entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import repository.DaoFactory;
import repository.employee.EmployeeDao;
import repository.item.ItemDao;
import service.Employee.EmployeeService;
import service.ServiceFactory;
import service.item.ItemService;
import service.order.OrderService;
import util.DaoType;
import util.ServiceType;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeePlaceOrderFormController implements Initializable {
    @FXML
    public TextField txtEmail;
    @FXML
    public TextField txtCustomerName;

    public AnchorPane OrderPane;

    public TableView tblCart;

    public TableColumn colItemID;

    public TableColumn colDescription;

    public TableColumn colUnitPrice;

    public TableColumn colTotal;

    public TableColumn colQty;

    public TextField txtSize;

    public TableColumn colSize;
    public JFXTextField txtOrId;

    @FXML
    private JFXComboBox<String> cmbEmployeeID;

    @FXML
    private JFXComboBox<String> cmbItemCode;
    
    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblOrderID;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtStock;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    void backOnAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Employee_Dash_Board.fxml"));
        AnchorPane dashboardPane = null;
        try {
            dashboardPane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        OrderPane.getChildren().setAll(dashboardPane);

        Stage stage = (Stage) OrderPane.getScene().getWindow();
        stage.setX(350);
        stage.setY(150);

        for (Window window : Window.getWindows()) {
            if (window instanceof Stage) {
                Stage stage2 = (Stage) window;
                if (stage2.getTitle().equals("ItemList")) {
                    stage2.close();
                    break;
                }
            }
        }

    }

    public void placeOrderOnAction(ActionEvent actionEvent) {
        try {
            // Step 1: Fetch the OrderService from ServiceFactory
            OrderService orderService = ServiceFactory.getInstance().getServiceType(ServiceType.ORDER);

            // Step 2: Create a new OrderEntity
            String orderId = txtOrId.getText();  // Get the order ID from the label
            Date orderDate = new Date();  // Current date as the order date
            String employeeId = cmbEmployeeID.getValue();  // Get the employee ID from the combo box

            // Fetch the EmployeeEntity based on employeeId
            EmployeeService employeeService = ServiceFactory.getInstance().getServiceType(ServiceType.EMPLOYEE);
            EmployeeEntity employeeEntity = employeeService.searchEmployeeById(employeeId);

            if (employeeEntity == null) {
                new Alert(Alert.AlertType.ERROR, "Invalid Employee ID!").show();
                return;
            }

            // Create the OrderEntity
            OrderEntity orderEntity = new OrderEntity(orderId, orderDate);

            // Step 3: Prepare OrderDetailEntity list
            List<OrderDetailEntity> orderDetails = new ArrayList<>();

            cartTMS.forEach(obj -> {
                String itemCode = obj.getItemCode();  // Assuming cartTMS provides this data
                Integer quantity = obj.getQty();  // Assuming cartTMS provides this data

                // Create an OrderDetailEntity for each item in the cart
                OrderDetailEntity orderDetail = new OrderDetailEntity(orderEntity, itemCode, quantity);
                orderDetails.add(orderDetail);
            });

            // Step 4: Call the placeOrder method and display a message based on the result
            if (orderService.placeOrder(orderEntity, orderDetails)) {
                new Alert(Alert.AlertType.INFORMATION, "Successfully placed an Order!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to place the Order!").show();
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error occurred: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }


    public void AddToCartOnAction(ActionEvent actionEvent) {
        colItemID.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));



        String itemCode= (String) cmbItemCode.getValue();
        String description=txtDescription.getText();
        String size=txtSize.getText();
        Integer qty= Integer.valueOf(txtQty.getText());
        Double unitPrice= Double.valueOf(txtUnitPrice.getText());
        Double total=qty*unitPrice;

        if (Integer.parseInt(txtStock.getText())<qty){
            new Alert(Alert.AlertType.WARNING,"Invalid QTY").show();
        }else{
            cartTMS.add(new CartTM(itemCode,description,size,qty,unitPrice,total));
            calcNetTotal();
        }
        tblCart.setItems(cartTMS);

    }


    ObservableList<CartTM> cartTMS = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbItemCode.setItems(getItemCodes());
        cmbEmployeeID.setItems(getEmployeeIds());

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observableValue, s, newVal) -> {
            if (newVal!=null){
                searchItem(newVal);
            }
        });

    }
    private void calcNetTotal(){
        Double total=0.0;

        for (CartTM cartTM: cartTMS){
            total+=cartTM.getTotal();
        }

        lblNetTotal.setText(total.toString()+"/=");

    }
    private void searchItem(String itemCode) {
        ItemService itemService= ServiceFactory.getInstance().getServiceType(ServiceType.ITEM);
       ItemEntity entity=itemService.searchItemByCode(itemCode);
        txtDescription.setText(entity.getDescription());
        txtUnitPrice.setText(String.valueOf(entity.getUnitPrice()));
        txtSize.setText(entity.getSize());
        txtStock.setText(String.valueOf(entity.getQtyOnHand()));
    }

    public ObservableList<String> getEmployeeIds() {
        EmployeeDao employeeDao = DaoFactory.getInstance().getDaoType(DaoType.EMPLOYEE);
        ObservableList<String> employeeIds = FXCollections.observableArrayList();
        ObservableList<EmployeeEntity> employeeObservableList = employeeDao.getAll();
        employeeObservableList.forEach(employee -> {
            employeeIds.add(employee.getId());
        });

        return employeeIds;

    }

    public ObservableList<String> getItemCodes() {
        ItemDao itemDao = DaoFactory.getInstance().getDaoType(DaoType.ITEM);
        ObservableList<String> itemCodes = FXCollections.observableArrayList();
        ObservableList<ItemEntity> itemObservableList = itemDao.getAll();
        itemObservableList.forEach(item -> {
            itemCodes.add(item.getItemCode());
        });

        return itemCodes;

    }


}
