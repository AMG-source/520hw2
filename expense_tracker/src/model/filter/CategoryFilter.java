package model.filter;

import model.Transaction;
import controller.InputValidation;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Filter that keeps transactions matching a specific category (case-insensitive).
 *
 * <p>This filter performs an exact, case-insensitive match against the transaction's
 * category string. The provided category parameter is validated via
 * {@link controller.InputValidation}.</p>
 *
 * @author Auto
 * @since 1.0
 */
public class CategoryFilter implements TransactionFilter {
    private final String category;

    public CategoryFilter(String category) {
        if (!InputValidation.isValidCategory(category)) {
            throw new IllegalArgumentException("Invalid category");
        }
        this.category = category.trim();
    }

    @Override
    public List<Transaction> filter(List<Transaction> txs) {
        final String target = category.toLowerCase(Locale.ROOT);
        return txs.stream()
                  .filter(t -> t.getCategory() != null && t.getCategory().toLowerCase(Locale.ROOT).equals(target))
                  .collect(Collectors.toList());
    }
}

