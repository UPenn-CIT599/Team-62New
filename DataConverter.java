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
    	
    	int j = 0;
    	for(int k = i - 1; k >= 0; k--) {
    		StockData stockTime = stockData.get(j);
    		times[k] = stockTime.getTime();
    		j++;
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
    	
    	int j = 0;
    	for(int k = i -1 ; k >= 0; k--) {
    		StockData stockValue = stockData.get(j);
    		values[k] = stockValue.getClose();
    		j++;
    	}
    	return values;        
    }

}
