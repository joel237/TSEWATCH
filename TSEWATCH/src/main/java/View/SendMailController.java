package View;

import Launcher.DisplayController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

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
	public void SMAnnulerButtonClicked() {
		displayCtrl.closeSendMailStage();
	}
	
}
