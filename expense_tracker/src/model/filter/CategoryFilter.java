package model.filter;

import model.Transaction;
import controller.InputValidation;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Filter implementation that keeps transactions matching a specific category.
 * The matching is performed case-insensitively, so "Food", "food", and "FOOD"
 * are all treated as equivalent.
 *
 * This filter performs an exact, case-insensitive match against the transaction's
 * category string. The provided category parameter is validated via
 * InputValidation to ensure it meets the application's category requirements.
 * This class implements the TransactionFilter interface following the Strategy
 * design pattern.
 *
 * @author Auto
 * @since 1.0
 */
public class CategoryFilter implements TransactionFilter {
    private final String category;

    /**
     * Constructs a new CategoryFilter with the specified category.
     * The category must be valid according to InputValidation rules
     * (one of: food, travel, bills, entertainment, other).
     * 
     * @param category The category to filter by (case-insensitive)
     * @throws IllegalArgumentException if the category is not valid according
     *         to InputValidation.isValidCategory()
     */
    public CategoryFilter(String category) {
        if (!InputValidation.isValidCategory(category)) {
            throw new IllegalArgumentException("Invalid category");
        }
        this.category = category.trim();
    }

    /**
     * Filters the supplied transaction list, returning only transactions
     * whose category matches the category specified in the constructor.
     * The comparison is case-insensitive. Transactions with null categories
     * are excluded from the result.
     * 
     * @param txs Input list of transactions to filter (must not be null)
     * @return A new list containing only transactions matching the specified category
     */
    @Override
    public List<Transaction> filter(List<Transaction> txs) {
        final String target = category.toLowerCase(Locale.ROOT);
        return txs.stream()
                  .filter(t -> t.getCategory() != null && t.getCategory().toLowerCase(Locale.ROOT).equals(target))
                  .collect(Collectors.toList());
    }
}
