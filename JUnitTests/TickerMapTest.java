import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TickerMapTest {
    TickerMap map = new TickerMap();

    
    //Tests that the getTickerArray() method returns all 46 ticker symbols within the tickers TreeMap 
    @Test
    void testGetTickerArray() {  
        String[] expectedArr = {"AAPL", "AMZN", "AXP", "BA", "BAC", "BRK.B", "CAT", "CMCSA", "CSCO", "CVX", 
                "DIS", "DJIA", "DOW", "FB", "GE", "GOOG", "GOOGL", "GS", "HD", "IBM", "INTC", "IVV", "JNJ", 
                "JPM", "KO", "MA", "MCD", "MMM", "MRK", "MSFT", "NKE", "NOK", "PEP", "PFE", "PG", "SPY", "T", 
                "TRV", "UNH", "UTX", "V", "VOO", "VZ", "WBA", "WMT", "XOM"}; 

        assertArrayEquals(expectedArr, map.getTickerArray());
    }
        
    
    //Tests that the getNameOfTicker() method returns the appropriate company name when a ticker symbol is given
    @ParameterizedTest
    @CsvSource({
        "'HD', 'Home Depot'",
        "'JPM', 'JPMorgan Chase'",
        "'UTX', 'United Technologies'",
        "'VOO', 'Vanguard S&P 500'",
        "'WMT', 'Wal-Mart'",
        "'XOM', 'Exxon Mobil'"
    })
    void testGetNameOfTicker(String ticker, String expectedName) {
        assertEquals(expectedName, map.getNameOfTicker(ticker));
    }

}
