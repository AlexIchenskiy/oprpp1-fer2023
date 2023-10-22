package hr.fer.oprpp1.custom.collections;

import hr.fer.oprpp1.custom.collections.demo.EvenIntegerTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedListIndexedCollectionTest {

    private LinkedListIndexedCollection llic;
    private LinkedListIndexedCollection llicFilled;

    @BeforeEach
    public void beforeEach() {
        llic = new LinkedListIndexedCollection();
        llicFilled = new LinkedListIndexedCollection();

        for (int i = 0; i < 8; i++) {
            llicFilled.add(i);
        }
    }

    /* SUCCESSFUL TESTS */

    @Test
    public void testConstructorDefaultCreateEmpty() {
        Object[] expected = new Object[0];

        assertArrayEquals(expected, llic.toArray());
    }

    @Test
    public void testSizeConstructorDefaultCreateEmpty() {
        assertEquals(0, llic.size());
    }

    @Test
    public void testConstructorWithCollectionCreateEmpty() {
        llic = new LinkedListIndexedCollection(new LinkedListIndexedCollection());

        Object[] expected = new Object[0];

        assertArrayEquals(expected, llic.toArray());
    }

    @Test
    public void testSizeConstructorWithCollectionCreateEmpty() {
        llic = new LinkedListIndexedCollection(new LinkedListIndexedCollection());
        assertEquals(0, llic.size());
    }

    @Test
    public void testConstructorWithCollectionCreateFilled() {
        llic = new LinkedListIndexedCollection(llicFilled);

        assertArrayEquals(llicFilled.toArray(), llic.toArray());
    }

    @Test
    public void testSizeConstructorWithCollectionCreateFilled() {
        llic = new LinkedListIndexedCollection(llicFilled);

        assertEquals(llicFilled.size(), llic.size());
    }

    @Test
    public void testAddToEmpty() {
        llic.add(5);
        llic.add(2);

        Object[] expected = new Object[2];
        expected[0] = 5;
        expected[1] = 2;

        assertArrayEquals(expected, llic.toArray());
    }

    @Test
    public void testAddToFilled() {
        llicFilled.add(8);

        Object[] expected = {0, 1, 2, 3, 4, 5, 6, 7, 8};

        assertArrayEquals(expected, llicFilled.toArray());
    }

    @Test
    public void testGetFirst() {
        assertEquals(llicFilled.toArray()[0], llicFilled.get(0));
    }

    @Test
    public void testGetMiddle() {
        assertEquals(llicFilled.toArray()[3], llicFilled.get(3));
    }

    @Test
    public void testGetLast() {
        assertEquals(llicFilled.toArray()[llicFilled.size() - 1], llicFilled.get(llicFilled.size() - 1));
    }

    @Test
    public void testClearFilled() {
        llicFilled.clear();

        Object[] expected = {};

        assertArrayEquals(expected, llicFilled.toArray());
    }

    @Test
    public void testClearEmpty() {
        llic.clear();

        Object[] expected = {};

        assertArrayEquals(expected, llic.toArray());
    }

    @Test
    public void testInsertIntoEmpty() {
        llic.insert(5, 0);

        Object[] expected = { 5 };

        assertArrayEquals(expected, llic.toArray());
    }

    @Test
    public void testInsertIntoFilledStart() {
        llicFilled.insert(0, 0);

        Object[] expected = {0, 0, 1, 2, 3, 4, 5, 6, 7};

        assertArrayEquals(expected, llicFilled.toArray());
    }

    @Test
    public void testInsertIntoFilledMiddle() {
        llicFilled.insert(0, 3);

        Object[] expected = {0, 1, 2, 0, 3, 4, 5, 6, 7};

        assertArrayEquals(expected, llicFilled.toArray());
    }

    @Test
    public void testInsertIntoFilledEnd() {
        llicFilled.insert(0, llicFilled.size());

        Object[] expected = {0, 1, 2, 3, 4, 5, 6, 7, 0};

        assertArrayEquals(expected, llicFilled.toArray());
    }

    @Test
    public void testIndexOfFirst() {
        assertEquals(0, llicFilled.indexOf(0));
    }

    @Test
    public void testIndexOfMiddle() {
        assertEquals(3, llicFilled.indexOf(3));
    }

    @Test
    public void testIndexOfLast() {
        assertEquals(7, llicFilled.indexOf(7));
    }

    @Test
    public void testRemoveFirst() {
        llicFilled.remove(0);

        Object[] expected = {1, 2, 3, 4, 5, 6, 7};

        assertArrayEquals(expected, llicFilled.toArray());
    }

    @Test
    public void testRemoveMiddle() {
        llicFilled.remove(3);

        Object[] expected = {0, 1, 2, 4, 5, 6, 7};

        assertArrayEquals(expected, llicFilled.toArray());
    }

    @Test
    public void testRemoveLast() {
        llicFilled.remove(7);

        Object[] expected = {0, 1, 2, 3, 4, 5, 6};

        assertArrayEquals(expected, llicFilled.toArray());
    }

    @Test
    public void testContainsFirst() {
        assertTrue(llicFilled.contains(0));
    }

    @Test
    public void testContainsMiddle() {
        assertTrue(llicFilled.contains(3));
    }

    @Test
    public void testContainsLast() {
        assertTrue(llicFilled.contains(7));
    }

    @Test
    public void testEmptyIsEmpty() {
        assertTrue(llic.isEmpty());
    }

    @Test
    public void testFilledIsNotEmpty() {
        assertFalse(llicFilled.isEmpty());
    }

    @Test
    public void testAddAllSatisfying() {
        LinkedListIndexedCollection llic2 = new LinkedListIndexedCollection();
        for (int i = 1; i < 5; i++) {
            llic2.add(i);
        }
        llic.addAllSatisfying(llic2, new EvenIntegerTester());

        assertEquals(2, llic.size());
        assertEquals(2, llic.get(0));
        assertEquals(4, llic.get(1));
    }

    /* TESTS FOR EXCEPTIONS */

    @Test
    public void testNullCollection() {
        assertThrows(NullPointerException.class, () -> new LinkedListIndexedCollection(null));
    }

    @Test
    public void testAddNullValue() {
        LinkedListIndexedCollection llic = new LinkedListIndexedCollection();
        assertThrows(NullPointerException.class, () -> llic.add(null));
    }

    @Test
    public void testGetOutOfBound() {
        LinkedListIndexedCollection llic = new LinkedListIndexedCollection();
        assertThrows(IndexOutOfBoundsException.class, () -> llic.get(-1));
    }

    @Test
    public void testInsertOutOfBound() {
        LinkedListIndexedCollection llic = new LinkedListIndexedCollection();
        assertThrows(IndexOutOfBoundsException.class, () -> llic.insert(5, -1));
    }

    @Test
    public void testRemoveOutOfBound() {
        LinkedListIndexedCollection llic = new LinkedListIndexedCollection();
        assertThrows(IndexOutOfBoundsException.class, () -> llic.remove(-1));
    }

    @Test
    public void testIndexOfNotFound() {
        LinkedListIndexedCollection llic = new LinkedListIndexedCollection();
        assertEquals(-1, llic.indexOf(5));
    }

}
