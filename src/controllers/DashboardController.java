package controllers;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Account;
import models.Customer;
import models.Transactions;
import models.functions;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


import application.DEMO;


/**
 * Controls the Dashboard view.
 */
public class DashboardController implements Initializable {
    public Label name;
    public Label balance;
    public PieChart piechart;

    public ScrollPane transactionPane;
    @FXML
    private TableColumn<Customer, String> idCol;
    @FXML
    private TableColumn<Customer, String> nameCol;
    @FXML
    private TableColumn<Customer, String> emailCol;
    @FXML
    private TableColumn<Customer, String> addressCol;
    
    @FXML
    private TableView<Customer> table;
    
    private ObservableList<Customer> customerList;
    private final ObservableList<PieChart.Data> accountsData = FXCollections.observableArrayList();
    @FXML
    private Label feedback;

    functions f=new functions();
    @FXML
    public void switchToDeposit(ActionEvent actionEvent) {
        try {
            DEMO.setRoot("Deposit",500,800);
        } catch (IOException e) {
            feedback.setText("An error occurred when trying to switch view.");
        }
    }
    
    @FXML
    public void switchToWithdraw(ActionEvent actionEvent) {
        try {
            DEMO.setRoot("Withdraw",500,800);
        } catch (IOException e) {
            feedback.setText("An error occurred when trying to switch view.");
        }
    }
    @FXML
    public void switchToCustomer(ActionEvent actionEvent) {
        try {
            DEMO.setRoot("Customer",400,650);
        } catch (IOException e) {
            feedback.setText("An error occurred when trying to switch view.");
        }
    }
    @FXML
    public void switchToAccount(ActionEvent actionEvent) {
        try {
            DEMO.setRoot("Account",400,650);
        } catch (IOException e) {
            feedback.setText("An error occurred when trying to switch view.");
        }
    }

    @FXML
    public void switchToLogin(ActionEvent actionEvent) {
        try {
            DEMO.setRoot("Login",400,650);
        } catch (IOException e) {
            feedback.setText("An error occurred when trying to switch view.");
        }
    }
  
    

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        		updatetable();
    }
    @FXML
    void updatetable() {
    	idCol.setCellValueFactory(celldata->celldata.getValue().getId());
    	nameCol.setCellValueFactory(celldata->celldata.getValue().getName());
    	emailCol.setCellValueFactory(celldata->celldata.getValue().getEmail());
    	addressCol.setCellValueFactory(celldata->celldata.getValue().getAddress());
    	try {
    		customerList=f.getCustomersData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	table.setItems(customerList);
	}


}
