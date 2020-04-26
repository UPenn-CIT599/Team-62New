import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import org.junit.jupiter.api.Test;

class GraphConstructorTest {
    GraphConstructor gc = new GraphConstructor();
    double[] randomTestArray = new double[10];

    // Testing that Both Inputing Arrays are the Same Length
    @Test
    public void ArrayLengthTest() {
	assertEquals(gc.getStockTimes().length, gc.getStockValues().length,
		"Check that your two inputting arrays are the same length.");
    }

    // Testing that No Entering Array is Longer than 365
    @Test
    public void TimesArrayOverlyLong() {
	assertTrue(gc.getStockTimes().length <= 365, "Stock Times array is too long.");
	assertTrue(gc.getStockValues().length <= 365, "Stock Values array is too long.");
    }

    // Testing that Each Array Does not Contain Null Values
    @Test
    public void NonNullValue() {
	assertNotNull(gc.getStockValues(), "Stock Values array is null.");
    }

    // Testing that the Prices Are Never Negative Values
    @Test
    public void notNegativeValue() {
	for (int i = 0; i < gc.getStockValues().length; i++) {
	    assertTrue(gc.getStockValues()[i] > 0, "Stock values array has a negative price.");
	}
    }

    public double[] createDoubleRandomArray() {
	Random rand = new Random();
	for (int i = 0; i < randomTestArray.length; i++) {
	    randomTestArray[i] = rand.nextDouble();
	}

	return randomTestArray;
    }

    public double[] createComparisonArray() {
	double[] comparisonArray = randomTestArray;
	Arrays.sort(comparisonArray);
	return comparisonArray;
    }

    // Max Test
    @Test
    public void maxMethodTest() {
	double[] doubleRandomArray = createDoubleRandomArray();
	double[] comparisonArray = createComparisonArray();

	assertTrue(gc.getMax(comparisonArray) == doubleRandomArray[doubleRandomArray.length - 1],
		"Get Max didn't work.");
    }

    // Min Test
    @Test
    public void minMethodTest() {
	double[] doubleRandomArray = createDoubleRandomArray();
	double[] comparisonArray = createComparisonArray();

	assertTrue(gc.getMin(comparisonArray) == doubleRandomArray[0], "GetMin didn't work.");
    }

    // Graph Interval Test (Testing that Graph Interval is Positive
    @Test
    public void notNegativeGraphInterval() {
	assertTrue(gc.getGraphInterval() > 0, "Graph interval can't be negative.");
    }

    // Check Width is Greater than Height
    @Test
    public void dimensionsCheck() {
	assertTrue(gc.getWidth() >= gc.getHeight(), "Width is too small. ");
    }

    // Checking that Dimensions are Positive
    @Test
    public void dimensionPositive() {
	assertTrue(gc.getWidth() >= 0, "Width cannot be negative.");
	assertTrue(gc.getHeight() >= 0, "Height cannot be negative.");
    }

}
