package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleHashtableTest {

    private SimpleHashtable<String, String> sh;
    private SimpleHashtable<String, Integer> examMarks;

    @BeforeEach
    public void beforeEach() {
        sh = new SimpleHashtable<>();
        examMarks = new SimpleHashtable<>(2);
        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Kristina", 5);
        examMarks.put("Ivana", 5);
    }

    /* SUCCESSFUL TESTS */

    @Test
    public void testPutToEmpty() {
        assertNull(sh.put("hello", "world"));

        assertEquals("world", sh.get("hello"));
    }

    @Test
    public void testPutSameMultiple() {
        assertNull(sh.put("hello", "world"));
        assertEquals("world", sh.put("hello", "again"));

        assertEquals("again", sh.get("hello"));
    }

    @Test
    public void testPutNullValue() {
        assertNull(sh.put("hello", null));

        assertNull(sh.get("hello"));
    }

    @Test
    public void testGetFromEmpty() {
        assertNull(sh.get("hello"));
    }

    @Test
    public void testGetNonexistent() {
        assertNull(examMarks.get("hello"));
    }

    @Test
    public void testGetNull() {
        assertNull(examMarks.get(null));
    }

    @Test
    public void testRemoveNonexistent() {
        assertNull(examMarks.remove("hello"));
    }

    @Test
    public void testRemoveNull() {
        assertNull(examMarks.remove(null));
    }

    @Test
    public void testRemove() {
        sh.put("hello", "world");
        assertEquals("world", sh.remove("hello"));
        assertTrue(sh.isEmpty());
    }

    @Test
    public void testContainsKeyNull() {
        assertFalse(examMarks.containsKey(null));
    }

    @Test
    public void testContainsKeyNonexistent() {
        assertFalse(examMarks.containsKey("hello"));
    }

    @Test
    public void testContainsKey() {
        assertTrue(examMarks.containsKey("Ante"));
    }

    @Test
    public void testContainsValue() {
        assertTrue(examMarks.containsValue(5));
    }

    @Test
    public void testContainsValueNonexistent() {
        assertFalse(examMarks.containsValue(7));
    }

    @Test
    public void testContainsValueNonexistentNull() {
        assertFalse(examMarks.containsValue(null));
    }

    @Test
    public void testContainsValueNull() {
        sh.put("hello", null);
        assertTrue(sh.containsValue(null));
    }

    @Test
    public void testToStringEmpty() {
        assertEquals("[]", sh.toString());
    }

    @Test
    public void testToStringOneValue() {
        sh.put("hello", "world");
        assertEquals("[hello=world]", sh.toString());
    }

    @Test
    public void testToStringMultipleValues() {
        sh.put("hello", "world");
        sh.put("bye", "world");
        assertEquals("[hello=world, bye=world]", sh.toString());
    }

    @Test
    public void testClearEmpty() {
        sh.clear();

        assertTrue(sh.isEmpty());
    }

    @Test
    public void testClear() {
        examMarks.clear();

        assertTrue(examMarks.isEmpty());
    }

    @Test
    public void testIterator() {
        Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();

        System.out.println(examMarks);

        assertEquals("Ante", iter.next().getKey());
        assertTrue(iter.hasNext());
        assertTrue(iter.hasNext());
        assertEquals("Ivana", iter.next().getKey());
        assertTrue(iter.hasNext());
        assertEquals("Jasna", iter.next().getKey());
        assertTrue(iter.hasNext());
        assertEquals("Kristina", iter.next().getKey());
        assertFalse(iter.hasNext());
    }

    @Test
    public void testIteratorRemove() {
        Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();

        while (iter.hasNext()) {
            iter.next();
            iter.remove();
        }

        assertTrue(examMarks.isEmpty());
    }

    @Test
    public void testIteratorPutUpdate() {
        Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();

        assertEquals("Ante", iter.next().getKey());

        examMarks.put("Ivana", 3);

        SimpleHashtable.TableEntry<String, Integer> te = iter.next();
        assertEquals("Ivana", te.getKey());
        assertEquals(3, te.getValue());
    }

    /* TESTS FOR EXCEPTIONS */

    @Test
    public void testTooSmallCapacity() {
        assertThrows(IllegalArgumentException.class, () -> new SimpleHashtable<String, String>(-1));
    }

    @Test
    public void testPutNull() {
        assertThrows(NullPointerException.class, () -> sh.put(null, "world"));
    }

    @Test
    public void testPutWhileIterator() {
        Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();

        examMarks.put("Anton", 5);

        assertThrows(ConcurrentModificationException.class, iter::next);
    }

    @Test
    public void testRemoveWhileIterator() {
        Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();

        examMarks.remove("Ivana");

        assertThrows(ConcurrentModificationException.class, iter::next);
    }

    @Test
    public void testNoNext() {
        Iterator<SimpleHashtable.TableEntry<String, String>> iter = sh.iterator();

        assertThrows(NoSuchElementException.class, iter::next);
    }

    @Test
    public void testRemoveBeforeNext() {
        Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();

        assertThrows(IllegalStateException.class, iter::remove);
    }

    @Test
    public void testRemoveSameMultiple() {
        Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();

        iter.next();
        iter.remove();
        assertThrows(IllegalStateException.class, iter::remove);
    }

}
