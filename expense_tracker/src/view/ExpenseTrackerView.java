package view;

import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatterFactory;
import javax.swing.table.DefaultTableModel;

import controller.InputValidation;

import java.awt.*;
import java.text.NumberFormat;

import model.Transaction;
import java.util.List;

/**
 * View class for the Expense Tracker application following the MVC pattern.
 * Provides the graphical user interface for displaying transactions and
 * accepting user input. Includes UI components for adding transactions,
 * filtering by category or amount, and displaying results in a table.
 */
public class ExpenseTrackerView extends JFrame {

  private JTable transactionsTable;
  private JButton addTransactionBtn;
  private JFormattedTextField amountField;
  private JTextField categoryField;
  // filter controls
  private JComboBox<String> filterCombo;
  private JTextField filterParamField;
  private JButton applyFilterBtn;
  private DefaultTableModel model;
  

  /**
   * Constructs a new ExpenseTrackerView and initializes all UI components.
   * Sets up the main window with input fields for transactions, a table
   * to display transactions, and filter controls. The window is made visible
   * upon construction.
   */
  public ExpenseTrackerView() {
    setTitle("Expense Tracker"); // Set title
    setSize(600, 400); // Make GUI larger

    String[] columnNames = {"serial", "Amount", "Category", "Date"};
    this.model = new DefaultTableModel(columnNames, 0);

    addTransactionBtn = new JButton("Add Transaction");

    // Create UI components
    JLabel amountLabel = new JLabel("Amount:");
    NumberFormat format = NumberFormat.getNumberInstance();

    amountField = new JFormattedTextField(format);
    amountField.setColumns(10);

    
    JLabel categoryLabel = new JLabel("Category:");
    categoryField = new JTextField(10);

    // Create table
    transactionsTable = new JTable(model);
  
    // Layout components
    JPanel inputPanel = new JPanel();
    inputPanel.add(amountLabel);
    inputPanel.add(amountField);
    inputPanel.add(categoryLabel); 
    inputPanel.add(categoryField);
    inputPanel.add(addTransactionBtn);
  // filter UI
  filterCombo = new JComboBox<>(new String[]{"None", "Category", "Amount >="});
  filterParamField = new JTextField(10);
  applyFilterBtn = new JButton("Apply Filter");
  inputPanel.add(new JLabel("Filter:"));
  inputPanel.add(filterCombo);
  inputPanel.add(filterParamField);
  inputPanel.add(applyFilterBtn);
  
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(addTransactionBtn);
  
    // Add panels to frame
    add(inputPanel, BorderLayout.NORTH);
    add(new JScrollPane(transactionsTable), BorderLayout.CENTER); 
    add(buttonPanel, BorderLayout.SOUTH);
  
    // Set frame properties
    setSize(400, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  
  }

  /**
   * Refreshes the transaction table with a new list of transactions.
   * Clears the existing table rows, adds all provided transactions with
   * sequential serial numbers, calculates and displays the total cost,
   * and updates the table display.
   * 
   * @param transactions The list of Transaction objects to display in the table
   */
  public void refreshTable(List<Transaction> transactions) {
      // Clear existing rows
      model.setRowCount(0);
      // Get row count
      int rowNum = model.getRowCount();
      double totalCost=0;
      // Calculate total cost
      for(Transaction t : transactions) {
        totalCost+=t.getAmount();
      }
      // Add rows from transactions list
      for(Transaction t : transactions) {
        model.addRow(new Object[]{rowNum+=1,t.getAmount(), t.getCategory(), t.getTimestamp()}); 
      }
        // Add total row
        Object[] totalRow = {"Total", null, null, totalCost};
        model.addRow(totalRow);
  
      // Fire table update
      transactionsTable.updateUI();
  
    }  
  

  
  /**
   * Returns the "Add Transaction" button component.
   * Used by the controller to attach action listeners.
   * 
   * @return The JButton for adding transactions
   */
  public JButton getAddTransactionBtn() {
    return addTransactionBtn;
  }
  
  /**
   * Returns the filter type combo box component.
   * Allows selection between "None", "Category", and "Amount >=" filters.
   * 
   * @return The JComboBox for selecting filter type
   */
  public JComboBox<String> getFilterCombo() { 
    return filterCombo; 
  }
  
  /**
   * Returns the filter parameter text field component.
   * Used to enter the parameter value for the selected filter type.
   * 
   * @return The JTextField for entering filter parameters
   */
  public JTextField getFilterParamField() { 
    return filterParamField; 
  }
  
  /**
   * Returns the "Apply Filter" button component.
   * Used by the controller to attach action listeners.
   * 
   * @return The JButton for applying filters
   */
  public JButton getApplyFilterBtn() { 
    return applyFilterBtn; 
  }
  
  /**
   * Returns the table model used by the transactions table.
   * 
   * @return The DefaultTableModel managing the table data
   */
  public DefaultTableModel getTableModel() {
    return model;
  }
  
  /**
   * Returns the JTable component that displays transactions.
   * 
   * @return The JTable displaying transaction data
   */
  public JTable getTransactionsTable() {
    return transactionsTable;
  }

  /**
   * Retrieves the amount value entered in the amount field.
   * Strips commas and whitespace before parsing the numeric value.
   * Returns 0 if the field is empty.
   * 
   * @return The amount as a double, or 0 if the field is empty
   */
  public double getAmountField() {
    if(amountField.getText().isEmpty()) {
      return 0;
    }else {
    // Strip commas and whitespace before parsing
    String text = amountField.getText().replaceAll("[,\\s]", "");
    double amount = Double.parseDouble(text);
    return amount;
    }
  }

  /**
   * Sets the amount input field component.
   * 
   * @param amountField The JFormattedTextField to use for amount input
   */
  public void setAmountField(JFormattedTextField amountField) {
    this.amountField = amountField;
  }

  /**
   * Retrieves the category text entered in the category field.
   * 
   * @return The category string entered by the user
   */
  public String getCategoryField() {
    return categoryField.getText();
  }

  /**
   * Sets the category input field component.
   * 
   * @param categoryField The JTextField to use for category input
   */
  public void setCategoryField(JTextField categoryField) {
    this.categoryField = categoryField;
  }
}
