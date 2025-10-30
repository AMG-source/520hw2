package model.filter;

import model.Transaction;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Filter implementation that keeps transactions with amount greater than
 * or equal to a specified minimum amount.
 *
 * This filter uses an inclusive comparison (>=) to determine which transactions
 * to include. Transactions with null references are ignored and excluded from
 * the result. This class implements the TransactionFilter interface following
 * the Strategy design pattern.
 *
 * @author Auto
 * @since 1.0
 */
public class AmountFilter implements TransactionFilter {
    private final double minAmount;

    /**
     * Constructs a new AmountFilter with the specified minimum amount threshold.
     * Only transactions with amount >= minAmount will pass through this filter.
     * 
     * @param minAmount The minimum transaction amount (inclusive) to filter by
     */
    public AmountFilter(double minAmount) {
        this.minAmount = minAmount;
    }

    /**
     * Filters the supplied transaction list, returning only transactions
     * that have an amount greater than or equal to the minimum amount
     * specified in the constructor. Null transactions are excluded.
     * 
     * @param txs Input list of transactions to filter (must not be null)
     * @return A new list containing only transactions with amount >= minAmount
     */
    @Override
    public List<Transaction> filter(List<Transaction> txs) {
        return txs.stream()
                  .filter(t -> t != null && t.getAmount() >= minAmount)
                  .collect(Collectors.toList());
    }
}
