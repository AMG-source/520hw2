package controller;

import view.ExpenseTrackerView;

import java.util.List;

import model.ExpenseTrackerModel;
import model.Transaction;
import model.filter.TransactionFilter;
import model.filter.CategoryFilter;
import model.filter.AmountFilter;

import javax.swing.JOptionPane;

/**
 * Controller for the ExpenseTracker application following the MVC pattern.
 * Manages interaction between the Model and View, handles user actions,
 * and supports adding transactions and applying filters (category or amount).
 */
public class ExpenseTrackerController {

  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;
  private TransactionFilter currentFilter = null;

  /**
   * Constructs a new ExpenseTrackerController and wires up event handlers
   * for the view components. Initializes the view with any existing data.
   * 
   * @param model The data model containing transaction data
   * @param view The view component that displays the UI
   */
  public ExpenseTrackerController(ExpenseTrackerModel model, ExpenseTrackerView view) {
    this.model = model;
    this.view = view;

    // Wire add button
    view.getAddTransactionBtn().addActionListener(e -> {
      double amount = view.getAmountField();
      String category = view.getCategoryField();
      if (!InputValidation.isValidAmount(amount)) {
        JOptionPane.showMessageDialog(view, "Invalid amount");
        return;
      }
      if (!InputValidation.isValidCategory(category)) {
        JOptionPane.showMessageDialog(view, "Invalid category");
        return;
      }
      addTransaction(amount, category);
    });

    // Wire filter UI
    view.getApplyFilterBtn().addActionListener(e -> {
      String sel = (String) view.getFilterCombo().getSelectedItem();
      String param = view.getFilterParamField().getText();
      if (sel == null || sel.equals("None")) {
        clearFilter();
        return;
      }
      try {
        if (sel.equals("Category")) {
          if (!InputValidation.isValidCategory(param)) {
            JOptionPane.showMessageDialog(view, "Invalid category parameter");
            return;
          }
          TransactionFilter f = new CategoryFilter(param);
          applyFilter(f);
        } else if (sel.startsWith("Amount")) {
          double min = Double.parseDouble(param);
          if (!InputValidation.isValidAmount(min)) {
            JOptionPane.showMessageDialog(view, "Amount parameter is invalid");
            return;
          }
          TransactionFilter f = new AmountFilter(min);
          applyFilter(f);
        } else {
          clearFilter();
        }
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(view, "Invalid number for amount filter");
      } catch (IllegalArgumentException ex) {
        JOptionPane.showMessageDialog(view, "Invalid filter parameter: " + ex.getMessage());
      }
    });

    // initial refresh
    refresh();
  }

  /**
   * Refreshes the view's transaction table with current data from the model.
   * If a filter is currently active, applies the filter before updating the view.
   * This method should be called whenever the model data changes or filters are modified.
   */
  public void refresh() {
    List<Transaction> transactions = model.getTransactions();
    if (currentFilter != null) {
      transactions = currentFilter.filter(transactions);
    }
    view.refreshTable(transactions);
  }

  /**
   * Adds a new transaction to the model after validating the input parameters.
   * If validation fails, the transaction is not added and false is returned.
   * After successful addition, the view is refreshed to display the new transaction.
   * 
   * @param amount The transaction amount (must be between 0 and 1000 exclusive/inclusive)
   * @param category The transaction category (must be one of the valid categories)
   * @return true if the transaction was successfully added, false if validation failed
   */
  public boolean addTransaction(double amount, String category) {
    if (!InputValidation.isValidAmount(amount)) {
      return false;
    }
    if (!InputValidation.isValidCategory(category)) {
      return false;
    }

    Transaction t = new Transaction(amount, category);
    model.addTransaction(t);
    // refresh (will apply filter if active)
    refresh();
    return true;
  }

  /**
   * Applies a filter to the displayed transactions. Only one filter can be active
   * at a time; applying a new filter replaces any existing filter.
   * The view is immediately refreshed to show only filtered transactions.
   * 
   * @param filter The TransactionFilter to apply (CategoryFilter or AmountFilter)
   */
  public void applyFilter(TransactionFilter filter) {
    this.currentFilter = filter;
    refresh();
  }

  /**
   * Clears any currently active filter and displays all transactions.
   * The view is immediately refreshed to show all transactions from the model.
   */
  public void clearFilter() {
    this.currentFilter = null;
    refresh();
  }

  // Other controller methods can be added here
}
