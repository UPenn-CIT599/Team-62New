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
	static int map;
	
	@BeforeClass
	public static void setUpBeforeClass() throws IOException {
		StockDataReaderModified stock = new StockDataReaderModified("IBM", 20);
		testMap = stock.fetchData();
		map = testMap.size();
		converter = new DataConverter(testMap);
		times = converter.getStockTimes().length;
		values = converter.getStockValues().length;

	}
	
	//confirm that the converted arrays are the same size
	@Test
	void TimesValuesSizeTest() {
		
		assertEquals(times, values); 
	}
	
	//confirm that the converted time data is the same size as the hashMap
	@Test
	void convertedTimesSizeTest() {
		assertEquals(map, times);
	}
	
	//confirm that the converted price data is the same size as the hashMap
	@Test
	void convertedValuesSizeTest() {
		assertEquals(map, values);
	}

}
