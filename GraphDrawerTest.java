import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import org.junit.jupiter.api.Test;

class GraphDrawerTest {
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
	assertTrue(gc.getStockTimes().length <= 365);
	assertTrue(gc.getStockValues().length <= 365);
    }

    // Testing that Each Array Does not Contain Null Values
    @Test
    public void NonNullValue() {
	assertNotNull(gc.getStockValues());
    }

    // Testing that the Prices Are Never Negative Values
    @Test
    public void notNegativeValue() {
	for (int i = 0; i < gc.getStockValues().length; i++) {
	    assertTrue(gc.getStockValues()[i] > 0);
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

	assertTrue(gc.getMax(comparisonArray) == doubleRandomArray[doubleRandomArray.length - 1]);
    }

    // Min Test
    @Test
    public void minMethodTest() {
	double[] doubleRandomArray = createDoubleRandomArray();
	double[] comparisonArray = createComparisonArray();

	assertTrue(gc.getMin(comparisonArray) == doubleRandomArray[0]);
    }

    // Graph Interval Test (Testing that Graph Interval is Positive
    @Test
    public void notNegativeGraphInterval() {
	assertTrue(gc.getGraphInterval() > 0);
    }

    // Check Width is Greater than Height
    @Test
    public void dimensionsCheck() {
	assertTrue(gc.getWidth() >= gc.getHeight());
    }

    // Checking that Dimensions are Positive
    @Test
    public void dimensionPositive() {
	assertTrue(gc.getWidth() >= 0);
	assertTrue(gc.getHeight() >= 0);
    }

}
