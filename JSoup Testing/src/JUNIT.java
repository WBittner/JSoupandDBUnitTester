import java.io.IOException;





import java.util.ArrayList;

//import JSoup bus'
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
//import Json 
import org.json.*;

//To get byStat output: doc.body().text()




public class JUNIT {

	
	final static String descriptorURL = "http://usve74985.serverprofi24.com/API/descriptor.php";
	final static String byCountryURL = "http://usve74985.serverprofi24.com/API/by_country.php?countryIDs=";
	final static String byStatURL = "http://usve74985.serverprofi24.com/API/by_stat.php?statID=";

	static int numStats,numCountries,DEATHS,CASES,BIRTHS,POPULATION,MCV1,MCV2,
				ESTIMATED_MORTALITY,ESTIMATED_CASES_UB,ESTIMATED_CASES,
				ESTIMATED_MORTALITY_UB, ESTIMATED_MORTALITY_LB, ESTIMATED_CASES_LB;
								
	
	@SuppressWarnings("unused")
	public static void main(String args[]) throws IOException
	{
		String baseURL = "http://usve74985.serverprofi24.com/API/";
		String byStat = "by_stat.php?statID=";
		String byCountry = "by_country.php?countryIDs=";
		String descriptor = "descriptor.php";
		
		//set the number of countries and stats into their respective globals 
		setGlobals();
		
		String param1 = "3";
		JSONArray[] countryStats = getCountryArrays(param1);	
		//printArray(countryStats);
		
		double[] statStats = getStatArrays(DEATHS);
		
		for(double d : statStats)
			System.out.print(d + ", ");
		
	}


	//Sets numStats and numCountries into the globals from the descriptorURL
	public static void setGlobals() throws IOException
	{		
		//connect to descriptor page and get the text
		Document desc = Jsoup.connect(descriptorURL).get();
		String descText = desc.body().text();
				
		//Convert the text into json
		JSONObject descJSON = new JSONObject(descText);
		
		//Convert the value of the key "stats" into an array, with each
		//index having a stat
		JSONArray statsArray = descJSON.getJSONArray("stats");
		
		//Add stats to an arraylist so I can use the indexOf() function
		ArrayList<String> statsAL = new ArrayList<String>();
		for(int i = 0; i < statsArray.length(); i++)
			statsAL.add((String)statsArray.get(i));
		
		BIRTHS= statsAL.indexOf("Births");
		DEATHS= statsAL.indexOf("Deaths");
		CASES= statsAL.indexOf("Cases");
		POPULATION= statsAL.indexOf("Population");
		MCV1= statsAL.indexOf("MCV1");
		MCV2= statsAL.indexOf("MCV2");
		ESTIMATED_MORTALITY= statsAL.indexOf("Estimated Mortality");
		ESTIMATED_CASES= statsAL.indexOf("Estimated Cases");
		ESTIMATED_MORTALITY_UB= statsAL.indexOf("Estimated Mortality - Upper Bound");
		ESTIMATED_CASES_UB= statsAL.indexOf("Estimated Cases - Upper Bound");
		ESTIMATED_MORTALITY_LB= statsAL.indexOf("Estimated Mortality - Lower Bound");
		ESTIMATED_CASES_LB= statsAL.indexOf("Estimated Cases - Lower Bound");
	
		//set the size of the array of stats, and therefore the number of stats
		numStats = statsArray.length();
		
		//Convert the value of the key "cc3" into an array, with each
		//index having a country
		JSONArray countriesArray = descJSON.getJSONArray("cc3");
		
		//set the size of the array of countries, and therefore the number of countries
		numCountries = countriesArray.length();		
	}
	
	public static JSONArray[] getCountryArrays(String countryID) throws IOException
	{
		Document doc = Jsoup.connect(byCountryURL + countryID).get();
		String bodyText = doc.body().text();

		//convert the entire text into JSON
		JSONObject country = new JSONObject(bodyText);
		
		//Step into the country keys value
		JSONObject countryJSON = country.getJSONObject(countryID);
		
		//Create array, then assign each index a JSONArray for each stat
		JSONArray[] jray = new JSONArray[numStats];
		for(int i = 0; i < jray.length;i++)
			jray[i] = countryJSON.getJSONArray(countryID);
		
		return jray;	
	}
	
	public static double[] getStatArrays(int statID) throws IOException
	{
		//Connect to the byStat page and grab its text
		Document doc = Jsoup.connect(byStatURL + statID).get();
		String bodyText = doc.body().text();
		
		//Split the text into an array of Strings
		String[] sray = bodyText.split(",");
		
		//Remove [ and ] from first and last strings
		sray[0]=sray[0].substring(1);
		sray[numCountries-1] = sray[numCountries-1].substring(0, sray[numCountries-1].length()-1);
		
		//get rid of quotes around numbers
		for(int i = 0; i < numCountries; i++)
			sray[i]=sray[i].substring(1,sray[i].length()-1);
		
		//convert string into double and put into double array
		double[] dray = new double[numCountries];	
		for(int i = 0; i < numCountries; i++)
		{
			dray[i] = Double.parseDouble(sray[i]);
		}
		
		return dray;
	}
	
	public static void printArray(JSONArray[] ar)
	{
		for(int i = 0; i < ar.length; i++)
			System.out.println("Index " + i + ": " + ar[i]);
	}
	
}
