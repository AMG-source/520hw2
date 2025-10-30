package model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Represents a single financial transaction in the Expense Tracker application.
 * Each transaction contains an amount, category, and automatically-generated timestamp.
 * Transaction objects are immutable once created.
 */
public class Transaction {

  private final double amount;
  private final String category;
  private final String timestamp;

  /**
   * Constructs a new Transaction with the specified amount and category.
   * The timestamp is automatically generated at the time of creation.
   * 
   * @param amount The monetary amount of the transaction
   * @param category The category classification of the transaction
   */
  public Transaction(double amount, String category) {
    this.amount = amount;
    this.category = category;
    this.timestamp = generateTimestamp();
  }

  /**
   * Returns the monetary amount of this transaction.
   * 
   * @return The transaction amount as a double
   */
  public double getAmount() {
    return amount;
  }

  /**
   * Returns the category of this transaction.
   * 
   * @return The transaction category as a String
   */
  public String getCategory() {
    return category;
  }
  
  /**
   * Returns the timestamp when this transaction was created.
   * The timestamp format is "dd-MM-yyyy HH:mm".
   * 
   * @return The transaction timestamp as a formatted String
   */
  public String getTimestamp() {
    return timestamp;
  }

  /**
   * Generates a timestamp string for the current date and time.
   * Format: "dd-MM-yyyy HH:mm" (e.g., "28-10-2025 14:30")
   * 
   * @return A formatted timestamp string
   */
  private String generateTimestamp() {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");  
    return sdf.format(new Date());
  }

}
