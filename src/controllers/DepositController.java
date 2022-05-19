package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.DEMO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import models.Customer;
import models.ErrorHandler;
import models.functions;

public class DepositController extends ErrorHandler implements Initializable{
	@FXML
	private TextField amount;
	@FXML
	private ComboBox accountNames;
	@FXML
	private ComboBox currencySelector;
	@FXML
	private Text feedback;
	@FXML
	private DatePicker datepicker;
	functions f=new functions();
	private ObservableList<String> accList;
	private String curr[]={"USD","LBP"};
	private String currency;
	private String title;
	private String message;
	@FXML
	void switchToDashboard(ActionEvent event) throws SQLException {
		try {
			
			DEMO.setRoot("Dashboard",750,650);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@FXML
	void deposit(ActionEvent event) throws SQLException {
		amount.setStyle(null);
		accountNames.setStyle(null);
		datepicker.setStyle(null);
		currencySelector.setStyle(null);
		if(amount.getText().isEmpty() || (datepicker.getValue()==null) || (accountNames.getSelectionModel().getSelectedItem()==null) ||(currencySelector.getSelectionModel().getSelectedItem()==null)) {
			this.title="Empty Fields";
			this.message="Please fill the missing or empty fields";
			this.makeErrorMessage();
			if(amount.getText().isEmpty()) amount.setStyle("-fx-border-color: red; -fx-borde-size: 2px;");
			if(datepicker.getValue()==null) datepicker.setStyle("-fx-border-color: red; -fx-borde-size: 2px;");
			if(accountNames.getSelectionModel().getSelectedItem()==null) accountNames.setStyle("-fx-border-color: red; -fx-borde-size: 2px;");
			if(currencySelector.getSelectionModel().getSelectedItem()==null) currencySelector.setStyle("-fx-border-color: red; -fx-borde-size: 2px;");
			
			return;
		}
		String date=datepicker.getValue().toString();
		String accName=accountNames.getSelectionModel().getSelectedItem().toString();
		String accId=accName.substring(0, accName.indexOf(":"));
		String cusId=f.getCustomerId(accId);
		accName=accName.substring(0,accName.indexOf("("));
		if(f.addTransaction(LoginController.currentEmployer, cusId,accName, "DEPOSIT", date,Integer.parseInt(amount.getText()),accId)) {
			feedback.setText("Success");
		}else {
			this.title="Error";
			this.message="Something went wrong!!. Operation Failed";
			this.makeErrorMessage();
			return;
		}
		String customerId=cusId.substring(0,cusId.indexOf(":"));
		DEMO.notify.register(new Customer(customerId,f.getCustomerEmailByid(customerId),Integer.parseInt(amount.getText()),"Deposit"));
	}
	
	@FXML
	void onSelectCurrency(ActionEvent event) throws SQLException {
		currency=currencySelector.getSelectionModel().getSelectedItem().toString();
		accList=f.getAllAccounts(currency);
		accountNames.setItems(accList);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
			
		currencySelector.setItems(FXCollections.observableArrayList(curr));
		
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
