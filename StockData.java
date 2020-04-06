
/**
 * A class to store the time stamps and close prices for the given stock 
 * 
 * @author jeris
 *
 */
public class StockData {
		
	private String time; 
	private double close;
	
	public StockData(String timeStamp, double closePrice) {
		time = timeStamp;
		close = closePrice;
		
	}

	public String getTime() {
		return time;
	}

	public double getClose() {
		return close;
	}
}