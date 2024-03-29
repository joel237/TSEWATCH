package Launcher;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class DisplayController extends Application{
	private Stage primaryStage;
	private BorderPane rootLayout;
	//private static RootLayoutController rootLayCrtl;
	private static DisplayController instance;
	private static Stage sendMailStage;
	private static Stage addClientStage;
	private static Stage addReportStage;
	public static void display(String[] args) {
		launch(args);
	}
	
	/*
	 * Show the Mainpage Overview inside the root layout
	 */
	public void showMainpageOverview() {
		primaryStage.setResizable(false);
		try {
			// Load person overview
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getClassLoader().getResource("Home.fxml"));
			AnchorPane mainpageOverview = (AnchorPane) loader.load();
			Scene scene = new Scene(mainpageOverview);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	
	public void showAddReport() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getClassLoader().getResource("AddReport.fxml"));
			AnchorPane addReportPane;
			addReportPane = (AnchorPane) loader.load();
			addReportStage = new Stage();
			Scene scene = new Scene(addReportPane);
			addReportStage.setTitle("Ajouter Rapport");
			addReportStage.setScene(scene);
			addReportStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static DisplayController getInstance() {
		return instance;
	}

	public static void setInstance(DisplayController instance) {
		DisplayController.instance = instance;
	}

 	public void showAddClient() {
 		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getClassLoader().getResource("AddClient.fxml"));
			AnchorPane addClientPane;
			addClientPane = (AnchorPane) loader.load();
			addClientStage = new Stage();
			Scene scene = new Scene(addClientPane);
			addClientStage.setTitle("Ajouter Client");
			addClientStage.setScene(scene);
			addClientStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void showSendMail() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getClassLoader().getResource("SendMail.fxml"));
			AnchorPane sendMailPane = (AnchorPane) loader.load();
			sendMailStage = new Stage();
			Scene scene = new Scene(sendMailPane);
			sendMailStage.setTitle("Envoyer mail");
			sendMailStage.setScene(scene);
			sendMailStage.show();			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeSendMailStage() {
		sendMailStage.close();
	}
	
	public void closeAddClientStage() {
		addClientStage.close();
	}
	
	public void closeAddReportStage() {
		addReportStage.close();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("TSEWATCH");
		this.instance = this;
		
		showMainpageOverview();
		//showAddClient();
	}
	
}