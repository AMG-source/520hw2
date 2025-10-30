package model.filter;

import model.Transaction;
import java.util.List;

/**
 * Strategy interface for filtering transactions.
 * Implementations of this interface define different filtering strategies
 * (e.g., by category, by amount) to select specific transactions from a list.
 *
 * This interface follows the Strategy design pattern, allowing different
 * filtering algorithms to be used interchangeably. Implementations perform
 * a selection over an input list of Transaction objects and return a
 * (possibly empty) list of the matching transactions. Implementations
 * should not modify the input list.
 *
 * @author Auto
 * @since 1.0
 */
public interface TransactionFilter {
    /**
     * Filter the supplied transactions and return only those that match
     * the implementation's filtering criteria.
     *
     * @param txs Input list of transactions to filter (must not be null)
     * @return A new list containing only the transactions that match the filter
     *         criteria. May be empty if no transactions match. Never null.
     */
    List<Transaction> filter(List<Transaction> txs);
}
