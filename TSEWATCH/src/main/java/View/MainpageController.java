package View;

import java.io.File;
import java.util.ArrayList;

import Launcher.DisplayController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import util.Const;

public class MainpageController extends Application{
	
	/**
	 *  Variables MainpageController 
	 */
	private static ArrayList<String> sites = new ArrayList<String>();
	/**
	 *  false -> hide | true -> display
	 */
	private boolean hideOptionsFlag = false;
	private DisplayController displayCtrl;
	
	/**
	 *  Variables FXML
	 */
	@FXML 
	private ChoiceBox<String> siteList ;
	@FXML
	private ImageView logoImageView;
	@FXML
	private Pane optionsPane;
	@FXML
	private Button optionsButton;
	@FXML
	private Button SMAnnulerButton;
	@FXML
	private Button DEAjouterButton;
	@FXML 
	private Button nouveauRapportButton;
	@FXML
	private Tab rechercheTab;
	@FXML
	private Tab axeDeVeilleTab;
	@FXML
	private Tab rapportsTab;
	@FXML
	private Tab diffusionEmailTab;
	
	
	@FXML
	public void initialize() {
		tabChangedColorWhenSelected();
		
		/**
		 *  Add the sites we want in the main page
		 */
		File file = new File("src/main/resources/logo.jpg");
		Image image = new Image(file.toURI().toString());
		logoImageView.setImage(image);
		
		for(String name : Const.namesOfSites) {
			sites.add(name);
		}

		ObservableList<String> list = FXCollections.observableArrayList(sites);
		siteList.setItems(list);
		
		/**
		 *  Hide the optionsPane (params side)
		 */
		optionsPane.setVisible(hideOptionsFlag);
		
		/**
		 *  get DisplayController for displaying or some new Tab
		 */
		displayCtrl = DisplayController.getInstance();
		
	}
	
	private void tabChangedColorWhenSelected() {
		rechercheTab.setStyle("-fx-background-image: url(\"backG.jpg\");");
		
		diffusionEmailTab.setOnSelectionChanged(event->{
			if(diffusionEmailTab.isSelected()) {
				diffusionEmailTab.setStyle("-fx-background-image: url(\"backG.jpg\");");
			
				axeDeVeilleTab.setStyle("-fx-background-image: none");
				rechercheTab.setStyle("-fx-background-image: none");
				rapportsTab.setStyle("-fx-background-image: none");
			}
		});
		
		axeDeVeilleTab.setOnSelectionChanged(event->{
			if(axeDeVeilleTab.isSelected()) {
				axeDeVeilleTab.setStyle("-fx-background-image: url(\"backG.jpg\");");
			
				diffusionEmailTab.setStyle("-fx-background-image: none");
				rechercheTab.setStyle("-fx-background-image: none");
				rapportsTab.setStyle("-fx-background-image: none");
			}
			
		});
		rapportsTab.setOnSelectionChanged(event->{
			if(rapportsTab.isSelected()) {
				rapportsTab.setStyle("-fx-background-image: url(\"backG.jpg\");");
			
				diffusionEmailTab.setStyle("-fx-background-image: none");
				axeDeVeilleTab.setStyle("-fx-background-image: none");
				rechercheTab.setStyle("-fx-background-image: none");
			}
		});
		rechercheTab.setOnSelectionChanged(event->{
			if(rechercheTab.isSelected()) {
				rechercheTab.setStyle("-fx-background-image: url(\"backG.jpg\");");
			
				diffusionEmailTab.setStyle("-fx-background-image: none");
				axeDeVeilleTab.setStyle("-fx-background-image: none");
				rapportsTab.setStyle("-fx-background-image: none");
			}
		});
	}
	
	@FXML
	private void optionsButtonClicked() {
		
		hideOptionsFlag = !hideOptionsFlag;
		optionsPane.setVisible(hideOptionsFlag);
		
	}
	
	@FXML
	private void DEAjouterButtonClicked() {
		displayCtrl.showAddClient();
	}
	
	@FXML
	private void envoyerButtonClicked() {
		displayCtrl.showSendMail();
	}
	
	@FXML
	private void nouveauRapportButtonClicked() {
		displayCtrl.showAddReport();
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
	}

}
