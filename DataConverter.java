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
        
    }
    
    
    /**
     * This method uses the data stored in the stockData HashMap to create an array of stock values. 
     * @return double array of stock values
     */
    public double[] getStockValues() {
        
    }

}
