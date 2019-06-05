package View;

import Launcher.DisplayController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddClientController {
	
	
	
	
	@FXML
	private Button ACAjouterButton;
	
	@FXML
	private TextField clientText;
	
	@FXML 
	private TextField emailText;
	
	@FXML
	private DisplayController displayCtrl;
	
	
	@FXML
	public void initialize() {
		/**
		 *  get DisplayController
		 */
		displayCtrl = DisplayController.getInstance();
	}
	 
	@FXML
	private void ACAnnulerButtonClicked() {
		displayCtrl.closeAddClientStage();
	}
	
}
