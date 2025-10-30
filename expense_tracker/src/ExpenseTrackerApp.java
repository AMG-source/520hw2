import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import controller.ExpenseTrackerController;
import model.ExpenseTrackerModel;
import view.ExpenseTrackerView;
import model.Transaction;
import controller.InputValidation;

/**
 * Main application class for the Expense Tracker application.
 * This class initializes the MVC components and starts the GUI.
 */
public class ExpenseTrackerApp {

  /**
   * Main entry point for the Expense Tracker application.
   * Creates the Model, View, and Controller components following the MVC pattern,
   * then displays the GUI. All event handling is managed by the controller.
   * 
   * @param args Command line arguments (not used)
   */
  public static void main(String[] args) {
    
    // Create MVC components
    ExpenseTrackerModel model = new ExpenseTrackerModel();
    ExpenseTrackerView view = new ExpenseTrackerView();
    ExpenseTrackerController controller = new ExpenseTrackerController(model, view);

    // Initialize view
    view.setVisible(true);

    // Controller handles button listeners, no need to add them here

  }

}
