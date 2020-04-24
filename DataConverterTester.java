import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.util.HashMap;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

class DataConverterTester {
	
	static DataConverter converter;
	static HashMap<Integer, StockData> testMap = new HashMap<Integer, StockData>();
	static int times;
	static int values;
	
	@BeforeClass
	public static void setUpBeforeClass() throws IOException {
		StockDataReaderModified stock = new StockDataReaderModified("IBM", 20);
		converter = new DataConverter(stock.fetchData());
		times = converter.getStockTimes().length;
		values = converter.getStockValues().length;

	}
	

	@Test
	void test() {
		
		assertEquals(times, values); 
	}

}
