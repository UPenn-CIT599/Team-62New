import java.io.*;
import java.util.*;


public class StockGrapher {
	
	public static void main(String[] args) throws IOException{
		StockDataReader stock = new StockDataReader("IBM", 5);
		HashMap<Integer, StockData> stockData = stock.fetchData();
		
		DataConverter converter = new DataConverter(stockData);
		String[] times = converter.getStockTimes();
		double[] values = converter.getStockValues();
		
		GraphDrawer graph = new GraphDrawer(times, values);
		graph.drawGraph();
	}

}
