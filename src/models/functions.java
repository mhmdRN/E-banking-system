package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


import application.DataBase;
import controllers.LoginController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class functions extends ErrorHandler{
	private String title;
	private String message;
	public boolean addEmployer(String firstName, String lastName, String email, String password) throws SQLException {
		Connection conn=DataBase.connect();
		 PreparedStatement pst=conn.prepareStatement("insert into EMPLOYER values (?,?,?,?,?)");

		pst.setString(1,"EMP_"+(this.getCounterDatabase("EMPLOYER")+1));
		//Employer.incrementID();
		pst.setString(2,firstName);
		pst.setString(3, lastName);
		pst.setString(4, email);
		pst.setString(5, password);
		int rs =pst.executeUpdate();

		if(rs==1) {
			return true;
		}
		
		return false;
	    	
	}
	public boolean addCustomer(String name, String email, String password, String address) throws SQLException {
		Connection conn=DataBase.connect();
		 PreparedStatement pst=conn.prepareStatement("insert into CUSTOMER values (?,?,?,?,?)");

		pst.setString(1,"Cus_"+(this.getCounterDatabase("CUSTOMER")+1));
		//Customer.incrementID();;
		pst.setString(2,name);
		pst.setString(3, email);
		pst.setString(4, password);
		pst.setString(5, address);
		int rs =pst.executeUpdate();

		if(rs==1) {
			return true;
		}
		
		return false;
	    	
	}
	public boolean addAccount(String name, String cusId, int balance, String date,String currency) throws SQLException {
		Connection conn=DataBase.connect();
		PreparedStatement pst=conn.prepareStatement("insert into Account values (?,?,?,?,?,?,?)");
		pst.setString(1,"ACC_"+(this.getCounterDatabase("ACCOUNT")+1));
		//Account.incrementId();
		pst.setString(2, cusId);
		pst.setString(3,LoginController.currentEmployer);
		pst.setString(4,name);
		pst.setInt(5, balance);
		pst.setString(6,date);
		
		pst.setString(7,currency);
		int rs =pst.executeUpdate();

		if(rs==1) {
			return true;
		}
		
		return false;
	    	
	}
	public boolean addTransaction(String empId, String cusId,String accName ,String name, String date,int amount,String accId) throws SQLException {
		Connection conn=DataBase.connect();
		int balance;
		PreparedStatement pst1=conn.prepareStatement("Select balance from account where ACC_ID=?");
		pst1.setString(1, accId);
		ResultSet rs=pst1.executeQuery();
		if(rs.next()) {
			
			balance=rs.getInt("BALANCE");
			if(name.equals("WITHDRAW")) {
				PreparedStatement pst2=conn.prepareStatement("update ACCOUNT SET balance=balance -? where ACC_ID=?");
				pst2.setInt(1,amount);
				pst2.setString(2, accId);
				if(amount>balance) {
					this.title="Error!";
					this.message="Unsufficient amount of money";
					this.makeErrorMessage();
					return false;
				}else {
					int done=pst2.executeUpdate();
					if(done!=1) return false;
				}
			}else if(name.equals("DEPOSIT")) {
				PreparedStatement pst2=conn.prepareStatement("update ACCOUNT SET balance=balance +? where ACC_ID=?");
				pst2.setInt(1,amount);
				pst2.setString(2,accId);
				int done=pst2.executeUpdate();
				if(done!=1) return false;
				
			}
			PreparedStatement pst=conn.prepareStatement("insert into Transactions values (?,?,?,?,?,?)");
			
			pst.setString(1,"TRANS_"+(this.getCounterDatabase("TRANSACTIONS")+1));
			//Transactions.incrementId();
			
			pst.setString(2,empId);
			pst.setString(3, cusId);
			pst.setString(4, accName);
			pst.setString(5,name);
			pst.setString(6,date);
			int result =pst.executeUpdate();

			if(result==1) {
				return true;
			}else return false;
		}
		
	    	return false;
	}
	public boolean addTransferTransaction(String cusId,String accName, String date,int amount,String ToAccId,String fromAccId) throws SQLException {
		Connection conn=DataBase.connect();
		int balance;
		PreparedStatement pst1=conn.prepareStatement("Select balance from account where ACC_ID=?");
		pst1.setString(1, fromAccId);
		ResultSet rs=pst1.executeQuery();
		if(rs.next()) {
				balance=rs.getInt("BALANCE");
				if(amount>balance) {
					this.title="Error!";
					this.message="Unsufficient amount of money";
					this.makeErrorMessage();
					return false;
				}
				PreparedStatement pst2=conn.prepareStatement("update ACCOUNT SET balance=balance -? where ACC_ID=?");
				pst2.setInt(1,amount);
				pst2.setString(2, fromAccId);
				if(pst2.executeUpdate()!=1) return false;
				
				PreparedStatement pst3=conn.prepareStatement("update ACCOUNT SET balance=balance +? where ACC_ID=?");
				pst3.setInt(1,amount);
				pst3.setString(2, ToAccId);
				if(pst3.executeUpdate()!=1) return false;
							
			
			PreparedStatement pst=conn.prepareStatement("insert into Transactions values (?,?,?,?,?,?)");
			
			pst.setString(1,"TRANS_"+(this.getCounterDatabase("TRANSACTIONS")+1));
			//Transactions.incrementId();
			
			pst.setString(2,null);
			pst.setString(3, cusId);
			pst.setString(4, accName);
			pst.setString(5,"TRANSFER");
			pst.setString(6,date);
			int result =pst.executeUpdate();

			if(result==1) {
				return true;
			}else return false;
	}
		return false;
}
	public boolean checkEmailExists(String mail,String TableName) throws SQLException {
		Connection conn=DataBase.connect();
		PreparedStatement ps = null;
		if(TableName.equals("EMPLOYER")) {
			ps=conn.prepareStatement("select email from employer where email=?");
		}else if(TableName.equals("CUSTOMER")) {
			ps=conn.prepareStatement("select email from customer where email=?");
		}
		ps.setString(1, mail);
		ResultSet rs=ps.executeQuery();
		if(rs.next()) {
			return true;
		}
		return false;
	}
	public boolean checkNameAccountExists(String accname) throws SQLException {
		Connection conn=DataBase.connect();
		PreparedStatement ps=conn.prepareStatement("select NAME from account where Name=?");
		ps.setString(1, accname);
		ResultSet rs=ps.executeQuery();
		if(rs.next()) {
			return true;
		}
		return false;
	}
	//get the list of customers
	public ObservableList getAllCustomers() throws SQLException {
		ObservableList<String> list=FXCollections.observableArrayList();
		Connection conn=DataBase.connect();
		PreparedStatement ps=conn.prepareStatement("select CUS_ID,name from customer");
		ResultSet rs=ps.executeQuery();
		while(rs.next()){
			String name=rs.getString("name");
			String id=rs.getString("CUS_ID");
			list.add(id+":"+name);
		}
		return list;
		
	}
	//get the list of accounts
	public ObservableList getAllAccounts(String currency) throws SQLException {
			ObservableList<String> list=FXCollections.observableArrayList();
			Connection conn=DataBase.connect();
			PreparedStatement ps=conn.prepareStatement("select ACC_ID,CUS_ID,name from account where CURRENCY=?");
			ps.setString(1, currency);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				String acc=rs.getString("ACC_ID");
				String name=rs.getString("name");
				String id=rs.getString("CUS_ID");
				String cusName=id.substring(6, id.length());
				list.add(acc+":"+name+"("+cusName+")");
			}
			return list;
			
		}
	//get employer id from his email address
	public String getEmployerId(String email) throws SQLException {
		Connection conn=DataBase.connect();
		PreparedStatement ps=conn.prepareStatement("select EMP_ID from employer where email=?");
		ps.setString(1,email);
		ResultSet rs=ps.executeQuery();
		if(rs.next())
			
			return rs.getString("EMP_ID");
		
		return null;
	}
	//get customer id from his email
	public String getCustomerIdByEmail(String email) throws SQLException {
		Connection conn=DataBase.connect();
		PreparedStatement ps=conn.prepareStatement("select CUS_ID from customer where email=?");
		ps.setString(1,email);
		ResultSet rs=ps.executeQuery();
		if(rs.next())
			
			return rs.getString("CUS_ID");
		
		return null;
	}
	//get customer id from his account id
	public String getCustomerId(String accId) throws SQLException {
		Connection conn=DataBase.connect();
		PreparedStatement ps=conn.prepareStatement("select CUS_ID from account where ACC_ID=?");
		ps.setString(1,accId);
		ResultSet rs=ps.executeQuery();
		if(rs.next())
			return rs.getString("CUS_ID");
		
		return null;
	}
	//get customer or employer password
	public String getPassword(String accId,String tableName) throws SQLException {
		Connection conn=DataBase.connect();
		PreparedStatement ps=null;
		if(tableName.equals("EMPLOYER")) {
			ps=conn.prepareStatement("select PASSWORD from employer where EMP_ID=?");
		}else if(tableName.equals("CUSTOMER")) {
			ps=conn.prepareStatement("select PASSWORD from customer where CUS_ID=?");	
		}
		ps.setString(1,accId);
		ResultSet rs=ps.executeQuery();
		if(rs.next())
			return rs.getString("PASSWORD");
		
		return null;
	}
	//get name by customer id
	public String getCustomerNameByid(String id) throws SQLException {
		Connection conn=DataBase.connect();
		PreparedStatement ps=conn.prepareStatement("select name from customer where CUS_ID=?");
		ps.setString(1,id);
		ResultSet rs=ps.executeQuery();
		if(rs.next())
			
			return rs.getString("NAME");
		
		return null;
	}
	//get customer email by customer id
	public String getCustomerEmailByid(String id) throws SQLException {
		Connection conn=DataBase.connect();
		PreparedStatement ps=conn.prepareStatement("select email from customer where CUS_ID=?");
		ps.setString(1,id);
		ResultSet rs=ps.executeQuery();
		if(rs.next())
			
			return rs.getString("EMAIL");
		
		return null;
	}
	//get currency by account id
		public String getCurrencyByAccountId(String id) throws SQLException {
			Connection conn=DataBase.connect();
			PreparedStatement ps=conn.prepareStatement("select currency from account where ACC_ID=?");
			ps.setString(1,id);
			ResultSet rs=ps.executeQuery();
			if(rs.next())

				return rs.getString("CURRENCY");
			
			return null;
		}
	//get List of customers
	public static ObservableList<Customer> getCustomersData() throws SQLException{
		  
		  Connection conn=DataBase.connect();
		 ObservableList<Customer> list=FXCollections.observableArrayList();
		  
		  try {
			  PreparedStatement ps=conn.prepareStatement("select * from Customer");
			  ResultSet rs=ps.executeQuery();
			  while(rs.next()) {
				  String cusId=""+rs.getString("CUS_ID");
				  String name=""+rs.getString("NAME");
				  String email=""+rs.getString("EMAIL");
				  String address=""+rs.getString("ADDRESS");
				  Customer c=new Customer(cusId,name,email,address);
				  list.add(c);
			  }
		  }catch(Exception e){
			  System.out.println("error");
		  }
		  
		  return list;
		  
	  }
	
	//get List of customers
		public static ObservableList<Transactions> getTransactionData(String cusId) throws SQLException{
			  
			  Connection conn=DataBase.connect();
			 ObservableList<Transactions> list=FXCollections.observableArrayList();
			  
			  try {
				  PreparedStatement ps=conn.prepareStatement("select * from Transactions where CUS_ID=?");
				  ps.setString(1, cusId);
				  ResultSet rs=ps.executeQuery();
				  while(rs.next()) {
					  String accName=""+rs.getString("AccountNumber");
					  String name=""+rs.getString("NAME");
					  String date=""+rs.getString("TRANSDATE");
					  Transactions t=new Transactions(accName,name,date);
					  list.add(t);
					  
				  }
				  
			  }catch(Exception e){
				  System.out.println("error");
			  }
			  
			  return list;
			  
		  }
	//get accounts for a specific customer
	public  ObservableList getAccountsByCustomerId() throws SQLException{
		  
		ObservableList<String> list=FXCollections.observableArrayList();
		Connection conn=DataBase.connect();
		String cusId = null;
		try {
			cusId=LoginController.currentCustomer+":"+getCustomerNameByid(LoginController.currentCustomer.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PreparedStatement ps=conn.prepareStatement("select ACC_ID,name from account where CUS_ID=?");
		ps.setString(1, cusId);
		ResultSet rs=ps.executeQuery();
		while(rs.next()){
			String name=rs.getString("name");
			String id=rs.getString("ACC_ID");
			list.add(id+":"+name);
		}
		return list;
		  
	  }
	//get user data from database
	public String[] getUserData(String accId) throws SQLException {
		Connection conn=DataBase.connect();
		String data[]=new String[4];
		PreparedStatement ps=conn.prepareStatement("select balance,dateopened from account where ACC_ID=?");
		ps.setString(1,accId);
		ResultSet rs=ps.executeQuery();
		if(rs.next()) {
			data[0]=rs.getString("BALANCE");
			data[1]=rs.getString("DATEOPENED");
		}else return null;
		ps=conn.prepareStatement("select email,address from customer where CUS_ID=?");
		ps.setString(1, LoginController.currentCustomer);
		ResultSet rs1=ps.executeQuery();
		if(rs1.next()) {
			data[2]=rs1.getString("EMAIL");
			data[3]=rs1.getString("ADDRESS");
		}else return null;
		return data;
	}
	//get account id by name
	public String getAccountIdByName(String name) throws SQLException {
		Connection conn=DataBase.connect();
		PreparedStatement ps=conn.prepareStatement("select ACC_ID from account where NAME=?");
		ps.setString(1,name);
		ResultSet rs=ps.executeQuery();
		if(rs.next())
			return rs.getString("ACC_ID");
		
		return null;
	}
	//get id counter for data base
	public int getCounterDatabase(String tableName) throws SQLException {
		Connection conn=DataBase.connect();
		PreparedStatement ps = null;
		if(tableName.equals("EMPLOYER")) {
			 ps=conn.prepareStatement("select EMP_ID from employer");
		}else if(tableName.equals("CUSTOMER")) {
			 ps=conn.prepareStatement("select CUS_ID from customer");
		}else if(tableName.equals("ACCOUNT")) {
			 ps=conn.prepareStatement("select ACC_ID from account");
		}else if(tableName.equals("TRANSACTIONS")) {
			 ps=conn.prepareStatement("select TRANS_ID from transactions");
		}
		ResultSet rs=ps.executeQuery();
		int id=0;
		while(rs.next()) id++;
		return id;
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
