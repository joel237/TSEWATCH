package util;

public class Const {
	
	/**
	 *  Name list of websites we need to crawl 
	 */
	final static public String BOAMP = "https://www.boamp.fr/avis/liste";
	
	
	final static public String[] namesOfSites = {
			"Proxilegales", "Boamp" , "Marche-publics(info)",
			"Marche-publics(gouv)", "Auvergnerhonealpes",
			"Ted.europa","FranceMarche","E-marchespublics",
			"Centraledesmarches","Marchesonline"
	};
	
	/**
	 *  Configurations for Boamp.fr
	 */
	
	final static public String CONF1 = "estrecherchesimple";
	
	final static public String CONF2 = "archive";
	
	final static public String DESCRIPTION = "descripteur[]";
	
	final static public String AVIS = "typeavis[]"; /****** typeavis[] : 1 ~ 5  ********/
	
	final static public String[] listDescripteur = {"mc68","mc97","mc453","mc454","mc162","mc163","mc186","mc463","mc283","mc171"};
	
	/****** ******/
	final static public String DATESTART = "dateparutionmin";
	
	final static public String DATEEND = "dateparutionmax";
	
	
	
	
	
}
