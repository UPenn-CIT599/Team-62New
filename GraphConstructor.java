import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GraphConstructor extends JPanel {
    private static final long serialVersionUID = 1L;
    private int buffer = 20;
    private int windowWidth = 800;
    private int width = windowWidth - buffer;
    private int windowHeight = 500;
    private int height = windowHeight - buffer;
    private int graphInterval = 1;
    private int timeType;
    private boolean trendlineVisible = false;

    private double[] StockValues = new double[] {};
    private double[] TrendLine = new double[] {};

    private String[] StockTimes = new String[] {};

    private int[] xAxisIntervals = new int[] {};
    private int[] yAxisIntervals = new int[] {};
    private int[] yAxisPrices = new int[] {};
    private int[] trendLinePrices = new int[] {};

    private String[] yAxisPriceLevels = new String[] {};

    private int heightMidline = (int) ((height / 7) * 6);
    private int xAxisStart = (int) (width / 11);

    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	g.setFont(new Font("Courier", Font.PLAIN, 13));

	this.graphInterval = setGraphInterval();
	this.xAxisIntervals = setXAxis(StockTimes);
	this.yAxisIntervals = setYAxis(StockValues);
	this.yAxisPrices = new int[xAxisIntervals.length];
	this.trendLinePrices = new int[xAxisIntervals.length];
	this.yAxisPriceLevels = setYAxisPriceLevels(StockValues);

	// This Method draws both Y and X Axis.
	drawBasicGraph(g);
	drawScale(g);
	drawPoints(g);
	drawLines(g);
	drawPositiveNegativeLine(g);
    }

    /**
     * drawBasicGraph: This method draws the X and Y Axis. It also draws a label for
     * the X-Axis.
     * 
     * @param g
     */

    private void drawBasicGraph(Graphics g) {
	// Draw Background Rectangle
	g.setColor(Color.LIGHT_GRAY);
	g.fillRect(xAxisIntervals[0], yAxisIntervals[yAxisIntervals.length - 1],
		xAxisIntervals[xAxisIntervals.length - 1] - xAxisIntervals[0],
		heightMidline - yAxisIntervals[yAxisIntervals.length - 1]);

	g.setColor(Color.BLACK);
	// Draw Outer Graph Lines
	g.drawLine(xAxisIntervals[0], yAxisIntervals[yAxisIntervals.length - 1],
		xAxisIntervals[xAxisIntervals.length - 1], yAxisIntervals[yAxisIntervals.length - 1]);

	g.drawLine(xAxisIntervals[xAxisIntervals.length - 1], yAxisIntervals[yAxisIntervals.length - 1],
		xAxisIntervals[xAxisIntervals.length - 1], heightMidline);

	// Draw Y Axis
	g.drawLine(xAxisIntervals[0], yAxisIntervals[yAxisIntervals.length - 1], xAxisIntervals[0], heightMidline);

	// Draw X Axis
	g.drawLine(xAxisIntervals[0], heightMidline, xAxisIntervals[xAxisIntervals.length - 1], heightMidline);

    }

    /**
     * drawScale: This method draws the tick-marks on the X and Y Axis. It sets the
     * Y-Axis tick marks depending on the overall scale of the Y-Axis.
     * 
     * @param g
     */

    private void drawScale(Graphics g) {
	// Draw X Labels and TickMarks
	String timeFrame = StockTimes[0].split(" ")[0] + " to " + StockTimes[StockTimes.length - 1].split(" ")[0];

	// Draw X Axis Labels (Day)
	if (timeType == 1) {
	    g.drawString(StockTimes[0].split(" ")[0], windowWidth / 2, heightMidline + 40);
	}
	// Draw X Axis Labels (Week)
	if (timeType == 5) {
	    g.drawString(timeFrame, (width / 2) - 30, heightMidline + 40);
	}
	// Draw X Axis Labels (Month)
	if (timeType == 20) {
	    g.drawString(timeFrame, (width / 2) - 30, heightMidline + 40);
	}
	// Draw X Axis Labels (Years)
	if (timeType == 260) {
	    g.drawString(timeFrame, (width / 2) - 30, heightMidline + 40);
	}

	for (int i = 0; i < xAxisIntervals.length; i++) {
	    String stockDate = StockTimes[i].split(" ")[0];
	    String[] stockDateRemoveYear = stockDate.split("-");
	    String stockMonth = stockDateRemoveYear[1] + "-" + stockDateRemoveYear[2];

	    if ((timeType == 260) & (i % 50 == 0)) {
		g.drawString(StockTimes[i], xAxisIntervals[i] - 15, heightMidline + 20);
		g.drawLine(xAxisIntervals[i], heightMidline, xAxisIntervals[i], heightMidline - 10);
	    }
	    if ((timeType == 20) & (i % 2 == 0)) {
		g.drawString(stockMonth, xAxisIntervals[i] - 15, heightMidline + 20);
		g.drawLine(xAxisIntervals[i], heightMidline, xAxisIntervals[i], heightMidline - 10);
	    }

	    if (timeType == 5 & i % 7 == 0) {
		g.drawString(stockMonth, xAxisIntervals[i] - 25, heightMidline + 20);
		g.drawLine(xAxisIntervals[i], heightMidline, xAxisIntervals[i], heightMidline - 10);
	    }

	    if ((timeType == 1) & (i % 2 == 0)) {
		String[] stockTimesNoYear = StockTimes[i].split(" ");
		String[] stockTimes = stockTimesNoYear[1].split(":");
		String stockTimeNoDate = stockTimes[0] + ":" + stockTimes[1];
		g.drawString(stockTimeNoDate, xAxisIntervals[i] - 20, heightMidline + 20);
		g.drawLine(xAxisIntervals[i], heightMidline, xAxisIntervals[i], heightMidline - 10);
	    }
	}

	// Draw Y Labels and TickMarks
	for (int i = 0; i < yAxisIntervals.length; i++) {

	    // Printing Y Axis in Segments of 10,000 if there are more than 100,000 Segments
	    if (yAxisIntervals.length > 100000 & i % 20000 == 0) {
		drawLineLabel(yAxisPriceLevels[i], yAxisIntervals[i] + 5, g);
	    }

	    // Printing Y Axis in Segments of 1,000 if there are more than 10,000 Segments
	    else if ((yAxisIntervals.length > 10000 & yAxisIntervals.length <= 100000) & i % 2000 == 0) {
		drawLineLabel(yAxisPriceLevels[i], yAxisIntervals[i] + 5, g);
	    }

	    // Printing Y Axis in Segments of 100 if there are more than 1000 Segments
	    else if ((yAxisIntervals.length > 1000 & yAxisIntervals.length <= 10000) & i % 200 == 0) {
		drawLineLabel(yAxisPriceLevels[i], yAxisIntervals[i] + 5, g);
	    }

	    // Printing Y Axis in Segments of 20 if there are more than 1000 Segments
	    else if ((yAxisIntervals.length > 100 & yAxisIntervals.length <= 1000) & i % 30 == 0) {
		drawLineLabel(yAxisPriceLevels[i], yAxisIntervals[i] + 5, g);
	    }

	    else if ((yAxisIntervals.length > 10 & yAxisIntervals.length <= 100) & i % 2 == 0) {
		drawLineLabel(yAxisPriceLevels[i], yAxisIntervals[i] + 5, g);
	    }

	    else if (yAxisIntervals.length <= 10) {
		drawLineLabel(yAxisPriceLevels[i], yAxisIntervals[i] + 5, g);
	    }
	}
    }

    /**
     * drawLineLabel: This method draws a String and line label for each of the
     * YAxis Tick Marks on the Y-Axis
     * 
     * @param String PriceLevel, int yValue, Graphics g
     * @return nothing
     */

    private void drawLineLabel(String PriceLevel, int yValue, Graphics g) {
	g.drawString(PriceLevel, xAxisIntervals[0] - 50, yValue);
	g.drawLine(xAxisIntervals[0], yValue - 5, xAxisIntervals[0] + 10, yValue - 5);
    }

    /**
     * drawPoints: This method draws each price point. It sets the color of the
     * points based on whether the trend is positive or negative.
     * 
     * @param g
     */

    private void drawPoints(Graphics g) {

	// Set Color of Points
	if (negMarket() == true) {
	    g.setColor(Color.RED.darker().darker());
	} else {
	    g.setColor(Color.GREEN.darker().darker());
	}

	// Draw Points
	for (int i = 0; i < xAxisIntervals.length; i++) {
	    yAxisPrices[i] = valueConverterY(StockValues[i]);

	    g.fillOval(xAxisIntervals[i] - 3, yAxisPrices[i] - 2, 7, 7);

	    if (timeType == 260 & i % 20 == 0) {
		LabelMaker(Double.toString(StockValues[i]), xAxisIntervals[i], yAxisPrices[i] + buffer);
	    }
	    if (timeType == 5 & i % 5 == 0) {
		LabelMaker(Double.toString(StockValues[i]), xAxisIntervals[i], yAxisPrices[i] + buffer);
	    }
	    if (timeType == 20 || timeType == 1) {
		LabelMaker(Double.toString(StockValues[i]), xAxisIntervals[i], yAxisPrices[i] + buffer);
	    }
	}
    }

    /**
     * drawLines: This method draws lines that connect all the various (x,y) price
     * points. It colors the lines red if the price is lower than the start and
     * green if the price is higher than the start.
     * 
     * @param g
     */

    private void drawLines(Graphics g) {
	// Draw Lines Setting Color Based on if it's positive or negative.
	if (negMarket() == true) {
	    g.setColor(Color.RED.darker().darker());
	} else {
	    g.setColor(Color.GREEN.darker().darker());
	}
	for (int i = 0; i < xAxisIntervals.length - 1; i++) {
	    g.drawLine(xAxisIntervals[i], yAxisPrices[i], xAxisIntervals[i + 1], yAxisPrices[i + 1]);
	}

	g.setColor(Color.BLACK);
	for (int i = 0; i < xAxisIntervals.length; i++) {
	    trendLinePrices[i] = valueConverterY(TrendLine[i]);
	}

	if (trendlineVisible == true) {
	    for (int i = 0; i < xAxisIntervals.length - 1; i++) {
		g.drawLine(xAxisIntervals[i], trendLinePrices[i], xAxisIntervals[i + 1], trendLinePrices[i + 1]);
	    }
	}
    }

    /**
     * LabelMaker: This method adds a Jlabel of the point price at the XY values of
     * that point. it moves labels that happen below the X-Axis.
     * 
     * @param String priceLevel, int xValue, int yValue
     */

    private void LabelMaker(String priceLevel, int xValue, int yValue) {
	JLabel label = new JLabel(priceLevel);
	label.setOpaque(true);
	Dimension size = label.getPreferredSize();
	label.setBackground(Color.lightGray);

	if (yValue > heightMidline - 20) {
	    label.setBounds(xValue, yValue - 50, size.width, size.height);
	} else if (xValue >= xAxisIntervals[xAxisIntervals.length - 1]) {
	    label.setBounds(xValue - 40, yValue, size.width, size.height);
	} else {
	    label.setBounds(xValue, yValue, size.width, size.height);
	}

	setLayout(null);
	add(label);
    }

    /**
     * drawPositiveNegativeLine: This method draws a flat horizontal, dashed line
     * that represents the price-level at the beginning of the plotted series.
     * 
     * @param g
     */

    private void drawPositiveNegativeLine(Graphics g) {
	Graphics2D g2d = (Graphics2D) g;

	float[] dashingPattern1 = { 2f, 2f };
	Stroke strokePattern = new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, dashingPattern1,
		2.0f);
	g2d.setStroke(strokePattern);
	g2d.setColor(Color.black);
	g2d.drawLine(xAxisIntervals[0], yAxisPrices[0], xAxisIntervals[xAxisIntervals.length - 1], yAxisPrices[0]);
    }

    /**
     * negMarket: This method returns true if the time range is negative and
     * positive if the time range is positive. It is used to color the line in
     * particular colors.
     * 
     * @return boolean true/false
     */

    private boolean negMarket() {
	double finalValue = StockValues[StockValues.length - 1];
	double firstValue = StockValues[0];

	if (finalValue < firstValue) {
	    return true;
	} else
	    return false;
    }

    /**
     * getMin: This method returns the smallest value out of an Array.
     * 
     * @param double[] stockPrice
     * @return double min
     */

    public double getMin(double[] stockPrice) {
	double min = stockPrice[0];

	for (int i = 0; i < stockPrice.length; i++) {
	    if (stockPrice[i] < min) {
		min = stockPrice[i];
	    }
	}

	return min;
    }

    /**
     * getMax: This method takes in a double[] and returns the largest double in
     * that array.
     * 
     * @param stockPrice
     * @return double max
     */

    public double getMax(double[] stockPrice) {
	double max = 0;

	for (int i = 0; i < stockPrice.length; i++) {
	    if (stockPrice[i] > max) {
		max = stockPrice[i];
	    }
	}

	return max;
    }

    /**
     * setXAxis: This method creates an evenly-spaced X-Axis. It returns an array
     * containing the X-coordinates of each evenly spaced tick-mark.
     * 
     * @param times
     * @return numberedXAxis
     */

    private int[] setXAxis(String[] times) {
	int[] numberedXAxis = new int[StockTimes.length];

	for (int i = 0; i < numberedXAxis.length; i++) {
	    numberedXAxis[i] = valueConverterX(i);
	}

	return numberedXAxis;
    }

    /**
     * setYAxis: This method creates an evenly spaced Y-Axis. It returns an array
     * containing the Y coordinates of each evenly spaced tick-mark.
     * 
     * @param stockValues
     * @return return numberedYAxis
     */

    private int[] setYAxis(double[] stockValues) {
	double range = getScale(StockValues);
	int numberOfSegments = (int) (range / graphInterval);

	int[] numberedYAxis = new int[numberOfSegments + 1];

	double tempLength = downNextLandmark(getMin(StockValues));

	for (int i = 0; i < numberedYAxis.length; i++) {
	    numberedYAxis[i] = valueConverterY(tempLength);
	    tempLength = tempLength + graphInterval;
	}

	return numberedYAxis;
    }

    /**
     * yAxisPricelevels: This method returns a String[] of all the prices for the
     * Y-Axis Scale.
     * 
     * @param stockPrice
     * @return YAxisPricelevels
     */

    private String[] setYAxisPriceLevels(double[] stockPrice) {
	double range = getMax(stockPrice);

	int extendedRange = upNextLandmark(range);

	int priceInterval = extendedRange / graphInterval;

	String[] YAxisPriceLevels = new String[priceInterval + 1];

	int tempPrice = downNextLandmark(getMin(StockValues));

	for (int i = 0; i < YAxisPriceLevels.length; i++) {
	    int tempPriceInt = tempPrice;
	    YAxisPriceLevels[i] = "$" + Integer.toString(tempPriceInt);
	    tempPrice = tempPrice + graphInterval;
	}

	return YAxisPriceLevels;
    }

    /**
     * valueConverterY: This method converts the price levels of a stock to a pixel
     * value for graphing. It uses preportions to calculate this. For example, if a
     * stock's price was at $400, this function would convert that to a pixel value
     * for graphing. It is used in plotting the Y values of points and in
     * constructing the Y-Axis scale.
     * 
     * @param double originalPrice
     * @return int resultInt.
     */

    private int valueConverterY(double originalPrice) {
	double range = getScale(StockValues);

	double yAxisLength = heightMidline - buffer;
	double result = ((originalPrice - downNextLandmark(getMin(StockValues))) * yAxisLength) / range;

	int resultInt = (int) (heightMidline - result);

	return resultInt;
    }

    /**
     * getScale This method takes calculates the range of values to be shown on the
     * Y-Axis. It returns a range with a upward and downward buffer.
     * 
     * @param double range
     * @return double rangeLabel
     */

    private double getScale(double[] stockPrices) {
	double max = getMax(StockValues);
	double extendedMax = upNextLandmark(max);

	double min = getMin(StockValues);
	double extendedMin = downNextLandmark(min);

	double range = extendedMax - extendedMin;

	return range;
    }

    /**
     * valueConverterX: This method converts the times of a stock to a pixel value
     * for graphing. It uses preportions to calculate this. It is used in plotting
     * the X values of points and in constructing the X-Axis scale.
     * 
     * @param int arrayIndex
     * @return int resultInt.
     */

    private int valueConverterX(int arrayIndex) {
	int range = StockTimes.length;
	double xAxisLength = width - (buffer * 4);

	double result = (arrayIndex * xAxisLength) / range;

	int resultInt = (int) (xAxisStart + result);

	return resultInt;
    }

    /**
     * upNextLandmark This method takes in a double and returns the next landmark
     * above that value. It uses the existing GraphInterval to calculate this
     * landmark.
     * 
     * @param double range
     * @return double rangeLabel
     */

    private int upNextLandmark(double range) {
	int rangeLabel = 0;

	int upwardInt = (int) Math.ceil(range);

	while (upwardInt % graphInterval != 0) {
	    upwardInt = upwardInt + 1;
	}

	rangeLabel = upwardInt;
	return rangeLabel;
    }

    /**
     * downNextLandmark This method takes in a double and returns the next landmark
     * above that value. It uses the existing GraphInterval to calculate this
     * landmark.
     * 
     * @param double range
     * @return double rangeLabel
     */

    private int downNextLandmark(double range) {
	int rangeLabel = 0;

	int downwardInt = (int) Math.floor(range);
	while (downwardInt % graphInterval != 0) {
	    downwardInt = downwardInt - 1;
	}

	rangeLabel = downwardInt;
	return rangeLabel;
    }

    /**
     * setGraphInterval This method sets the scale of the YAxis based on the overall
     * range of the data. It controls the spread of the YAxis tickmarks on the
     * graph.
     * 
     * @return temp tempGraphInterval
     */

    private int setGraphInterval() {
	double scale = getScale(StockValues);
	int tempGraphInterval = 10;

	if (scale < 5) {
	    tempGraphInterval = 1;
	    return tempGraphInterval;
	}

	return tempGraphInterval;
    }

    public int getGraphInterval() {
	return graphInterval;
    }

    public String[] getStockTimes() {
	return StockTimes;
    }

    public double[] getStockValues() {
	return StockValues;
    }

    public void setStockValues(double[] stockValue) {
	this.StockValues = stockValue;
    }

    public void setStockTimes(String[] stockTimes) {
	this.StockTimes = stockTimes;
    }

    public void setTrendlineVisible(boolean trendlineVisible) {
	this.trendlineVisible = trendlineVisible;
    }

    public void setTrendLine(double[] trendLine, boolean trendlineVisible) {
	this.TrendLine = trendLine;
    }

    public void setTimeType(int timeType) {
	this.timeType = timeType;
    }

    /**
     * main: This second main method plots the new graph on the JPanel every time
     * the OK button is placed.
     * 
     * @return
     */

    public static void main(String[] stockTimes, double[] stockValues, int timeType, boolean trendlineVisible,
	    double[] trendLine, JPanel panel) {
	GraphConstructor gc = new GraphConstructor();
	gc.setStockValues(stockValues);
	gc.setStockTimes(stockTimes);
	gc.setTimeType(timeType);
	gc.setTrendlineVisible(trendlineVisible);
	gc.setTrendLine(trendLine, trendlineVisible);
	panel.add(gc);
    }

}
