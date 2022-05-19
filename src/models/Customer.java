package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;

public class Customer implements Observer{
	private SimpleStringProperty name;
	private SimpleStringProperty address;
	private SimpleStringProperty email;
	private String password;
	private SimpleStringProperty id;
	private String amount;
	private String transName;
	public static int CUS_NB=100;
	
	public Customer(String id,String name,String email, String address) {
		this.name = new SimpleStringProperty(name);
		this.address = new SimpleStringProperty(address);
		this.email =new SimpleStringProperty(email);
		this.id = new SimpleStringProperty(id);
	}
	public Customer(String id,String email,int amount,String transName) {
		this.transName=transName;
		this.email=new SimpleStringProperty(email);
		this.id=new SimpleStringProperty(id);
		this.amount=amount+"";
	}
	public SimpleStringProperty getId() {
		return id;
	}
	public void setId(String id) {
		this.id = new SimpleStringProperty(id);
	}
	public SimpleStringProperty getName() {
		return name;
	}
	public void setName(String name) {
		this.name = new SimpleStringProperty(name);
	}
	public SimpleStringProperty getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = new SimpleStringProperty(address);
	}
	public SimpleStringProperty getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = new SimpleStringProperty(email);
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public static int getCUS_NB() {
		return CUS_NB;
	}
	public static void incrementID() {
		CUS_NB++;
	}
	@Override
	public void update(String amount,String trans) {
		// TODO Auto-generated method stub
		Alert a = new Alert(Alert.AlertType.INFORMATION);
	    a.setTitle(trans);
	    a.setHeaderText(amount+" "+(trans.equals("Withdraw")?"Withdrawed from":trans+"ed to")+" your account!!");
	    a.show();
	}
	public String getAmount() {
		// TODO Auto-generated method stub
		return this.amount;
	}
	public String getTrans() {
		// TODO Auto-generated method stub
		return this.transName;
	}
}
