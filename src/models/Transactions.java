package models;

import java.util.ArrayList;
import java.util.Date;

import javafx.beans.property.SimpleStringProperty;

public class Transactions implements TransNotification{
	private String employerId;
	private String customerId;
	private SimpleStringProperty transName;
	private SimpleStringProperty accountName;
	private SimpleStringProperty transDate;
	private String amount;
	private ArrayList<Observer> notification;
	public ArrayList<Observer> getNotification() {
		return notification;
	}
	public Transactions(String employerId, String customerId, String accountName,String transName, String transDate) {
		this.employerId = employerId;
		this.customerId = customerId;
		this.transName = new SimpleStringProperty(transName);
		this.transDate = new SimpleStringProperty(transDate);
		this.accountName=new SimpleStringProperty(accountName);
	}
	public Transactions(String accName, String name, String date) {
		// TODO Auto-generated constructor stub
		this.transName = new SimpleStringProperty(name);
		this.transDate = new SimpleStringProperty(date);
		this.accountName=new SimpleStringProperty(accName);
	}
	
	public Transactions() {
		// TODO Auto-generated constructor stub
		this.notification=new ArrayList<>();
		
	}
	public String getEmployerId() {
		return employerId;
	}
	public void setEmployerId(String employerId) {
		this.employerId = employerId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public SimpleStringProperty getTransName() {
		return transName;
	}
	public void setTransName(String transName) {
		this.transName = new SimpleStringProperty(transName);
	}
	public SimpleStringProperty getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = new SimpleStringProperty(transDate);
	}
	public SimpleStringProperty getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = new SimpleStringProperty(accountName);
	}
	
	@Override
	public void register(Observer  o) {
		// TODO Auto-generated method stub
		notification.add(o);
	}
	@Override
	public void unregister(Observer o) {
		// TODO Auto-generated method stub                 
		notification.remove(o);
	}
	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub
		for(Observer o:notification)
			o.update(amount,transName.getValue().toString());
	}
	public void setTransOperations(String amount,String trans) {
		
		this.amount=amount;
		this.transName=new SimpleStringProperty(trans);
		notifyObservers();
	}
	
}
