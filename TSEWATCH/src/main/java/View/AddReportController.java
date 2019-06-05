package View;

import Launcher.DisplayController;
import javafx.fxml.FXML;

public class AddReportController {
	private DisplayController displayCtrl;
	
	@FXML
	public void initialize() {
		/**
		 *  get DisplayController
		 */
		displayCtrl = DisplayController.getInstance();
	}
	
	@FXML
	private void ARAnnulerButtonClicked() {
		displayCtrl.closeAddReportStage();
	}
}
