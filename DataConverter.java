import java.util.HashMap;

/**
 * This class converts stock data stored within a HashMap to a String array of times and double array of stock values. 
 * @author gracepark
 *
 */
public class DataConverter {
    HashMap<Integer, StockData> stockData;
    
    public DataConverter(HashMap<Integer, StockData> stockData) {
        this.stockData = stockData;
    }
    
    /**
     * This method uses the data stored in the stockData HashMap to create an array of stock times.
     * @return String array of stock times
     */
    public String[] getStockTimes() {
    	int i = stockData.size();
    	String[] times = new String[i];
    	
    	for(int j = i - 1; j >= 0; j--) {
    		StockData stockTime = stockData.get(j);
    		times[j] = stockTime.getTime();
    	}
    	return times;
    }
    
    
    /**
     * This method uses the data stored in the stockData HashMap to create an array of stock values. 
     * @return double array of stock values
     */
    public double[] getStockValues() {
    	int i = stockData.size();
    	double[] values = new double[i];
    	
    	for(int j = i -1 ; j >= 0; j--) {
    		StockData stockValue = stockData.get(j);
    		values[j] = stockValue.getClose();
    	}
    	return values;        
    }

}
