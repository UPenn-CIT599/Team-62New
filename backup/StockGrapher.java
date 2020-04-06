import java.io.*;


public class StockGrapher {
	
	public static void main(String[] args) throws IOException{
		StockDataReader stock = new StockDataReader("IBM", 5);
		stock.fetchData();
	}

}
