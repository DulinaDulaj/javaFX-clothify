package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.CartTM;
import entity.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
import repository.DaoFactory;
import repository.custom.EmployeeDao;
import repository.custom.ItemDao;
import repository.custom.OrderDao;
import service.custom.EmployeeService;
import service.ServiceFactory;
import service.custom.ItemService;
import service.custom.OrderService;
import util.DaoType;
import util.ServiceType;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
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

    public Label lblTime;

    public Label lblDate;

    public Label lblOrderId;

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
            OrderService orderService = ServiceFactory.getInstance().getServiceType(ServiceType.ORDER);
            String orderId = lblOrderId.getText();
            String orderDate =lblDate.getText();
            String employeeId = cmbEmployeeID.getValue();

            EmployeeService employeeService = ServiceFactory.getInstance().getServiceType(ServiceType.EMPLOYEE);
            EmployeeEntity employeeEntity = employeeService.searchEmployeeById(employeeId);

            if (employeeEntity == null) {
                new Alert(Alert.AlertType.ERROR, "Invalid Employee ID!").show();
                return;
            }


            OrderEntity orderEntity = new OrderEntity(orderId, orderDate);


            List<OrderDetailEntity> orderDetails = new ArrayList<>();

            cartTMS.forEach(obj -> {
                String itemCode = obj.getItemCode();
                Integer quantity = obj.getQty();

                OrderDetailEntity orderDetail = new OrderDetailEntity(orderEntity, itemCode, quantity);
                orderDetails.add(orderDetail);
            });

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
        loadDateAndTime();
        setOrderID();



        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observableValue, s, newVal) -> {
            if (newVal!=null){
                searchItem(newVal);
            }
        });

    }
    public void setOrderID(){
        OrderDao orderDao = DaoFactory.getInstance().getDaoType(DaoType.ORDER);
        Long orderCount= orderDao.getOrderCount()+1;
        if(orderCount<10){
            lblOrderId.setText("R100"+orderCount);
        }else if(orderCount<100){
            lblOrderId.setText("R10"+orderCount);
        }else{
            lblOrderId.setText("R1"+orderCount);
        }

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
    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow = f.format(date);
        lblDate.setText(dateNow);

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime now = LocalTime.now();
            lblTime.setText(now.getHour() + " : " + now.getMinute() + " : " + now.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }


}
