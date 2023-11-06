package hr.fer.oprpp1.hw04.db;

import hr.fer.oprpp1.hw04.db.lexer.QueryLexer;
import hr.fer.oprpp1.hw04.db.lexer.QueryToken;
import hr.fer.oprpp1.hw04.db.lexer.QueryTokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class representing a query parser
 */
public class QueryParser {

    /**
     * A lexer for this parser.
     */
    private final QueryLexer lexer;

    /**
     * List of conditional expressions parsed.
     */
    private final List<ConditionalExpression> expressions;

    /**
     * Mapper for value getters by names.
     */
    private final Map<String, IFieldValueGetter> valueGetterByName = Map.ofEntries(
            Map.entry("lastname", FieldValueGetters.LAST_NAME),
            Map.entry("firstname", FieldValueGetters.FIRST_NAME),
            Map.entry("jmbag", FieldValueGetters.JMBAG)
    );

    /**
     * Mapper for comparison operators by names.
     */
    private final Map<String, IComparisonOperator> comparisonOperatorByName = Map.ofEntries(
            Map.entry(">", ComparisonOperators.GREATER),
            Map.entry("<", ComparisonOperators.LESS),
            Map.entry(">=", ComparisonOperators.GREATER_OR_EQUALS),
            Map.entry("<=", ComparisonOperators.LESS_OR_EQUALS),
            Map.entry("=", ComparisonOperators.EQUALS),
            Map.entry("!=", ComparisonOperators.NOT_EQUALS),
            Map.entry("like", ComparisonOperators.LIKE)
    );

    /**
     * Constructs a parser from a given query.
     * @param query String query representation
     */
    public QueryParser(String query) {
        if (query == null) {
            throw new NullPointerException("Query cant be null!");
        }

        this.lexer = new QueryLexer(query);
        this.expressions = new ArrayList<>();

        this.parseQuery();
    }

    /**
     * Checks whether the query is direct, meaning it only checks if jmbag is some value.
     * @return Boolean representing whether the query is direct
     */
    public boolean isDirectQuery() {
        return (this.expressions.size() == 1 &&
                this.expressions.get(0).getFieldValueGetter() == FieldValueGetters.JMBAG &&
                this.expressions.get(0).getComparisonOperator() == ComparisonOperators.EQUALS);
    }

    /**
     * If a query is direct, it will return the jmbag value from the query.
     * @return Jmbag value from the query
     */
    public String getQueriedJMBAG() {
        if (!this.isDirectQuery()) {
            throw new IllegalStateException("Cant get jmbag from an indirect query!");
        }

        return this.expressions.get(0).getStringLiteral();
    }

    /**
     * Returns a list of conditional expressions from a query.
     * @return A list of conditional expressions
     */
    public List<ConditionalExpression> getQuery() {
        return this.expressions;
    }

    /**
     * Function for query parsing.
     */
    private void parseQuery() {
        boolean firstExpression = true;

        while (true) {
            QueryToken temp = this.lexer.nextToken();

            if (temp.getType() == QueryTokenType.EOF) {
                break;
            }

            if (temp.getValue().toString().equalsIgnoreCase("and")) {
                this.lexer.nextToken();
                this.expressions.add(this.parseExpression());
                continue;
            }

            if (firstExpression) {
                this.expressions.add(this.parseExpression());
                firstExpression = false;
                continue;
            }

            throw new QueryParserException("Invalid query!");
        }
    }

    /**
     * Returns a single conditional expression parsed from a lexer tokens.
     * @return Single conditional expression
     */
    private ConditionalExpression parseExpression() {
        String identifierKey;
        try {
            identifierKey = this.lexer.getToken().getValue().toString().toLowerCase();
        } catch (Exception e) {
            throw new QueryParserException("No query field provided!");
        }

        if (!valueGetterByName.containsKey(identifierKey)) {
            throw new QueryParserException("Cant query by the provided field!");
        }

        String operatorKey;
        try {
            operatorKey = this.lexer.nextToken().getValue().toString().toLowerCase();
        } catch (Exception e) {
            throw new QueryParserException("No operator provided!");
        }

        if (!comparisonOperatorByName.containsKey(operatorKey)) {
            throw new QueryParserException("Cant identify the operator provided!");
        }

        QueryToken literal = this.lexer.nextToken();
        if (literal == null || literal.getValue() == null) {
            throw new QueryParserException("No string literal pattern provided!");
        }
        String literalString = literal.getValue().toString();

        if (literal.getType() != QueryTokenType.STRING) {
            throw new QueryParserException("Cant identify the pattern provided!");
        }

        return new ConditionalExpression(
                valueGetterByName.get(identifierKey),
                literalString,
                comparisonOperatorByName.get(operatorKey)
        );
    }

}
