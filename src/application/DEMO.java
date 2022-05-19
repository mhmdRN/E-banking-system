package application;


import java.io.IOException;
import java.sql.Connection;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import models.Transactions;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;


public class DEMO extends Application {
	private static Scene scene;
	public static Stage anotherStage;
	public static Transactions notify;
	@Override
	public void start(Stage primaryStage) {
		try {
			anotherStage=primaryStage;
			Pane root =(Pane)FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
			scene = new Scene(root,400,650);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void setRoot(String fxml,int width,int height) throws IOException {
		Pane rt=new Pane();
		Scene scene1=new Scene(rt,width,height);
		scene1.setRoot(loadFXML(fxml));
		anotherStage.setScene(scene1);
		anotherStage.show();
        
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = setFXMLLoader(fxml);
        return fxmlLoader.load();
    }

    public static FXMLLoader setFXMLLoader(String fxml) {
        return new FXMLLoader(DEMO.class.getResource("/views/" + fxml + ".fxml"));
    }
    
	public static void main(String[] args) {
		notify=new Transactions();
		launch(args);
	}
}
