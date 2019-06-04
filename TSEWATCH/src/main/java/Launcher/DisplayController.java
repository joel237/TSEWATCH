package Launcher;

import java.io.IOException;
import java.net.URL;

import javax.swing.border.TitledBorder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class DisplayController extends Application{
	private Stage primaryStage;
	private BorderPane rootLayout;
	//private static RootLayoutController rootLayCrtl;
	private static DisplayController instance;
	
	public static void display(String[] args) {
		launch(args);
	}
	
	/*
	 * Show the Mainpage Overview inside the root layout
	 */
	public void showMainpageOverview() {
		try {
			// Load person overview
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getClassLoader().getResource("MainpageOverview.fxml"));
			AnchorPane mainpageOverview = (AnchorPane) loader.load();
			Scene scene = new Scene(mainpageOverview);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	public void showAddClient() throws IOException {
//		
//		try {
//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(getClass().getResource("/AddClient.fxml"));
//			TitledPane addClientPane;
//			addClientPane = (TitledPane) loader.load();
//			Scene scene = new Scene(addClientPane);
//			primaryStage.setScene(scene);
//			primaryStage.show();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
	
	public void showAddReport() {
		
	}
	
	public void showSendMain() {
		
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