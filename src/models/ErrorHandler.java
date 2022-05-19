package models;

import javafx.scene.control.Alert;

public abstract class ErrorHandler {
	
	 protected final void makeErrorMessage() {
		Alert a = new Alert(Alert.AlertType.WARNING);
	    a.setTitle(setTitle());
	    a.setHeaderText(setMessage());
	    a.show();		
	}
	public abstract String setTitle();
	public abstract String setMessage();
}
