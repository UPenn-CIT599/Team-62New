import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
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
	private boolean trend;
	
	public StockDataReader(String stockTicker, int TimeSeries, boolean trendLine) {
		symbol = stockTicker;
		time = TimeSeries;
		trend = trendLine;
		
	}

	public HashMap<Integer, StockData> fetchData() throws IOException {
		String function = "TIME_SERIES_DAILY"; // try TIME_SERIES_DAILY or TIME_SERIES_INTRADAY
		String functionTrend = "SMA";
		String interval = "Daily"; // (Daily) for TIME_SERIES_DAILY or 1min, 5min, 15min, 30min, 60min only for TIME_SERIES_INTRADAY
		String intervalTrend = "daily";
		String outputSize = "full"; // full or compact
		String label = "4. close";
		String dateLabel = "3. Last Refreshed";
		HashMap<Integer, StockData> timePrice = new HashMap<Integer, StockData>();
		URL url = null; 
		URL urlTrend = null;
		URLConnection urlConn;
		
		
		
		//check if only one day and then use intraday query
		if(time == 1) {
			function = "TIME_SERIES_INTRADAY";
			interval = "30min";
			outputSize = "compact";
			//url connection and reading from "Java Program to Read Stock Quote" https://www.youtube.com/watch?v=UVqjMbYlCFs
			if(trend == true) {
				urlTrend = new URL ("https://www.alphavantage.co/query?function=" + functionTrend + "&symbol=" + symbol 
						+ "&interval=" + interval + "&time_period=" + (time + 1) +"&series_type=close" + "&apikey=" + apiKey);
				urlConn = urlTrend.openConnection();
				label = "SMA";
				dateLabel = "3: Last Refreshed";
			}
			else {
				url = new URL ("https://www.alphavantage.co/query?function=" + function + "&symbol=" + symbol 
						+ "&interval=" + interval + "&outputsize=" + outputSize + "&apikey=" + apiKey);
				urlConn = url.openConnection();
			}
		}
		
		else if(time == 5) {
			function = "TIME_SERIES_INTRADAY";
			interval = "60min";	
			//url connection and reading from "Java Program to Read Stock Quote" https://www.youtube.com/watch?v=UVqjMbYlCFs
			if(trend == true) {
				urlTrend = new URL ("https://www.alphavantage.co/query?function=" + functionTrend + "&symbol=" + symbol 
						+ "&interval=" + interval + "&time_period=" + time +"&series_type=close" + "&apikey=" + apiKey);
				urlConn = urlTrend.openConnection();
				label = "SMA";
				dateLabel = "3: Last Refreshed";
			}
			else {
				url = new URL ("https://www.alphavantage.co/query?function=" + function + "&symbol=" + symbol 
						+ "&interval=" + interval + "&outputsize=" + outputSize + "&apikey=" + apiKey);
				urlConn = url.openConnection();
			}
		}

		else {
			//url connection and reading from "Java Program to Read Stock Quote" https://www.youtube.com/watch?v=UVqjMbYlCFs
			if(trend == true) {
				urlTrend = new URL ("https://www.alphavantage.co/query?function=" + functionTrend + "&symbol=" + symbol 
						+ "&interval=" + intervalTrend + "&time_period=" + time +"&series_type=close" + "&apikey=" + apiKey);
				urlConn = urlTrend.openConnection();
				label = "SMA";
				dateLabel = "3: Last Refreshed";
			}
			else {
				url = new URL ("https://www.alphavantage.co/query?function=" + function + "&symbol=" + symbol 
						+ "&outputsize=" + outputSize + "&apikey=" + apiKey);
				urlConn = url.openConnection();
			}
		}
		
		InputStreamReader inStream = new InputStreamReader(urlConn.getInputStream());
		
		//create json object from entire stream from url
		JsonObject obj = Json.parse(inStream).asObject();
		
		// get the current date 
		JsonObject meta = obj.get("Meta Data").asObject();
		String currentDay = meta.get(dateLabel).asString();
		String currentDate = currentDay.substring(0,10);
		LocalDate today = LocalDate.parse(currentDate);
		String stop = null;
		String stop1 = null;
		String stop2 = null;
		String stop3 = null;
		
		//create json object for time series from the larger json 
		JsonObject times;
		
		if(trend == true) {
			times = obj.get("Technical Analysis: SMA").asObject();
		}
		else {
			times = obj.get("Time Series (" + interval + ")").asObject();
		}
		
		
		//determine the beginning date of the series (i.e. when to stop looping through the json)
		//though we could determine if the date fell on a weekend, it is more difficult to determine market holidays, therefore
		//a series of stop dates are determined so that if the date 1 week, month or year prior is on a weekend or holiday, 
		//the process will stop on the next earliest market open date. 
		switch(time) {
			case 5: 
				stop = today.minusWeeks(1).toString();
				stop1 = today.minusWeeks(1).minusDays(1).toString();
				stop2 = today.minusWeeks(1).minusDays(2).toString();
				stop3 = today.minusWeeks(1).minusDays(3).toString();
				break;
			case 20: stop = today.minusMonths(1).toString();
				stop = today.minusMonths(1).toString();
				stop1 = today.minusMonths(1).minusDays(1).toString();
				stop2 = today.minusMonths(1).minusDays(2).toString();
				stop3 = today.minusMonths(1).minusDays(3).toString();
				break;
			case 260: 
				stop = today.minusYears(1).toString();
				stop1 = today.minusYears(1).minusDays(1).toString();
				stop2 = today.minusYears(1).minusDays(2).toString();
				stop3 = today.minusYears(1).minusDays(3).toString();
				break;	
		}
		
		// for each time interval in the json
		if(time == 1) {
			int i = 0;
			for (Member member : times) { 
				
				// if the time interval contains today's date
				if(member.getName().contains(currentDate)) { 
					// create json object of the time interval
					String memName = member.getName();
					JsonObject tickTimes = times.get(memName).asObject();
					String closeQuote = tickTimes.get(label).asString();
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
					String memName = member.getName();
					if(memName.contains(stop) || memName.contains(stop1) || memName.contains(stop2) || memName.contains(stop3)) {
						break;
					}
					JsonObject tickTimes = times.get(memName).asObject();
					String closeQuote = tickTimes.get(label).asString();
					double close = Double.valueOf(closeQuote); //convert close to double
					StockData stock = new StockData(memName, close);
					timePrice.put(i, stock);
					i++;
			}

		}
		
		return timePrice;
	}

}
