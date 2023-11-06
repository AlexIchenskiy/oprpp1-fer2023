package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConditionalExpressionTest {

    private StudentRecord studentRecord;
    private ConditionalExpression expr;

    @BeforeEach
    public void beforeEach() {
        studentRecord = new StudentRecord("0000000001", "Prezime", "Ime", 4);
        expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Pre*", ComparisonOperators.LIKE);
    }

    @Test
    public void testConditionTrue() {
        assertTrue(expr.getComparisonOperator().satisfied(
                expr.getFieldValueGetter().get(studentRecord),
                expr.getStringLiteral()
        ));
    }

}
