import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GraphConstructor extends JPanel {
    int buffer = 20;
    int windowWidth = 800;
    int width = windowWidth - buffer;
    int windowHeight = 500;
    int height = windowHeight - buffer;
    int graphInterval = 10;

    double[] StockValues = new double[] {};
    String[] StockTimes = new String[] {};
    String[] PriceLevels = new String[] {};

    int[] xAxisIntervals = new int[] {};
    int[] yAxisIntervals = new int[] {};
    int[] yAxisPrices = new int[] {};
    String[] yAxisPriceLevels = new String[] {};

    int heightMidline = (int) ((height / 7) * 6);

    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	g.setFont(new Font("Courier", Font.PLAIN, 13));

	this.xAxisIntervals = setXAxis(StockTimes);
	this.yAxisIntervals = setYAxis(StockValues);
	this.yAxisPrices = new int[xAxisIntervals.length];
	this.yAxisPriceLevels = setYAxisPriceLevels(StockValues);

	// This Method draws both Y and X Axis.
	drawBasicGraph(g);
	drawScale(g);
	drawPoints(g);
	drawLines(g);
	drawPositiveNegativeLine(g);
    }

    /**
     * drawBasicGraph: This method draws the X and Y Axis. It also draws a label for the X-Axis. 
     * @param g
     */

    public void drawBasicGraph(Graphics g) {
	// Draw X Label
	g.drawString("Time", width / 2, heightMidline + 40);

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

    public void drawScale(Graphics g) {
	// Draw X Labels and TickMarks
	for (int i = 0; i < xAxisIntervals.length; i++) {
	    g.drawString(StockTimes[i], xAxisIntervals[i] - 15, heightMidline + 20);
	    g.drawLine(xAxisIntervals[i], heightMidline, xAxisIntervals[i], heightMidline - 10);
	}

	// Draw Y Labels and TickMarks
	for (int i = 0; i < yAxisIntervals.length; i++) {
	    if (yAxisIntervals.length > 1000 & i % 100 == 0) {
		g.drawString(yAxisPriceLevels[i], xAxisIntervals[0] - 50, yAxisIntervals[i] + 5);
		g.drawLine(xAxisIntervals[0], yAxisIntervals[i], xAxisIntervals[0] + 10, yAxisIntervals[i]);
	    } else if ((yAxisIntervals.length > 100 & yAxisIntervals.length < 1000) & i % 10 == 0) {
		g.drawString(yAxisPriceLevels[i], xAxisIntervals[0] - 45, yAxisIntervals[i] + 5);
		g.drawLine(xAxisIntervals[0], yAxisIntervals[i], xAxisIntervals[0] + 10, yAxisIntervals[i]);
	    } else if (yAxisIntervals.length < 100) {
		g.drawString(yAxisPriceLevels[i], xAxisIntervals[0] - 35, yAxisIntervals[i] + 5);
		g.drawLine(xAxisIntervals[0], yAxisIntervals[i], xAxisIntervals[0] + 10, yAxisIntervals[i]);
	    }
	}
    }

    /**
     * drawPoints: This method draws each price point. It sets the color of the
     * points based on whether the trend is positive or negative.
     * 
     * @param g
     */

    public void drawPoints(Graphics g) {
	// Draw Points
	if (negMarket() == true) {
	    g.setColor(Color.RED.darker().darker());
	} else {
	    g.setColor(Color.GREEN.darker().darker());
	}
	for (int i = 0; i < xAxisIntervals.length; i++) {
	    int tempYValue = valueConverter(StockValues[i]);
	    yAxisPrices[i] = tempYValue;
	    g.fillOval(xAxisIntervals[i] - 3, tempYValue - 2, 7, 7);
	    g.drawString(Double.toString(StockValues[i]), xAxisIntervals[i], tempYValue + buffer);
	}
    }

    /**
     * drawLines: This method draws lines that connect all the various (x,y) price
     * points. It colors the lines red if the price is lower than the start and
     * green if the price is higher than the start.
     * 
     * @param g
     */

    public void drawLines(Graphics g) {
	// Draw Lines Setting Color Based on if it's positive or negative.
	if (negMarket() == true) {
	    g.setColor(Color.RED.darker().darker());
	} else {
	    g.setColor(Color.GREEN.darker().darker());
	}
	for (int i = 0; i < xAxisIntervals.length - 1; i++) {
	    g.drawLine(xAxisIntervals[i], yAxisPrices[i], xAxisIntervals[i + 1], yAxisPrices[i + 1]);
	}
    }

    /**
     * drawPositiveNegativeLine: This method draws a flat horizontal, dashed line
     * that represents the price-level at the beginning of the plotted series.
     * 
     * @param g
     */

    public void drawPositiveNegativeLine(Graphics g) {
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

    public boolean negMarket() {
	double finalValue = StockValues[StockValues.length - 1];
	double firstValue = StockValues[0];

	if (finalValue < firstValue) {
	    return true;
	} else
	    return false;
    }

    /**
     * getMax: This method takes in a double[] and returns the largest double in
     * that array.
     * 
     * @param stockPrice
     * @return max
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

    public int[] setXAxis(String[] times) {
	int[] numberedXAxis = new int[StockTimes.length];
	int equalInterval = width / StockTimes.length;
	int tempScaleLength = equalInterval;

	for (int i = 0; i < numberedXAxis.length; i++) {
	    numberedXAxis[i] = tempScaleLength;
	    tempScaleLength = tempScaleLength + equalInterval;
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

    public int[] setYAxis(double[] stockValues) {
	double range = getMax(stockValues);
	int extendedRange = upNext10(range);
	int numberOfSegments = extendedRange / graphInterval;

	int[] numberedYAxis = new int[numberOfSegments + 1];

	double tempLength = 0;

	for (int i = 0; i < numberedYAxis.length; i++) {
	    numberedYAxis[i] = valueConverter(tempLength);
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

    public String[] setYAxisPriceLevels(double[] stockPrice) {
	double range = getMax(stockPrice);
	int extendedRange = upNext10(range);
	int priceInterval = extendedRange / graphInterval;
	String[] YAxisPriceLevels = new String[priceInterval + 1];

	int tempPrice = 0;

	for (int i = 0; i < YAxisPriceLevels.length; i++) {
	    int tempPriceInt = tempPrice;
	    YAxisPriceLevels[i] = "$" + Integer.toString(tempPriceInt);
	    tempPrice = tempPrice + graphInterval;
	}

	return YAxisPriceLevels;
    }

    /**
     * valueConverter: This method converts the price levels of a stock to a pixel
     * value for graphing. It uses preportions to calculate this. For example, if a
     * stock's price was at $400, this function would convert that to a pixel value
     * for graphing. It is used in plotting the Y values of points and in
     * constructing the Y-Axis scale.
     * 
     * @param double originalPrice
     * @return int resultInt.
     */

    public int valueConverter(double originalPrice) {
	double range = getMax(StockValues);
	double extendedRange = upNext10(range);

	double yAxisLength = heightMidline - buffer;

	double result = (originalPrice * yAxisLength) / extendedRange;

	int resultInt = (int) (heightMidline - result);

	return resultInt;
    }

    /**
     * upNext10 This method takes in a double and returns the ten's place above it.
     * For example, if you give it 14, the function will return 20. This is used to
     * help construct the Y-Axis Scale.
     * 
     * @param double range
     * @return double rangeLabel
     */

    public int upNext10(double range) {
	int rangeLabel = 0;

	int upwardInt = (int) Math.ceil(range);

	while (upwardInt % graphInterval != 0) {
	    upwardInt = upwardInt + 1;
	}

	rangeLabel = upwardInt;
	return rangeLabel;
    }

    public int getWindowWidth() {
	return windowWidth;
    }

    public int getWindowHeight() {
	return windowHeight;
    }

    public void setStockValues(double[] stockValue) {
	this.StockValues = stockValue;
    }

    public void setStockTimes(String[] stockTimes) {
	this.StockTimes = stockTimes;
    }

}
