package TODELETE;

public class CODEMAYBEUSEFUL {

	




////get links from https://www.saint-etienne.fr/node/5659
//	 private ArrayList<String> getLinksST(String dateParution/*params add here*/) throws Exception{
//		String url = "https://cg42.marches-publics.info/avis/index.cfm?fuseaction=pub.affResultats&IDs=11";
//		Map<String,String> params = new HashMap<String, String>();
//		params.put("dateParution", dateParution);
//		
//		// Get result
//		String result = sendPost(url, params);
//		Document doc = Jsoup.parse(result);
//		
//		ArrayList<String> listLinks = new ArrayList<String>();
//		
//		Elements eles = doc.getElementsByAttributeValueStarting("href", "index.cfm?fuseaction=pub.affPublication&refPub=");
//		for(Element ele : eles) {
//			listLinks.add("https://cg42.marches-publics.info/avis/" + ele.attr("href"));
//		}
//		return listLinks;
//}	
	
//  private static AvisST getInfoAvisSt(String url) throws Exception {
//	incrementCrawlUrlNumByOne();
//	Document doc = Jsoup.parse(getHTML(url));
//	/**
//	 * find data one by one
//	 */
//	Elements elesTop = doc.getElementsByAttributeValue("valign", "top");
////		private String title;
//	Elements topBTags = elesTop.get(0).getElementsByTag("b");
//	String title = topBTags.get(0).text();		
//	String[] elesBrTop = elesTop.get(0).html().toString().split("<br>");
////		private String nameOfPoster;
//	String nameOfPoster = elesBrTop[1].trim();
////		private String address;
//	String address = elesBrTop[2].trim() + " " + elesBrTop[3].trim();
////		private String phoneNum;
//	
//	String phoneNum = Regex.telNumber(elesBrTop[4]).trim();
//	if(null == phoneNum) phoneNum = "unknown";
//	
////		private String reference;
//	Elements elesTable = doc.getElementsByClass("AW_TableM_Bloc1_Clair");
//	String reference = elesTable.get(1).text();
////		private String typeOfMarket;
//	String typeOfMarket = elesTable.get(3).text();
////		private String description;
//	String description = elesTable.get(9).text();
////		private String deadline;
//	String deadline = Regex.deadline(getHTML(url)).replaceAll("</b>", "").replaceAll("<br />", "").replaceAll("</td>", "");
//	return new AvisST(title,nameOfPoster,address,phoneNum,reference,typeOfMarket,description,deadline);
//}
	

//Download Function
//Param : Url to download
//	private static void download(String url,String nameOfFile)throws MalformedURLException {
//		URL site = new URL(url);
//		Calendar calendar = Calendar.getInstance();
//		Date time = calendar.getTime();
//		int lengthFolderName = time.toString().replaceAll(" ", "").length();
//		String folderName = time.toString().replaceAll(" ", "").substring(3, lengthFolderName-9);
//		folderName = folderName.substring(0, 5) + "_" + folderName.substring(5).replaceAll(":", "-");
//		try {
//			File directory = new File(String.valueOf("./Boamp/" + folderName + "/"));
//			if(!directory.exists()) {
//				directory.mkdir();
//			}
//			FileOutputStream fos = new FileOutputStream(new File("./Boamp/" + folderName + "/" +  nameOfFile + ".pdf"));
//			InputStream in = site.openStream();
//			//System.out.println("reading from resource and writing to file...");
//			int length = -1;
//			byte[] buffer = new byte[1024];
//			while((length=in.read(buffer)) > -1) {
//				fos.write(buffer,0,length);
//			}
//			
//			fos.close();
//			in.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} 
//		
//		incrementDownloadNumByOne();
//	}
	
}
