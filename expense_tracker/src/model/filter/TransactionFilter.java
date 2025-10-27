package model.filter;

import model.Transaction;
import java.util.List;

/**
 * Strategy interface for filtering transactions.
 *
 * <p>Implementations perform a selection over an input list of {@link Transaction}
 * and return a (possibly empty) list of the matching transactions. Implementations
 * should not modify the input list.</p>
 *
 * @author Auto
 * @since 1.0
 */
public interface TransactionFilter {
    /**
     * Filter the supplied transactions and return only those that match.
     *
     * @param txs input transactions (not null)
     * @return filtered list (may be empty)
     */
    List<Transaction> filter(List<Transaction> txs);
}

