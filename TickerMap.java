import java.util.TreeMap;


/**
 * This class holds a TreeMap of all the ticker symbols available for the user to select from and its corresponding company name. 
 * @author gracepark
 *
 */
public class TickerMap {
    TreeMap<String, String> tickers;
    
    
    
    public TickerMap() {
        tickers = new TreeMap<String, String>();
        addTickers();
    }
    
    
    /**
     * This is a getter method of the tickers TreeMap instance variable.  
     * @return
     */
    public TreeMap<String, String> getTickers() {
        return tickers;
    }


    /**
     * This is a helper method called within the TickerMap constructor to add ticker symbols and corresponding company names to the tickers TreeMap. 
     */
    public void addTickers() {
        tickers.put("AAPL", "Apple");
        tickers.put("AMZN", "Amazon");
        tickers.put("AXP", "American Express");
        tickers.put("BA", "Boeing");
        tickers.put("BAC", "Bank of America");
        tickers.put("BRK.B", "Berkshire Hathaway");
        tickers.put("CAT", "Caterpillar");
        tickers.put("CMCSA", "Comcast");
        tickers.put("CSCO", "Cisco");
        tickers.put("CVX", "Chevron");
        tickers.put("DIS", "Walt Disney");
        tickers.put("DJIA", "Dow Jones Industrial Average");
        tickers.put("DOW", "Dow Chemical");
        tickers.put("FB", "Facebook");
        tickers.put("GE", "General Electric");
        tickers.put("GOOG", "Alphabet Class C");
        tickers.put("GOOGL", "Alphabet");
        tickers.put("GS", "Goldman Sachs");
        tickers.put("HD", "Home Depot");
        tickers.put("IBM", "IBM");
        tickers.put("INTC", "Intel");
        tickers.put("IVV", "iShares S&P 500");
        tickers.put("JNJ", "Johnson & Johnson");
        tickers.put("JPM", "JPMorgan Chase");
        tickers.put("KO", "Coca-Cola");
        tickers.put("MA", "Mastercard");
        tickers.put("MCD", "McDonald's");
        tickers.put("MMM", "3M");
        tickers.put("MRK", "Merck");
        tickers.put("MSFT", "Microsoft");
        tickers.put("NKE", "Nike");
        tickers.put("NOK", "Nokia");
        tickers.put("PEP", "PepsiCo");
        tickers.put("PFE", "Pfizer");
        tickers.put("PG", "Procter & Gamble");
        tickers.put("SPY", "SPDR S&P 500");
        tickers.put("T", "AT&T");
        tickers.put("TRV", "Travelers Companies Inc");
        tickers.put("UNH", "UnitedHealth");
        tickers.put("UTX", "United Technologies");
        tickers.put("V", "Visa");
        tickers.put("VOO", "Vanguard S&P 500");
        tickers.put("VZ", "Verizon");
        tickers.put("WBA", "Walgreen");
        tickers.put("WMT", "Wal-Mart");
        tickers.put("XOM", "Exxon Mobil");
    }
    
  
    /**
     * This method returns an array of the ticker symbols within the tickers TreeMap. 
     * @return String array of ticker symbols
     */
    public String[] getTickerArray() {
        String[] tickerSymbolsArr = new String[tickers.size()];
        int i = 0;
        for(String key : tickers.keySet()) {
            tickerSymbolsArr[i] = key;
            i++;
        }
        return tickerSymbolsArr;
    }
    
    
    /**
     * This method retrieves the company name of a given ticker symbol. 
     * @param ticker 
     * @return String value of company name
     */
    public String getNameOfTicker(String ticker) {
        return tickers.get(ticker);
    }
        

}
