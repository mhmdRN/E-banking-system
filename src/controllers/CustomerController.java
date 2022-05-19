package controllers;

import java.io.IOException;
import java.sql.SQLException;

import application.DEMO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.ErrorHandler;
import models.functions;

public class CustomerController extends ErrorHandler{
	@FXML
	private TextField name;
	@FXML
	private TextField email;
	@FXML
	private TextField password;
	@FXML
	private TextField address;
	@FXML
	private Label feedback;
	private String title;
	private String message;
	functions f=new functions();
	@FXML
	void createCustomer(ActionEvent event) throws SQLException {
		name.setStyle(null);
		email.setStyle(null);
		password.setStyle(null);
		address.setStyle(null);
		if(name.getText().isEmpty() || email.getText().isEmpty() || password.getText().isEmpty() || address.getText().isEmpty()) {
			this.title="Empty Fields";
			this.message="Please fill the missing or empty fields";
			this.makeErrorMessage();
			if(name.getText().isEmpty()) name.setStyle("-fx-border-color: red; -fx-borde-size: 2px;");
			if(email.getText().isEmpty()) email.setStyle("-fx-border-color: red; -fx-borde-size: 2px;");
			if(password.getText().isEmpty()) password.setStyle("-fx-border-color: red; -fx-borde-size: 2px;");
			if(address.getText().isEmpty()) address.setStyle("-fx-border-color: red; -fx-borde-size: 2px;");
			
			return;
		}
		if(f.checkEmailExists(email.getText(), "CUSTOMER")) {
			this.title="Error";
			this.message="Email already exists. Please choose another one.";
			this.makeErrorMessage();
			return;
		}
		if(!f.addCustomer(name.getText(), email.getText(), password.getText(), address.getText())) {
			this.title="Error";
			this.message="Something went wrong!!";
			this.makeErrorMessage();
			return;
		}else feedback.setText("Success!");
		
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
