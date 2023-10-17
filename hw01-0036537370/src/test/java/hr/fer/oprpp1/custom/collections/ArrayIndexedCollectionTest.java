package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayIndexedCollectionTest {

	private ArrayIndexedCollection aic;
	private ArrayIndexedCollection aicFilled;

	@BeforeEach
	public void beforeEach() {
		aic = new ArrayIndexedCollection();
		aicFilled = new ArrayIndexedCollection();

		for (int i = 0; i < 8; i++) {
			aicFilled.add(i);
		}
	}

	/* SUCCESSFUL TESTS */

	@Test
	public void testConstructorDefaultCreateEmpty() {
		Object[] expected = new Object[0];

		assertArrayEquals(expected, aic.toArray());
	}

	@Test
	public void testSizeConstructorDefaultCreateEmpty() {
		assertEquals(0, aic.size());
	}

	@Test
	public void testConstructorWithCapacityCreateEmpty() {
		aic = new ArrayIndexedCollection(8);

		Object[] expected = new Object[0];

		assertArrayEquals(expected, aic.toArray());
	}

	@Test
	public void testSizeConstructorWithCapacityCreateEmpty() {
		aic = new ArrayIndexedCollection(8);

		assertEquals(0, aic.size());
	}

	@Test
	public void testConstructorWithCollectionCreateEmpty() {
		aic = new ArrayIndexedCollection(new Collection());

		Object[] expected = new Object[0];

		assertArrayEquals(expected, aic.toArray());
	}

	@Test
	public void testSizeConstructorWithCollectionCreateEmpty() {
		aic = new ArrayIndexedCollection(new Collection());
		assertEquals(0, aic.size());
	}

	@Test
	public void testConstructorWithCollectionCreateFilled() {
		aic = new ArrayIndexedCollection(aicFilled);

		assertArrayEquals(aicFilled.toArray(), aic.toArray());
	}

	@Test
	public void testSizeConstructorWithCollectionCreateFilled() {
		aic = new ArrayIndexedCollection(aicFilled);

		assertEquals(aicFilled.size(), aic.size());
	}

	@Test
	public void testConstructorWithCollectionAndCapacityCreateEmpty() {
		aic = new ArrayIndexedCollection(new Collection(), 8);

		Object[] expected = new Object[0];

		assertArrayEquals(expected, aic.toArray());
	}

	@Test
	public void testSizeConstructorWithCollectionAndCapacityCreateEmpty() {
		aic = new ArrayIndexedCollection(new Collection(), 8);

		assertEquals(0, aic.size());
	}

	@Test
	public void testConstructorWithCollectionAndCapacityCreateFilled() {
		aic = new ArrayIndexedCollection(aicFilled, 1);

		assertArrayEquals(aicFilled.toArray(), aic.toArray());
	}

	@Test
	public void testSizeConstructorWithCollectionAndCapacityCreateFilled() {
		aic = new ArrayIndexedCollection(aicFilled, 1);

		assertEquals(aicFilled.size(), aic.size());
	}

	@Test
	public void testAddToEmpty() {
		aic.add(5);
		aic.add(2);

		Object[] expected = new Object[2];
		expected[0] = 5;
		expected[1] = 2;

		assertArrayEquals(expected, aic.toArray());
	}

	@Test
	public void testAddToFilled() {
		aicFilled.add(8);

		Object[] expected = {0, 1, 2, 3, 4, 5, 6, 7, 8};

		assertArrayEquals(expected, aicFilled.toArray());
	}

	@Test
	public void testGetFirst() {
		assertEquals(aicFilled.toArray()[0], aicFilled.get(0));
	}

	@Test
	public void testGetMiddle() {
		assertEquals(aicFilled.toArray()[3], aicFilled.get(3));
	}

	@Test
	public void testGetLast() {
		assertEquals(aicFilled.toArray()[aicFilled.size() - 1], aicFilled.get(aicFilled.size() - 1));
	}

	@Test
	public void testClearFilled() {
		aicFilled.clear();

		Object[] expected = {};

		assertArrayEquals(expected, aicFilled.toArray());
	}

	@Test
	public void testClearEmpty() {
		aic.clear();

		Object[] expected = {};

		assertArrayEquals(expected, aic.toArray());
	}

	@Test
	public void testInsertIntoEmpty() {
		aic.insert(5, 0);

		Object[] expected = { 5 };

		assertArrayEquals(expected, aic.toArray());
	}

	@Test
	public void testInsertIntoFilledStart() {
		aicFilled.insert(0, 0);

		Object[] expected = {0, 0, 1, 2, 3, 4, 5, 6, 7};

		assertArrayEquals(expected, aicFilled.toArray());
	}

	@Test
	public void testInsertIntoFilledMiddle() {
		aicFilled.insert(0, 3);

		Object[] expected = {0, 1, 2, 0, 3, 4, 5, 6, 7};

		assertArrayEquals(expected, aicFilled.toArray());
	}

	@Test
	public void testInsertIntoFilledEnd() {
		aicFilled.insert(0, aicFilled.size());

		Object[] expected = {0, 1, 2, 3, 4, 5, 6, 7, 0};

		assertArrayEquals(expected, aicFilled.toArray());
	}

	@Test
	public void testIndexOfFirst() {
		assertEquals(0, aicFilled.indexOf(0));
	}

	@Test
	public void testIndexOfMiddle() {
		assertEquals(3, aicFilled.indexOf(3));
	}

	@Test
	public void testIndexOfLast() {
		assertEquals(7, aicFilled.indexOf(7));
	}

	@Test
	public void testRemoveFirst() {
		aicFilled.remove(0);

		Object[] expected = {1, 2, 3, 4, 5, 6, 7};

		assertArrayEquals(expected, aicFilled.toArray());
	}

	@Test
	public void testRemoveMiddle() {
		aicFilled.remove(3);

		Object[] expected = {0, 1, 2, 4, 5, 6, 7};

		assertArrayEquals(expected, aicFilled.toArray());
	}

	@Test
	public void testRemoveLast() {
		aicFilled.remove(7);

		Object[] expected = {0, 1, 2, 3, 4, 5, 6};

		assertArrayEquals(expected, aicFilled.toArray());
	}

	@Test
	public void testContainsFirst() {
		assertTrue(aicFilled.contains(0));
	}

	@Test
	public void testContainsMiddle() {
		assertTrue(aicFilled.contains(3));
	}

	@Test
	public void testContainsLast() {
		assertTrue(aicFilled.contains(7));
	}

	@Test
	public void testEmptyIsEmpty() {
		assertTrue(aic.isEmpty());
	}

	@Test
	public void testFilledIsNotEmpty() {
		assertFalse(aicFilled.isEmpty());
	}

	/* TESTS FOR EXCEPTIONS */

	@Test
	public void testTooSmallCapacity() {
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(0));
	}

	@Test
	public void testNullCollection() {
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null));
	}

	@Test
	public void testTooSmallCollection() {
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(new Collection(), 0));
	}

	@Test
	public void testAddNullValue() {
		ArrayIndexedCollection aic = new ArrayIndexedCollection();
		assertThrows(NullPointerException.class, () -> aic.add(null));
	}

	@Test
	public void testGetOutOfBound() {
		ArrayIndexedCollection aic = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> aic.get(-1));
	}

	@Test
	public void testInsertOutOfBound() {
		ArrayIndexedCollection aic = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> aic.insert(5, -1));
	}

	@Test
	public void testRemoveOutOfBound() {
		ArrayIndexedCollection aic = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> aic.remove(-1));
	}

	@Test
	public void testIndexOfNotFound() {
		ArrayIndexedCollection aic = new ArrayIndexedCollection();
		assertEquals(-1, aic.indexOf(5));
	}

}
