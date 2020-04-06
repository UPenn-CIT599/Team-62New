import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.util.HashMap;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Validation tests for StockDataReader
 * 
 * @author jeris
 *
 */

public class StockDataReaderTester {

	static int testDays = 11;
	static HashMap<Integer, StockData> testMap = new HashMap<Integer, StockData>();
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws IOException {
		StockDataReader stock = new StockDataReader("IBM", testDays);
		testMap = stock.fetchData();

	}
	
	@Test
	public void numberOfDays() {
		assertEquals(testDays, testMap.size(), "Check that you're returning the correct number of days");
	}



	
}
