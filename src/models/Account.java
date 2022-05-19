package models;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Account {
	private String customerId;
	private String employerId;
	private String accName;
	private Double balance;
	private Date openDate;
	private String currency;
	private static int AccNB=0;
	
	public static int getAccNB() {
		return AccNB;
	}
	public static void incrementId() {
		AccNB++;
	}
	public Account(String customerId, String employerId, String accName, Double balance, Date openDate,String currency) {
		this.customerId = customerId;
		this.employerId = employerId;
		this.accName = accName;
		this.balance = balance;
		this.openDate = openDate;
		this.currency=currency;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getEmployerId() {
		return employerId;
	}
	public void setEmployerId(String employerId) {
		this.employerId = employerId;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	
}
