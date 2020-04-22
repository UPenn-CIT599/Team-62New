import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import com.eclipsesource.json.*;
import com.eclipsesource.json.JsonObject.Member;

/**
 * 
 * The StockDataReader retrieves times and prices for a given stock symbol from alphavantage.co
 * the fetchData method returns a hashmap of time stamps and prices either for every 30 min for a 1 day chart
 * or the daily close for the number of days provided to the method as an int
 * 
 * 
 * @author jeris
 *
 */
public class StockDataReader {
	
	private String apiKey = "HME2RSXV2FYKBRPX"; //apiKey = "HME2RSXV2FYKBRPX"; // i signed up for a free API key at https://www.alphavantage.co/
	private String symbol;
	private int time;
	
	
	public StockDataReader(String stockTicker, int TimeSeries) {
		symbol = stockTicker;
		time = TimeSeries;
	}

	public HashMap<Integer, StockData> fetchData() throws IOException {

		boolean oneDay = false;
		String function = "TIME_SERIES_DAILY"; // try TIME_SERIES_DAILY or TIME_SERIES_INTRADAY
		String interval = "Daily"; // (Daily) for TIME_SERIES_DAILY or 1min, 5min, 15min, 30min, 60min only for TIME_SERIES_INTRADAY
		String outputSize = "full"; // full or compact
		HashMap<Integer, StockData> timePrice = new HashMap<Integer, StockData>();
		URL url; 
		
		
		//check if only one day and then use intraday query
		if(time == 1) {
			oneDay = true;
			function = "TIME_SERIES_INTRADAY";
			interval = "30min";
			outputSize = "compact";
			
		}
		
		//url connection and reading from "Java Program to Read Stock Quote" https://www.youtube.com/watch?v=UVqjMbYlCFs
		if(oneDay == true) {
			url = new URL ("https://www.alphavantage.co/query?function=" + function + "&symbol=" + symbol 
					+ "&interval=" + interval + "&outputsize=" + outputSize + "&apikey=" + apiKey);
		}
		else {
			url = new URL ("https://www.alphavantage.co/query?function=" + function + "&symbol=" + symbol 
					+ "&outputsize=" + outputSize + "&apikey=" + apiKey);
		}
		
		
		URLConnection urlConn = url.openConnection();
		InputStreamReader inStream = new InputStreamReader(urlConn.getInputStream());
		
		//create json object from entire stream from url
		JsonObject obj = Json.parse(inStream).asObject();
		
		//create json object for time series from the larger json 
		JsonObject times = obj.get("Time Series (" + interval + ")").asObject();

		
		// for each time interval in the json
		if(oneDay == true) {
			// get the current date 
			JsonObject meta = obj.get("Meta Data").asObject();
			String currentDay = meta.get("3. Last Refreshed").asString();
			String currentDate = currentDay.substring(0,10);
			
			int i = 0;
			for (Member member : times) { 
				
				// if the time interval contains today's date
				if(member.getName().contains(currentDate)) { 
					
					// create json object of the time interval
					String memName = member.getName();
					JsonObject tickTimes = times.get(memName).asObject();
					String closeQuote = tickTimes.get("4. close").asString();
					double close = Double.valueOf(closeQuote); //convert close to double 
					StockData stock = new StockData(memName, close);
					timePrice.put(i, stock);
					i++;
				}
				
			}
		}
		
		// else for each time interval go through and get the close price for the requested number of days
		else {
			int i = 0;
				for(Member member : times) {
					if( i == time) {
						break;
					}
					String memName = member.getName();
					JsonObject tickTimes = times.get(memName).asObject();
					String closeQuote = tickTimes.get("4. close").asString();
					double close = Double.valueOf(closeQuote); //convert close to double
					StockData stock = new StockData(memName, close);
					timePrice.put(i, stock);
					i++;
			}

		}
		
		return timePrice;
	}

}
