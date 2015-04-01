import java.io.IOException;



//import JSoup bus'
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
//import Json 
import org.json.*;

//To get byStat output: doc.body().text()




public class Tester {

	static int numStats;
	static String descriptorURL = "http://usve74985.serverprofi24.com/API/descriptor.php";
		
	@SuppressWarnings("unused")
	public static void main(String args[])
	{
		String baseURL = "http://usve74985.serverprofi24.com/API/";
		String byStat = "by_stat.php?statID=";
		String byCountry = "by_country.php?countryIDs=";
		String descriptor = "descriptor.php";
		String param1 = "3";
		try {
			
			numStats = getNumStats();		
			Document doc = Jsoup.connect(baseURL + byCountry + param1).get();
			
			String bodyText = doc.body().text();

			JSONObject json = new JSONObject(bodyText);
			
			getCountryArrays(json);
			
			/*JSONObject jsonForCountry = json.getJSONObject(param1);
			
			System.out.println(jsonForCountry.get("2"));*/
			//System.out.println("By stat 3 get:\n" + doc.body().text());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public static int getNumStats()
	{
		//Get descriptor
		try {
			Document desc = Jsoup.connect(descriptorURL).get();
			String descText = desc.body().text();
			//System.out.println("{" +descText.substring(descText.indexOf("\"stats\""), descText.length()));
			JSONObject descJSON = new JSONObject("{" +descText.substring(descText.indexOf("\"stats\""), descText.length())); 
			JSONArray statsArray = descJSON.getJSONArray("stats");
			//System.out.println(statsArray.length());
			return statsArray.length();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not get the number of stats from the descriptor.");
			e.printStackTrace();
			return -1;
		}
	}
	
	public static void getCountryArrays(JSONObject country)
	{
		//Get the index of the country from it's json string, then step into the keys value
		String param = country.toString().charAt(2) + "";
		JSONObject json = country.getJSONObject(param);
		System.out.println(json);
		
		JSONArray[] jray = new JSONArray[numStats];
		
		
			
		
	}
	
}
