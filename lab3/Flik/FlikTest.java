import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class FlikTest {
    @Test
    public void testIsSameNumber(){
        assertTrue(Flik.isSameNumber(1024, 1024));
    }
}