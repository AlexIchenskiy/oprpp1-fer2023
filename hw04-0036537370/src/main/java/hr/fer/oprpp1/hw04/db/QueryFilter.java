package hr.fer.oprpp1.hw04.db;

import java.util.List;

/**
 * Class representing a filter for queries based on the list of conditional expressions.
 */
public class QueryFilter implements IFilter {

    /**
     * List of conditional expressions to be checked.
     */
    private final List<ConditionalExpression> expressions;

    /**
     * Constructs a query filter from a given conditional expressions list.
     * @param expressions Conditional expression list
     */
    public QueryFilter(List<ConditionalExpression> expressions) {
        if (expressions == null) {
            throw new NullPointerException("Expression list cant be null!");
        }

        this.expressions = expressions;
    }

    /**
     * Accepts student records by the list of conditional expressions given.
     * @param record Record to be checked
     * @return Boolean representing whether the provided student record is accepted
     */
    @Override
    public boolean accepts(StudentRecord record) {
        for (ConditionalExpression expression : expressions) {
            if (!expression.getComparisonOperator()
                    .satisfied(expression.getFieldValueGetter().get(record), expression.getStringLiteral())) {
                return false;
            }
        }

        return true;
    }

}
