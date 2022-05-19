package controllers;

import java.io.IOException;
import java.sql.SQLException;

import application.DEMO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.Employer;
import models.ErrorHandler;
import models.functions;

public class LoginController extends ErrorHandler{
	@FXML
	private TextField email;
	@FXML
	private TextField password;
	@FXML
	private Button create;
	private String title;
	private String message;
	public static String currentEmployer,currentCustomer ;
	functions f=new functions();
	@FXML
	void login(ActionEvent event) throws SQLException, IOException {
		email.setStyle(null);
		password.setStyle(null);
		if(email.getText().isEmpty() || password.getText().isEmpty()) {
			this.title="Empty Fields";
			this.message="Please fill the missing or empty fields";
			this.makeErrorMessage();
			if(email.getText().isEmpty()) {
				email.setStyle("-fx-border-color: red; -fx-borde-size: 2px;");
			}
			if(password.getText().isEmpty())
				password.setStyle("-fx-border-color: red; -fx-borde-size: 2px;");
			return;
		}
		currentEmployer=f.getEmployerId(email.getText());
		if(currentEmployer!=null)
			if(password.getText().equals(f.getPassword(currentEmployer, "EMPLOYER"))) {
				DEMO.setRoot("Dashboard",805,650);
			}else {
				this.title="Wrong Credentials";
				this.message="Wrong email or password. Please try again.";
				this.makeErrorMessage();
				return;
			}
			
		else {
			currentCustomer=f.getCustomerIdByEmail(email.getText());
			if(currentCustomer!=null){
				if(password.getText().equals(f.getPassword(currentCustomer, "CUSTOMER"))) {
					DEMO.setRoot("User",980,800);
				}else {
					this.title="Wrong Credentials";
					this.message="Wrong email or password. Please try again.";
					this.makeErrorMessage();
					return;
				}
				
				
			}else {
				this.title="Wrong Credentials";
				this.message="Wrong email or password. Please try again.";
				this.makeErrorMessage();
				return;
			}
		}
		
	}
	@FXML
	void switchToSignUp(ActionEvent event) throws SQLException {
		try {
			
			DEMO.setRoot("Signup",400,650);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public String setTitle() {
		// TODO Auto-generated method stub
		return this.title;
	}
	@Override
	public String setMessage() {
		// TODO Auto-generated method stub
		return this.message;
	}
}
