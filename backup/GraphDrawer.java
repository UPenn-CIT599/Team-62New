import javax.swing.JFrame;

public class GraphDrawer {
    GraphConstructor gc = new GraphConstructor();

    /**
     * GraphDrawer is the constructor that takes in an array of times and an array
     * of stock prices. It then graphs them.
     * 
     * @param stockTimes
     * @param stockValues
     */

    public GraphDrawer(String[] stockTimes, double[] stockValues) {
	gc.setStockTimes(stockTimes);
	gc.setStockValues(stockValues);
    }

    /***
     * drawGraph is the main function that draws a graph based on the inputs listed
     * in the constructor.
     * 
     */

    public void drawGraph() {
	JFrame jf = new JFrame();
	jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	jf.add(gc);
	jf.setTitle("Tutorial");
	jf.setSize(gc.getWindowWidth(), gc.getWindowHeight());
	jf.setVisible(true);
    }

    public static void main(String[] args) {
	String[] stockTimes = new String[] { "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00",
		"13:30", "14:00", "14:30", "15:00", "15:30", "16:00" };
	double[] stockValues = new double[] { 230, 235, 234, 238, 250, 260, 275, 260, 243, 235, 220, 215, 210, 205 };

	GraphDrawer gd = new GraphDrawer(stockTimes, stockValues);
	gd.drawGraph();
    }
}
