// package test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import controller.ExpenseTrackerController;
import model.ExpenseTrackerModel;
import model.Transaction;
import model.filter.TransactionFilter;
import model.filter.CategoryFilter;
import model.filter.AmountFilter;
import view.ExpenseTrackerView;


public class TestExample {
  
  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;
  private ExpenseTrackerController controller;

  @Before
  public void setup() {
    model = new ExpenseTrackerModel();
    view = new ExpenseTrackerView();
    controller = new ExpenseTrackerController(model, view);
  }

  public double getTotalCost() {
    double totalCost = 0.0;
        List<Transaction> allTransactions = model.getTransactions(); // Using the model's getTransactions method
    for (Transaction transaction : allTransactions) {
      totalCost += transaction.getAmount();
    }
    return totalCost;
  }

  @Test
  public void testAddTransaction() {
    // Pre-condition: List of transactions is empty
    assertEquals(0, model.getTransactions().size());
    
    // Perform the action: Add a transaction
    assertTrue(controller.addTransaction(50.00, "food"));
    
    // Post-condition: List of transactions contains one transaction
    assertEquals(1, model.getTransactions().size());
    
    // Check the contents of the list
    assertEquals(50.00, getTotalCost(), 0.01);
  }

  @Test
  public void testRemoveTransaction() {
    // Pre-condition: List of transactions is empty
    assertEquals(0, model.getTransactions().size());
    
    // Perform the action: Add and remove a transaction
        Transaction addedTransaction = new Transaction(50.00, "Groceries");
    model.addTransaction(addedTransaction);
    
    // Pre-condition: List of transactions contains one transaction
    assertEquals(1, model.getTransactions().size());
    
    // Perform the action: Remove the transaction
    model.removeTransaction(addedTransaction);
    
    // Post-condition: List of transactions is empty
    List<Transaction> transactions = model.getTransactions();
    assertEquals(0, transactions.size());
    
    // Check the total cost after removing the transaction
    double totalCost = getTotalCost();
    assertEquals(0.00, totalCost, 0.01);
  }

  // ==================== NEW TEST CASES ====================

  /**
   * Test Case 1: Add Valid Transaction
   * Steps: Add a transaction with amount 50.00 and category "food"
   * Expected Output: Transaction is added to the table, Total Cost is accurately updated
   */
  @Test
  public void testAddValidTransaction() {
    // Pre-condition: List of transactions is empty
    assertEquals(0, model.getTransactions().size());
    assertEquals(0.00, getTotalCost(), 0.01);
    
    // Perform the action: Add a valid transaction with amount 50.00 and category "food"
    boolean result = controller.addTransaction(50.00, "food");
    
    // Post-condition: Transaction is added successfully
    assertTrue(result);
    assertEquals(1, model.getTransactions().size());
    
    // Verify the transaction details
    Transaction addedTransaction = model.getTransactions().get(0);
    assertEquals(50.00, addedTransaction.getAmount(), 0.01);
    assertEquals("food", addedTransaction.getCategory());
    
    // Verify Total Cost is accurately updated
    assertEquals(50.00, getTotalCost(), 0.01);
  }

  /**
   * Test Case 2: Input Validation for Amount
   * Steps: Attempt to add a transaction with an invalid amount
   * Expected Output: Error messages are displayed, transactions and Total Cost remain unchanged
   */
  @Test
  public void testInputValidationForInvalidAmount() {
    // Pre-condition: List of transactions is empty
    assertEquals(0, model.getTransactions().size());
    double initialTotalCost = getTotalCost();
    
    // Perform the action: Attempt to add transaction with invalid amounts
    // Test negative amount
    boolean result1 = controller.addTransaction(-50.00, "food");
    assertFalse(result1);
    
    // Test zero amount
    boolean result2 = controller.addTransaction(0.00, "food");
    assertFalse(result2);
    
    // Test amount exceeding maximum (>1000)
    boolean result3 = controller.addTransaction(1500.00, "food");
    assertFalse(result3);
    
    // Post-condition: Transactions list remains empty
    assertEquals(0, model.getTransactions().size());
    
    // Verify Total Cost remains unchanged
    assertEquals(initialTotalCost, getTotalCost(), 0.01);
  }

  /**
   * Test Case 3: Input Validation for Category
   * Steps: Attempt to add a transaction with an invalid category
   * Expected Output: Error messages are displayed, transactions and Total Cost remain unchanged
   */
  @Test
  public void testInputValidationForInvalidCategory() {
    // Pre-condition: List of transactions is empty
    assertEquals(0, model.getTransactions().size());
    double initialTotalCost = getTotalCost();
    
    // Perform the action: Attempt to add transaction with invalid categories
    // Test invalid category (not in the allowed list)
    boolean result1 = controller.addTransaction(50.00, "groceries");
    assertFalse(result1);
    
    // Test empty category
    boolean result2 = controller.addTransaction(50.00, "");
    assertFalse(result2);
    
    // Test category with numbers
    boolean result3 = controller.addTransaction(50.00, "food123");
    assertFalse(result3);
    
    // Test category with special characters
    boolean result4 = controller.addTransaction(50.00, "food!");
    assertFalse(result4);
    
    // Post-condition: Transactions list remains empty
    assertEquals(0, model.getTransactions().size());
    
    // Verify Total Cost remains unchanged
    assertEquals(initialTotalCost, getTotalCost(), 0.01);
  }

  /**
   * Test Case 4: Filter by Amount
   * Steps: Add multiple transactions with different amounts, apply amount filter
   * Expected Output: Only transactions matching the amount are returned (and will be displayed)
   */
  @Test
  public void testFilterByAmount() {
    // Pre-condition: Add multiple transactions with different amounts
    controller.addTransaction(25.00, "food");
    controller.addTransaction(75.00, "travel");
    controller.addTransaction(150.00, "bills");
    controller.addTransaction(50.00, "entertainment");
    
    assertEquals(4, model.getTransactions().size());
    
    // Perform the action: Apply amount filter for amounts >= 50.00
    TransactionFilter amountFilter = new AmountFilter(50.00);
    List<Transaction> filteredTransactions = amountFilter.filter(model.getTransactions());
    
    // Post-condition: Only transactions with amount >= 50.00 are returned
    assertEquals(3, filteredTransactions.size());
    
    // Verify all returned transactions have amount >= 50.00
    for (Transaction t : filteredTransactions) {
      assertTrue(t.getAmount() >= 50.00);
    }
    
    // Verify the specific transactions that match
    assertTrue(filteredTransactions.stream().anyMatch(t -> t.getAmount() == 75.00));
    assertTrue(filteredTransactions.stream().anyMatch(t -> t.getAmount() == 150.00));
    assertTrue(filteredTransactions.stream().anyMatch(t -> t.getAmount() == 50.00));
    
    // Verify transaction with amount < 50.00 is NOT included
    assertFalse(filteredTransactions.stream().anyMatch(t -> t.getAmount() == 25.00));
  }

  /**
   * Test Case 5: Filter by Category
   * Steps: Add multiple transactions with different categories, apply category filter
   * Expected Output: Only transactions matching the category are returned (and will be displayed)
   */
  @Test
  public void testFilterByCategory() {
    // Pre-condition: Add multiple transactions with different categories
    controller.addTransaction(50.00, "food");
    controller.addTransaction(100.00, "travel");
    controller.addTransaction(75.00, "food");
    controller.addTransaction(200.00, "bills");
    controller.addTransaction(30.00, "food");
    
    assertEquals(5, model.getTransactions().size());
    
    // Perform the action: Apply category filter for "food"
    TransactionFilter categoryFilter = new CategoryFilter("food");
    List<Transaction> filteredTransactions = categoryFilter.filter(model.getTransactions());
    
    // Post-condition: Only transactions with category "food" are returned
    assertEquals(3, filteredTransactions.size());
    
    // Verify all returned transactions have category "food"
    for (Transaction t : filteredTransactions) {
      assertEquals("food", t.getCategory());
    }
    
    // Verify the specific amounts of food transactions
    assertTrue(filteredTransactions.stream().anyMatch(t -> t.getAmount() == 50.00));
    assertTrue(filteredTransactions.stream().anyMatch(t -> t.getAmount() == 75.00));
    assertTrue(filteredTransactions.stream().anyMatch(t -> t.getAmount() == 30.00));
    
    // Verify transactions with other categories are NOT included
    assertFalse(filteredTransactions.stream().anyMatch(t -> t.getCategory().equals("travel")));
    assertFalse(filteredTransactions.stream().anyMatch(t -> t.getCategory().equals("bills")));
  }

}