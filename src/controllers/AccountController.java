package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.DEMO;
import iban4j.CountryCode;
import iban4j.Iban;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.Account;
import models.ErrorHandler;
import models.functions;

public class AccountController extends ErrorHandler implements Initializable{
	@FXML
	private TextField name;
	@FXML 
	private ComboBox<String> cusName;
	@FXML 
	private ComboBox<String> currencyList;
	@FXML
	private TextField balance;
	@FXML
	private DatePicker datepicker;
	@FXML
	private Label feedBack;
	private String[] currency= {"USD","LBP"};
	private String title;
	private String message;
	functions f=new functions();
	private ObservableList<String> cusList;
	private ObservableList<String> curList;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		curList=FXCollections.observableArrayList(currency);
		try {
			cusName.setItems(cusList=f.getAllCustomers());
			currencyList.setItems(curList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@FXML
	void onIbanAccount(ActionEvent event) {
		Iban iban = Iban.random(CountryCode.LB);
		name.setText(iban.toString());
	}
	@FXML
	void createAccount(ActionEvent event) throws NumberFormatException, SQLException {
		name.setStyle(null);
		cusName.setStyle(null);
		balance.setStyle(null);
		datepicker.setStyle(null);
		
		if(name.getText().isEmpty() || cusName.getSelectionModel().getSelectedItem()==null || balance.getText().isEmpty() 
				|| (datepicker.getValue()==null) || currencyList.getSelectionModel().getSelectedItem()==null) {
			this.title="Empty Fields";
			this.message="Please fill the missing or empty fields";
			this.makeErrorMessage();
			if(name.getText().isEmpty()) name.setStyle(null);
			if(cusName.getSelectionModel().getSelectedItem()==null) cusName.setStyle("-fx-border-color: red; -fx-borde-size: 2px;");
			if(currencyList.getSelectionModel().getSelectedItem()==null) currencyList.setStyle("-fx-border-color: red; -fx-borde-size: 2px;");
			if(balance.getText().isEmpty()) balance.setStyle("-fx-border-color: red; -fx-borde-size: 2px;");
			if(datepicker.getValue()==null) datepicker.setStyle("-fx-border-color: red; -fx-borde-size: 2px;");
			
			return;
		}
		if(f.checkNameAccountExists(name.getText())) {
			this.title="Error!";
			this.message="Account Name already exists. Please choose another one";
			this.makeErrorMessage();
			return;
		}
		String date=datepicker.getValue().toString();
		if(f.addAccount(name.getText(), cusName.getSelectionModel().getSelectedItem(), Integer.parseInt(balance.getText()),date,
				currencyList.getSelectionModel().getSelectedItem())){
			feedBack.setText("Account Created!");
		}else {
			this.title="Error";
			this.message="Something went wrong!!";
			this.makeErrorMessage();
			return;
		}
		
	}
	
	@FXML
	void switchToDashboard(ActionEvent event) throws SQLException {
		try {
			
			DEMO.setRoot("Dashboard",750,650);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
