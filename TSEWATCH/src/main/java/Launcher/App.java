package Launcher;

import java.util.ArrayList;

import Model.Avis;
import util.HTMLGenerator;
import util.Mailjet;

public class App {
	
	
	public static void main(String[] args) throws Exception {
		
		DisplayController.display(args);
	
		/**
		 * Test generating html file function
		 * 
		 * For the test,
		 * The html generated will be found just in the folder src/main/resources/ 
		 * TODO:
		 * Put files .css to their server(MUST DO)! So that we can get remote access to these files
		 * @param args
		 */
		
		/*************** Generate HTML PAGE **************/
//		
//		ArrayList<Avis> list2Test = new ArrayList<Avis>();
//		// Date, Titre ,Lien
//		list2Test.add(new Avis("06/06/2019", "Titre d'avis1", "https://www.google.com"));
//		list2Test.add(new Avis("05/06/2019", "Titre d'avis2", "https://www.telecom-st-etienne.fr"));
//		list2Test.add(new Avis("04/06/2019", "Titre d'avis3", "https://www.youtube.com"));
//		
//		HTMLGenerator generator = new HTMLGenerator(list2Test);
//		
//		generator.generateReport("src/main/resources/","report1");
//		
//		
//		/***************Send a email*******************/
//		/**
//		 *  10 email / hour (Limit by trial account)
//		 *  SendMail(Sender,Receicer)
//		 *  Sender can only be "gabin.guo@gmail.com"
//		 *  Receiver can be everybody
//		 */
//		Mailjet.SendMail("gabin.guo@gmail.com", "Enter here your receiver");
		
		
	}

}
