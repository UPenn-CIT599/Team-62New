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
	
	static int testDays;
	static HashMap<Integer, StockData> testMap = new HashMap<Integer, StockData>();
	
	@BeforeClass
	public static void setUpBeforeClass() throws IOException {
		testDays = 20;
		StockDataReaderModified stock = new StockDataReaderModified("IBM", testDays);
		testMap = stock.fetchData();

	}
	
	@Test
	public void numberOfDaysTest() {
		assertTrue(testMap.size() >= testDays);
	}
	

}
