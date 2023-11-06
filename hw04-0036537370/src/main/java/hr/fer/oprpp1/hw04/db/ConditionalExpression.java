package hr.fer.oprpp1.hw04.db;

/**
 * Class representing a logical conditional expression.
 */
public class ConditionalExpression {

    /**
     * A field value getter.
     */
    private IFieldValueGetter fieldValueGetter;

    /**
     * A string literal pattern.
     */
    private String stringLiteral;

    /**
     * A comparison operator.
     */
    private IComparisonOperator comparisonOperator;

    /**
     * Constructs a conditional expression with a getter, a pattern and a comparison operator.
     * @param fieldValueGetter Field value getter
     * @param stringLiteral String literal pattern
     * @param comparisonOperator Comparison operator
     */
    public ConditionalExpression(IFieldValueGetter fieldValueGetter, String stringLiteral,
                                 IComparisonOperator comparisonOperator) {
        this.fieldValueGetter = fieldValueGetter;
        this.stringLiteral = stringLiteral;
        this.comparisonOperator = comparisonOperator;
    }

    /**
     * A getter for a field value getter.
     * @return Field value getter
     */
    public IFieldValueGetter getFieldValueGetter() {
        return fieldValueGetter;
    }

    /**
     * Pattern getter.
     * @return Pattern
     */
    public String getStringLiteral() {
        return stringLiteral;
    }

    /**
     * Comparison operator getter.
     * @return Comparison operator
     */
    public IComparisonOperator getComparisonOperator() {
        return comparisonOperator;
    }

}
