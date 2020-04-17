

import java.util.TreeMap;

public class TickerList {
    TreeMap<String, String> tickers;
    
    public TickerList() {
        tickers = new TreeMap<String, String>();
        add();
    }
    
    
    
    public TreeMap<String, String> getTickers() {
        return tickers;
    }



    public void add() {
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
    
    public String[] getTickerArray() {
        String[] tickerSymbolsArr = new String[tickers.size()];
        int i = 0;
        for (String key : tickers.keySet()) {
            tickerSymbolsArr[i] = key;
            i++;
        }
        return tickerSymbolsArr;
    }
    
    public String getCompanyName(String ticker) {
        return tickers.get(ticker);
    }
        

}
