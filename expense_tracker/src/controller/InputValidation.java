package controller;

import java.util.Arrays;

/**
 * Utility class for validating user inputs in the Expense Tracker application.
 * Provides static methods to validate transaction amounts and categories
 * according to business rules.
 */
public class InputValidation {

  /**
   * Validates whether a transaction amount is within acceptable bounds.
   * Valid amounts must be greater than 0 and not exceed 1000.
   * 
   * @param amount The transaction amount to validate
   * @return true if the amount is valid (0 < amount <= 1000), false otherwise
   */
  public static boolean isValidAmount(double amount) {
    
    // Check range
    if(amount >1000) {
      return false;
    }
    if (amount < 0){
      return false;
    }
    if (amount == 0){
      return false;
    }
    return true;
  }

  /**
   * Validates whether a category string is acceptable for a transaction.
   * Valid categories must be non-null, non-empty, contain only letters,
   * and match one of the predefined valid categories (case-insensitive):
   * "food", "travel", "bills", "entertainment", "other"
   * 
   * @param category The category string to validate
   * @return true if the category is valid, false otherwise
   */
  public static boolean isValidCategory(String category) {

    if(category == null) {
      return false; 
    }
  
    if(category.trim().isEmpty()) {
      return false;
    }

    if(!category.matches("[a-zA-Z]+")) {
      return false;
    }

    String[] validWords = {"food", "travel", "bills", "entertainment", "other"};

    if(!Arrays.asList(validWords).contains(category.toLowerCase())) {
      // invalid word  
      return false;
    }
  
    return true;
  
  }

}
