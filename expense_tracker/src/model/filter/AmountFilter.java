package model.filter;

import model.Transaction;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Filter that keeps transactions with amount >= minAmount.
 *
 * <p>Transactions with null references are ignored; the comparison is inclusive
 * (>=).</p>
 *
 * @author Auto
 * @since 1.0
 */
public class AmountFilter implements TransactionFilter {
    private final double minAmount;

    public AmountFilter(double minAmount) {
        this.minAmount = minAmount;
    }

    @Override
    public List<Transaction> filter(List<Transaction> txs) {
        return txs.stream()
                  .filter(t -> t != null && t.getAmount() >= minAmount)
                  .collect(Collectors.toList());
    }
}

