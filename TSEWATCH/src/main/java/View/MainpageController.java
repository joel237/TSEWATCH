package View;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import util.Const;

public class MainpageController extends Application{
	
	private static ArrayList<String> sites = new ArrayList<String>();
	
	@FXML 
	private ChoiceBox<String> siteList ;
	
	@FXML
	public void initialize() {
		/**
		 *  Add the sites we want in the main page
		 */
		for(String name : Const.namesOfSites) {
			sites.add(name);
		}
		
		ObservableList<String> list = FXCollections.observableArrayList(sites);
		siteList.setItems(list);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
	}

}
