package hr.fer.oprpp1.hw04.db;

/**
 * List of all comparison operators that can be used in the query.
 */
public class ComparisonOperators {

    /**
     * A character representing a wildcard.
     */
    private static final char WILDCARD_CHAR = '*';

    /**
     * Satisfied if value1 is lexicographically lower than value2.
     */
    public static final IComparisonOperator LESS = ((value1, value2) -> value1.compareTo(value2) < 0);

    /**
     * Satisfied if value1 is lexicographically lower or equal than value2.
     */
    public static final IComparisonOperator LESS_OR_EQUALS = ((value1, value2) -> value1.compareTo(value2) <= 0);

    /**
     * Satisfied if value1 is lexicographically greater than value2.
     */
    public static final IComparisonOperator GREATER = ((value1, value2) -> value1.compareTo(value2) > 0);

    /**
     * Satisfied if value1 is lexicographically greater or equal than value2.
     */
    public static final IComparisonOperator GREATER_OR_EQUALS = ((value1, value2) -> value1.compareTo(value2) >= 0);

    /**
     * Satisfied if value1 is lexicographically equal to value2.
     */
    public static final IComparisonOperator EQUALS = (String::equals);

    /**
     * Satisfied if value1 is not lexicographically equal to value2.
     */
    public static final IComparisonOperator NOT_EQUALS = ((value1, value2) -> !value1.equals(value2));

    /**
     * Satisfied if value1 is like pattern value2.
     */
    public static final IComparisonOperator LIKE = ((value1, value2) -> {

        if (value1 == null) {
            throw new NullPointerException("Value cant be null!");
        }

        if (value2 == null) {
            throw new NullPointerException("Pattern cant be null!");
        }

        int wildcardCount = value2.length() - value2.replace(WILDCARD_CHAR + "", "").length();

        if (wildcardCount > 1) {
            throw new IllegalArgumentException("Only one wildcard can be used in the LIKE expression!");
        }

        if (value1.equals(value2)) return true;

        if (value2.equals(String.valueOf(WILDCARD_CHAR))) return true;

        if (value2.charAt(0) == WILDCARD_CHAR
                && value1.endsWith(value2.replace(String.valueOf(WILDCARD_CHAR), ""))) return true;

        if (value2.charAt(value2.length() - 1) == WILDCARD_CHAR
                && value1.startsWith(value2.replace(String.valueOf(WILDCARD_CHAR), ""))) return true;

        if (wildcardCount != 0) {
            String[] startEnd = value2.split(String.valueOf(WILDCARD_CHAR).replace("*", "\\*"));

            return value1.startsWith(startEnd[0])
                    && value1.replaceFirst(startEnd[0], "").endsWith(startEnd[1]);
        }

        return false;

    });

}
