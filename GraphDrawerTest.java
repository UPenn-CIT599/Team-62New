import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GraphDrawerTest {
    GraphConstructor gc = new GraphConstructor();
    
    @Test
    public void ArrayLengthTest() {
	assertEquals(gc.getStockTimes().length, gc.getStockValues().length,  "Check that your two inputting arrays are the same length." );
    }
    
    @Test
    public void up10Test() {
	assertEquals(gc.upNext10(342), 350, "Check that up10 is working correctly");
    }

}
