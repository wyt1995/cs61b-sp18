package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        for (int i = 0; i < 10; i++) {
            arb.enqueue(i);
        }
        for (int i = 0; i < 5; i++) {
            assertEquals(i, (int) arb.dequeue());
        }
        for (int i = 0; i < 5; i++) {
            arb.enqueue(10 * i);
        }
        for (int i = 0; i < 5; i++) {
            assertEquals(i + 5, (int) arb.dequeue());
        }
        for (int i = 0; i < 5; i++) {
            assertEquals(10 * i, (int) arb.dequeue());
        }
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
