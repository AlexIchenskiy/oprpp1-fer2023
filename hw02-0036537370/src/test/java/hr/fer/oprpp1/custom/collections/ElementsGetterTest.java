package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class ElementsGetterTest {

    ElementsGetter eg;
    ElementsGetter eg2;
    ElementsGetter emptyEg;

    @BeforeEach
    public void beforeEach() {
        Collection col = new ArrayIndexedCollection();
        Collection emptyCol = new ArrayIndexedCollection();

        for (int i = 0; i < 3; i++) {
            col.add(i);
        }

        eg = col.createElementsGetter();
        eg2 = col.createElementsGetter();
        emptyEg = emptyCol.createElementsGetter();
    }

    @Test
    public void testGetFromEmpty() {
        assertFalse(emptyEg.hasNextElement());
        assertThrows(NoSuchElementException.class, () -> emptyEg.getNextElement());
    }

    @Test
    public void testGetFromFilled() {
        assertTrue(eg.hasNextElement());
        assertEquals(0, eg.getNextElement());
    }

    @Test
    public void testGetAll() {
        assertTrue(eg.hasNextElement());
        assertEquals(0, eg.getNextElement());

        assertTrue(eg.hasNextElement());
        assertEquals(1, eg.getNextElement());

        assertTrue(eg.hasNextElement());
        assertEquals(2, eg.getNextElement());

        assertFalse(eg.hasNextElement());
        assertThrows(NoSuchElementException.class, () -> eg.getNextElement());
    }

    @Test
    public void testHasNextWithoutGet() {
        assertTrue(eg.hasNextElement());
        assertTrue(eg.hasNextElement());
        assertTrue(eg.hasNextElement());
    }

    @Test
    public void testIndependentGet() {
        assertTrue(eg.hasNextElement());
        assertEquals(0, eg.getNextElement());

        assertTrue(eg2.hasNextElement());

        assertTrue(eg.hasNextElement());
        assertEquals(1, eg.getNextElement());

        assertTrue(eg2.hasNextElement());

        assertTrue(eg.hasNextElement());
        assertEquals(2, eg.getNextElement());

        assertTrue(eg2.hasNextElement());

        assertFalse(eg.hasNextElement());
        assertThrows(NoSuchElementException.class, () -> eg.getNextElement());

        assertTrue(eg2.hasNextElement());
        assertEquals(0, eg2.getNextElement());

    }

}
