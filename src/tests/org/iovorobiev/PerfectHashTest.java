package tests.org.iovorobiev;

import javafx.util.Pair;
import junit.framework.TestCase;
import org.iovorobiev.hashing.PerfectHashMap;

public class PerfectHashTest extends TestCase {

    private static final int ITEMS = 150;
    private Pair<Short, String>[] input;

    @SuppressWarnings("unchecked")
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        input = new Pair[ITEMS];
        for (int i = 0; i < input.length; i++) {
            short key = (short) (Math.random() * (Short.MAX_VALUE * 2 - 12) - Short.MAX_VALUE);
            input[i] = new Pair<>(key, Short.toString(key));
        }
    }

    public void testInsert() {
        PerfectHashMap perfectHashMap = new PerfectHashMap(input);
        int randomIndex = (int) (Math.random() * (ITEMS - 1));
        assertEquals(input[randomIndex].getValue(), perfectHashMap.get(input[randomIndex].getKey()));
        assertEquals(null, perfectHashMap.get((short) (Short.MAX_VALUE - 2)));
        //TODO: add check for right structure of perfect hash
    }
}
