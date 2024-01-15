package deque;

import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayDequeTest {

    @Test
    public void removeLastTest() {
        ArrayDeque<Integer> a = new ArrayDeque<>();
        ArrayDeque<Integer> b = new ArrayDeque<>();
        for (int i=0; i<1000; i++) {
            a.addFirst(i);
            b.addFirst(i);
            assertEquals(i+1, a.size());
            assertEquals(i+1, b.size());
        }

        for (int i=0; i<500; i++) {
            int c = a.removeFirst();
            int d = b.removeFirst();
            assertEquals(c, d);
            int e = a.removeLast();
            int f = b.removeLast();
            assertEquals(e, f);
        }
        assertTrue(a.isEmpty());
        assertTrue(b.isEmpty());
    }
}
