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
 * Controller for the ExpenseTracker application. Supports adding transactions
 * and applying a single active TransactionFilter (category or amount).
 */
public class ExpenseTrackerController {

  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;
  private TransactionFilter currentFilter = null;

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

  public void refresh() {
    List<Transaction> transactions = model.getTransactions();
    if (currentFilter != null) {
      transactions = currentFilter.filter(transactions);
    }
    view.refreshTable(transactions);
  }

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

  public void applyFilter(TransactionFilter filter) {
    this.currentFilter = filter;
    refresh();
  }

  public void clearFilter() {
    this.currentFilter = null;
    refresh();
  }

  // Other controller methods can be added here
}