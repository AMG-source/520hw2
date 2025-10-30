package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Model class for the Expense Tracker application following the MVC pattern.
 * Manages the collection of transactions and provides methods to add, remove,
 * and retrieve transactions. The transaction list is protected from external
 * modification by returning an unmodifiable view.
 */
public class ExpenseTrackerModel {

  private List<Transaction> transactions;

  /**
   * Constructs a new ExpenseTrackerModel with an empty transaction list.
   */
  public ExpenseTrackerModel() {
    transactions = new ArrayList<>(); 
  }

  /**
   * Adds a new transaction to the model's transaction list.
   * 
   * @param t The Transaction object to add (should not be null)
   */
  public void addTransaction(Transaction t) {
    transactions.add(t);
  }

  /**
   * Removes a transaction from the model's transaction list.
   * If the transaction is not found in the list, no action is taken.
   * 
   * @param t The Transaction object to remove
   */
  public void removeTransaction(Transaction t) {
    transactions.remove(t);
  }

  /**
   * Returns an unmodifiable view of the transaction list.
   * This prevents external code from modifying the internal transaction list
   * while still allowing read access to the data.
   * 
   * @return An unmodifiable List of Transaction objects
   */
  public List<Transaction> getTransactions() {
    // Alternative 1: Apply the decorator design pattern (see below)
    // Alternative 2: Return a copy of the list
    return Collections.unmodifiableList(transactions);
  }

}
