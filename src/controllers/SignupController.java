package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.ErrorHandler;
import models.functions;

import java.io.IOException;
import java.sql.SQLException;

import application.DEMO;
import javafx.event.ActionEvent;
public class SignupController extends ErrorHandler{
	@FXML
	private TextField firstName;
	@FXML
	private TextField lastName;
	@FXML
	private TextField email;
	@FXML
	private PasswordField password;
	@FXML
	private PasswordField confirmPassword;
	private String title;
	private String message;
	functions f=new functions();
	
	@FXML
	void signUp(ActionEvent event) throws SQLException, IOException {
		firstName.setStyle(null);
		lastName.setStyle(null);
		email.setStyle(null);
		password.setStyle(null);
		confirmPassword.setStyle(null);
		
		if(firstName.getText().isEmpty() || lastName.getText().isEmpty() || email.getText().isEmpty() 
				|| password.getText().isEmpty() || confirmPassword.getText().isEmpty()) {
			this.title="Empty Fields";
			this.message="Please fill the missing or empty fields";
			this.makeErrorMessage();
			if(firstName.getText().isEmpty()) firstName.setStyle("-fx-border-color: red; -fx-borde-size: 2px;");
			if(lastName.getText().isEmpty()) lastName.setStyle("-fx-border-color: red; -fx-borde-size: 2px;");
			if(email.getText().isEmpty()) email.setStyle("-fx-border-color: red; -fx-borde-size: 2px;");
			if(password.getText().isEmpty()) password.setStyle("-fx-border-color: red; -fx-borde-size: 2px;");
			if(confirmPassword.getText().isEmpty()) confirmPassword.setStyle("-fx-border-color: red; -fx-borde-size: 2px;");
			return;
		}
		if(f.checkEmailExists(email.getText(),"EMPLOYER")) {
			this.title="Error!";
			this.message="Email already exists. Login instead.";
			this.makeErrorMessage();
			return;
		}
		if(!password.getText().equals(confirmPassword.getText())) {
			this.title="Error!";
			this.message="Passwords miss match.";
			this.makeErrorMessage();
			return;
		}
		if(!f.addEmployer(firstName.getText(),lastName.getText(),email.getText(),password.getText())) {
			this.title="Error";
			this.message="Something went wrong!!";
			this.makeErrorMessage();
			return;
		}
		DEMO.setRoot("Login",400,650);
	}
	@FXML
	void switchToLogin(ActionEvent event) {
		try {
			DEMO.setRoot("Login",400,650);
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
