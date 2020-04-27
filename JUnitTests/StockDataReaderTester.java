import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import org.junit.BeforeClass;
import org.junit.Test;



/**
 * Validation test for StockDataReader
 * 
 * @author jeris
 *
 */

public class StockDataReaderTester {
	
	static int testDays;
	static HashMap<Integer, StockData> testMap = new HashMap<Integer, StockData>();
	static LocalDate date;
	static LocalDate stockDate;
	
	@BeforeClass
	public static void setUpBeforeClass() throws IOException {
		testDays = 20;
		StockDataReader stock = new StockDataReader("IBM", testDays);
		testMap = stock.fetchData();
		StockData stockData = testMap.get(0);
		stockDate = LocalDate.parse(stockData.getTime());
		date = LocalDate.now();
		String checkWeekend = date.toString();
		if(checkWeekend.equals("SUNDAY")) {
			date = date.minusDays(2);
		}
		if(checkWeekend.equals("SATURDAY")) {
			date = date.minusDays(1);
		}
		

	}
	
	//validate that the number of days being returned for 1 month is 20 days or greater
	@Test
	public void numberOfDaysTest() {
		assertTrue(testMap.size() >= testDays);
	}
	
	//validate that the first date being returned is today's date
	public void dateTest() {
		assertEquals(date, stockDate);
	}

	
}
