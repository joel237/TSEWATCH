package View;

import Launcher.DisplayController;
import javafx.fxml.FXML;

public class SendMailController {
	
	private DisplayController displayCtrl;
	
	
	@FXML
	public void initialize() {
		/**
		 *  get DisplayController
		 */
		displayCtrl = DisplayController.getInstance();
	}
	
	
	@FXML 
	private void SMAnnulerButtonClicked() {
		displayCtrl.closeSendMailStage();
	}
}
