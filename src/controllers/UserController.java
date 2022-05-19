package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import application.DEMO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Customer;
import models.ErrorHandler;
import models.Observer;
import models.TransNotification;
import models.Transactions;
import models.functions;

public class UserController extends ErrorHandler implements Initializable{
	@FXML
	private TextField name;
	@FXML
	private Label feedback;
	@FXML
	private ComboBox fromAccount;
	@FXML
	private TextField toAccount;
	@FXML
	private TextField balance;
	@FXML
	private TextField date;
	@FXML
	private TextField email;
	@FXML
	private TextField address;
	@FXML
	private TextField amount;
	@FXML
	private TableView table;
	@FXML
    private TableColumn<Transactions, String> accountNames;
    @FXML
    private TableColumn<Transactions, String> transaName;
    @FXML
    private TableColumn<Transactions, String> transDate;
    
	private Customer user;
    private String title;
    private String message;
    
    ObservableList<Transactions> customerTransactionList;
	
	private ObservableList<String> acclist;
	functions f=new functions();
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			try {
				ArrayList<Observer>o=DEMO.notify.getNotification();
				//for(Observer o:DEMO.notify.getNotification()) {
				for(int i=0;i<o.size();i++) {
					user=(Customer)o.get(i);
					if(user.getId().getValue().equals(LoginController.currentCustomer)) {
						DEMO.notify.setTransOperations(user.getAmount(),user.getTrans());
						DEMO.notify.unregister(o.get(i));
						
					}
				}
				
				updatetable();
				acclist=f.getAccountsByCustomerId();
				fromAccount.setItems(acclist);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	@FXML
	void onShowData(ActionEvent event) throws SQLException {
		String[] data=new String[4];
		String accName=fromAccount.getSelectionModel().getSelectedItem().toString();
		String accId=accName.substring(0, accName.indexOf(":"));
		data=f.getUserData(accId);
		if(data != null) {
			name.setText(accName);
			name.setStyle("-fx-text-fill: black; -fx-font-size: 18px; -fx-font-family: 'Calibri';");
			balance.setText(data[0]+(f.getCurrencyByAccountId(accId).equals("USD")?" $":" LL"));
			balance.setStyle("-fx-text-fill: black; -fx-font-size: 18px; -fx-font-family: 'Calibri';");
			date.setText(data[1]);
			date.setStyle("-fx-text-fill: black; -fx-font-size: 18px; -fx-font-family: 'Calibri';");
			email.setText(data[2]);
			email.setStyle("-fx-text-fill: black; -fx-font-size: 18px; -fx-font-family: 'Calibri';");
			address.setText(data[3]);
			address.setStyle("-fx-text-fill: black; -fx-font-size: 18px; -fx-font-family: 'Calibri';");
			
		}
	}
	@FXML
	void onTransfer(ActionEvent event) throws SQLException {
		amount.setStyle(null);
		fromAccount.setStyle(null);
		toAccount.setStyle(null);
		if(amount.getText().isEmpty() || fromAccount.getSelectionModel().getSelectedItem()==null || toAccount.getText().isEmpty()) {
			this.title="Empty Fields";
			this.message="Please fill the missing or empty fields";
			this.makeErrorMessage();
			if(amount.getText().isEmpty()) amount.setStyle("-fx-border-color: red; -fx-borde-size: 2px;"); 
			if(fromAccount.getSelectionModel().getSelectedItem()==null) fromAccount.setStyle("-fx-border-color: red; -fx-borde-size: 2px;");
			if(toAccount.getText().isEmpty()) toAccount.setStyle("-fx-border-color: red; -fx-borde-size: 2px;");
			return;
		}
		String fromAccName=fromAccount.getSelectionModel().getSelectedItem().toString();
		String fromAccId=fromAccName.substring(0, fromAccName.indexOf(":"));
		String toAccName=toAccount.getText();
		//String toAccId=toAccName.substring(0, toAccName.indexOf(":"));
		String toAccId=f.getAccountIdByName(toAccName);
		if(toAccId==null) {
			this.title="Error";
			this.message="Wrong Account Name. Please enter a valid one.";
			this.makeErrorMessage();
			return;
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		
		if(!f.addTransferTransaction(LoginController.currentCustomer+":"+f.getCustomerNameByid(LoginController.currentCustomer),
				fromAccName,dateFormat.format(date) , Integer.parseInt(amount.getText()), toAccId,fromAccId)) {
			this.title="Error";
			this.message="Operation Failed.";
			this.makeErrorMessage();
			return;
		}else feedback.setText("Success!!");
		
		String cus_id=f.getCustomerId(toAccId);
		String customerId=cus_id.substring(0,cus_id.indexOf(":"));
		DEMO.notify.register(new Customer(customerId,f.getCustomerEmailByid(customerId),Integer.parseInt(amount.getText()),"Transfer"));
		
		updatetable();
	}
	@FXML
	void signOut(ActionEvent event) throws SQLException{
		try {
			DEMO.setRoot("Login", 400, 650);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@FXML
    void updatetable() {
		accountNames.setCellValueFactory(celldata->celldata.getValue().getAccountName());
		transaName.setCellValueFactory(celldata->celldata.getValue().getTransName());
		transDate.setCellValueFactory(celldata->celldata.getValue().getTransDate());
    	
    	try {
    		customerTransactionList = f.getTransactionData(LoginController.currentCustomer+":"+f.getCustomerNameByid(LoginController.currentCustomer));
    		
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	table.setItems(customerTransactionList);
	}
	@Override
	public String setTitle() {
		// TODO Auto-generated method stub
		return title;
	}
	@Override
	public String setMessage() {
		// TODO Auto-generated method stub
		return message;
	}

}
