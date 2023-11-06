package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ComparisonOperatorsTest {

    private IComparisonOperator oper;

    @Test
    public void testLessTrue() {
        oper = ComparisonOperators.LESS;

        assertTrue(oper.satisfied("a", "b"));
    }

    @Test
    public void testLessFalse() {
        oper = ComparisonOperators.LESS;

        assertFalse(oper.satisfied("B", "A"));
    }

    @Test
    public void testLessFalseEquals() {
        oper = ComparisonOperators.LESS;

        assertFalse(oper.satisfied("B", "B"));
    }

    @Test
    public void testLessOrEqualsLessTrue() {
        oper = ComparisonOperators.LESS_OR_EQUALS;

        assertTrue(oper.satisfied("A", "B"));
    }

    @Test
    public void testLessOrEqualsEqualsTrue() {
        oper = ComparisonOperators.LESS_OR_EQUALS;

        assertTrue(oper.satisfied("A", "A"));
    }

    @Test
    public void testLessOrEqualsLessFalse() {
        oper = ComparisonOperators.LESS_OR_EQUALS;

        assertFalse(oper.satisfied("B", "A"));
    }

    @Test
    public void testGreaterTrue() {
        oper = ComparisonOperators.GREATER;

        assertTrue(oper.satisfied("b", "a"));
    }

    @Test
    public void testGreaterFalse() {
        oper = ComparisonOperators.GREATER;

        assertFalse(oper.satisfied("A", "B"));
    }

    @Test
    public void testGreaterFalseEquals() {
        oper = ComparisonOperators.GREATER;

        assertFalse(oper.satisfied("A", "A"));
    }

    @Test
    public void testGreaterOrEqualsGreaterTrue() {
        oper = ComparisonOperators.GREATER_OR_EQUALS;

        assertTrue(oper.satisfied("B", "A"));
    }

    @Test
    public void testGreaterOrEqualsEqualsTrue() {
        oper = ComparisonOperators.GREATER_OR_EQUALS;

        assertTrue(oper.satisfied("A", "A"));
    }

    @Test
    public void testGreaterOrEqualsGreaterFalse() {
        oper = ComparisonOperators.GREATER_OR_EQUALS;

        assertFalse(oper.satisfied("A", "B"));
    }

    @Test
    public void testEqualsTrue() {
        oper = ComparisonOperators.EQUALS;

        assertTrue(oper.satisfied("a", "a"));
    }

    @Test
    public void testEqualsFalse() {
        oper = ComparisonOperators.EQUALS;

        assertFalse(oper.satisfied("a", "not a"));
    }

    @Test
    public void testNotEqualsTrue() {
        oper = ComparisonOperators.NOT_EQUALS;

        assertTrue(oper.satisfied("a", "not a"));
    }

    @Test
    public void testNotEqualsFalse() {
        oper = ComparisonOperators.NOT_EQUALS;

        assertFalse(oper.satisfied("a", "a"));
    }

    @Test
    public void testLikeEqualsTrue() {
        oper = ComparisonOperators.LIKE;

        assertTrue(oper.satisfied("a", "a"));
    }

    @Test
    public void testLikeAnyTrue() {
        oper = ComparisonOperators.LIKE;

        assertTrue(oper.satisfied("Any str1ng. Literally 4ny.", "*"));
    }

    @Test
    public void testLikeStartWildcardTrue() {
        oper = ComparisonOperators.LIKE;

        assertTrue(oper.satisfied("Any str1ng. Literally 4ny.not so fast", "*not so fast"));
    }

    @Test
    public void testLikeStartWildcardFalse() {
        oper = ComparisonOperators.LIKE;

        assertFalse(oper.satisfied("Any str1ng. Literally 4ny.not so fast yet again", "*not so fast"));
    }

    @Test
    public void testLikeEndWildcardTrue() {
        oper = ComparisonOperators.LIKE;

        assertTrue(oper.satisfied("ABCDEF", "ABC*"));
    }

    @Test
    public void testLikeEndWildcardFalse() {
        oper = ComparisonOperators.LIKE;

        assertFalse(oper.satisfied("ABDEF", "ABC*"));
    }

    @Test
    public void testLikeMiddleWildcardTrue() {
        oper = ComparisonOperators.LIKE;

        assertTrue(oper.satisfied("AAAA", "AA*AA"));
    }

    @Test
    public void testLikeMiddleWildcardFalse() {
        oper = ComparisonOperators.LIKE;

        assertFalse(oper.satisfied("AAA", "AA*AA"));
    }

    @Test
    public void testLikeNullValue() {
        oper = ComparisonOperators.LIKE;

        assertThrows(NullPointerException.class, () -> oper.satisfied(null, "a"));
    }

    @Test
    public void testLikeNullPattern() {
        oper = ComparisonOperators.LIKE;

        assertThrows(NullPointerException.class, () -> oper.satisfied("a", null));
    }

    @Test
    public void testLikeTooManyWildcards() {
        oper = ComparisonOperators.LIKE;

        assertThrows(IllegalArgumentException.class, () -> oper.satisfied("a", "*a*"));
    }

}
