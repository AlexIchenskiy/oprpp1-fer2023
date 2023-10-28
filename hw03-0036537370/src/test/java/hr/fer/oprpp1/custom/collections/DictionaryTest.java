package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DictionaryTest {

    private Dictionary<String, String> dictionary;

    @BeforeEach
    public void beforeEach() {
        dictionary = new Dictionary<>();
    }

    @Test
    public void testPutToEmpty() {
        dictionary.put("key", "value");

        assertEquals("value", dictionary.get("key"));
    }

    @Test
    public void testPutMultiple() {
        dictionary.put("key", "value");
        dictionary.put("hello", "world");

        assertEquals("value", dictionary.get("key"));
        assertEquals("world", dictionary.get("hello"));
    }

    @Test
    public void testPutMultipleToSameKey() {
        dictionary.put("key", "value");
        dictionary.put("key", "world");

        assertEquals("world", dictionary.get("key"));
    }

    @Test
    public void testRemoveOne() {
        dictionary.put("key", "value");
        dictionary.remove("key");

        assertNull(dictionary.get("key"));
    }

    @Test
    public void testRemoveMultiple() {
        dictionary.put("key", "value");
        dictionary.put("hello", "world");
        dictionary.remove("key");
        dictionary.remove("hello");

        assertNull(dictionary.get("key"));
        assertNull(dictionary.get("hello"));
    }

    @Test
    public void testRemoveSame() {
        dictionary.put("key", "value");
        dictionary.remove("key");

        assertNull(dictionary.get("key"));
        assertNull(dictionary.remove("key"));
    }

    @Test
    public void testRemoveNonExisting() {
        assertNull(dictionary.remove("Nothing to see here"));
    }

}
