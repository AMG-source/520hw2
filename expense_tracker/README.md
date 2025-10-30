# Expense Tracker - Homework 2

A Java Swing application that allows users to track daily transactions with features for adding, viewing, and filtering expense records.

## Overview

The Expense Tracker is a desktop application built using the Model-View-Controller (MVC) architecture pattern. Users can add transactions with monetary amounts and categories, view them in a table, and apply filters to analyze their expenses.

## Features

### Core Functionality
- **Add Transactions**: Enter amount and category to record new expenses
- **View Transactions**: Display all transactions in a table with serial numbers, amounts, categories, and timestamps
- **Calculate Total**: Automatically calculate and display the total cost of all displayed transactions
- **Input Validation**: Validate amounts (must be between 0 and 1000, exclusive of 0) and categories (must be one of: food, travel, bills, entertainment, other)

### Filtering Capabilities (NEW)
- **Filter by Category**: Show only transactions matching a specific category (case-insensitive)
- **Filter by Amount**: Show only transactions with amount greater than or equal to a specified threshold
- **Clear Filters**: Return to viewing all transactions
- **Single Active Filter**: Only one filter can be active at a time

### User Interface
- Clean, intuitive Swing-based GUI
- Input fields for amount and category
- Filter selector dropdown with parameter field
- Sortable transaction table displaying all transaction details
- Real-time total cost calculation

## Architecture

The application follows the **MVC (Model-View-Controller)** design pattern:

### Model (`model` package)
- **ExpenseTrackerModel**: Manages the collection of transactions
- **Transaction**: Immutable class representing a single transaction with amount, category, and timestamp

### View (`view` package)  
- **ExpenseTrackerView**: Swing-based GUI with input fields, buttons, and transaction table

### Controller (`controller` package)
- **ExpenseTrackerController**: Coordinates between Model and View, handles user actions
- **InputValidation**: Utility class for validating user inputs

### Filter (`model.filter` package)
- **TransactionFilter**: Strategy interface for filtering transactions
- **CategoryFilter**: Filters transactions by category (case-insensitive)
- **AmountFilter**: Filters transactions by minimum amount (inclusive)

The filtering feature implements the **Strategy design pattern**, allowing different filtering algorithms to be applied interchangeably.

## Installation & Setup

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Apache Ant (optional, for build automation)

### Build Instructions

#### Using Ant (Recommended):
```bash
cd expense_tracker
ant clean compile    # Compile sources into bin/
ant test             # Run unit tests  
ant document         # Generate javadoc into jdoc/
```

#### Manual Compilation:
```bash
cd expense_tracker/src
javac -d ../bin ExpenseTrackerApp.java model/*.java view/*.java controller/*.java model/filter/*.java
```

## Running the Application

### After Ant Build:
```bash
cd expense_tracker
java -cp bin ExpenseTrackerApp
```

### After Manual Compilation:
```bash
cd expense_tracker
java -cp bin ExpenseTrackerApp
```

### Java Runtime Notes
If you encounter an `UnsupportedClassVersionError` (indicating the code was compiled with a newer JDK than your runtime), you have two options:

1. **Install a newer JDK** that matches or exceeds the compilation version
2. **Recompile** the code targeting your current Java runtime version

#### Example: Running with Homebrew OpenJDK 25 on macOS
```bash
/opt/homebrew/opt/openjdk/bin/java -cp bin ExpenseTrackerApp
```
Or add OpenJDK to your PATH:
```bash
echo 'export PATH="/opt/homebrew/opt/openjdk/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
```

## Usage Guide

### Adding a Transaction
1. Enter an amount in the "Amount" field (must be between 0 and 1000)
2. Enter a category in the "Category" field (must be one of: food, travel, bills, entertainment, other)
3. Click "Add Transaction"
4. The transaction appears in the table with an auto-generated timestamp

### Filtering Transactions

#### Filter by Category:
1. Select "Category" from the filter dropdown
2. Enter a category name in the parameter field (e.g., "food")
3. Click "Apply Filter"
4. Only transactions matching that category will be displayed

#### Filter by Amount:
1. Select "Amount >=" from the filter dropdown
2. Enter a minimum amount in the parameter field (e.g., "50")
3. Click "Apply Filter"
4. Only transactions with amount >= the specified value will be displayed

#### Clear Filters:
1. Select "None" from the filter dropdown
2. Click "Apply Filter"
3. All transactions will be displayed again

### Input Validation Rules

#### Amount Validation:
- Must be greater than 0
- Must not exceed 1000
- Cannot be exactly 0 or negative

#### Category Validation:
- Cannot be null or empty
- Must contain only alphabetic characters (no numbers or special characters)
- Must be one of the valid categories: **food**, **travel**, **bills**, **entertainment**, **other**
- Case-insensitive (e.g., "Food", "FOOD", and "food" are all valid)

## Project Structure

```
expense_tracker/
├── src/
│   ├── ExpenseTrackerApp.java          # Main application entry point
│   ├── controller/
│   │   ├── ExpenseTrackerController.java  # MVC Controller
│   │   └── InputValidation.java           # Input validation utilities
│   ├── model/
│   │   ├── ExpenseTrackerModel.java    # MVC Model (data management)
│   │   ├── Transaction.java            # Transaction entity class
│   │   └── filter/
│   │       ├── TransactionFilter.java  # Filter strategy interface
│   │       ├── CategoryFilter.java     # Category filter implementation
│   │       └── AmountFilter.java       # Amount filter implementation
│   └── view/
│       └── ExpenseTrackerView.java     # MVC View (GUI)
├── test/
│   └── TestExample.java                # Unit tests
├── bin/                                # Compiled class files
├── jdoc/                               # Generated Javadoc documentation
├── build.xml                           # Ant build configuration
└── README.md                           # This file
```

## Documentation

### API Documentation
Full API documentation (Javadoc) is generated into the `jdoc/` directory when you run:
```bash
ant document
```

Open `jdoc/index.html` in a web browser to view the complete API documentation.

### Key API Classes

#### Public APIs in ExpenseTrackerController:
- `addTransaction(double amount, String category)` - Add a new transaction
- `applyFilter(TransactionFilter filter)` - Apply a filter to displayed transactions
- `clearFilter()` - Remove active filter and show all transactions
- `refresh()` - Refresh the view with current model data

#### Public APIs in ExpenseTrackerModel:
- `addTransaction(Transaction t)` - Add a transaction to the model
- `removeTransaction(Transaction t)` - Remove a transaction from the model
- `getTransactions()` - Get an unmodifiable list of all transactions

#### Public APIs in InputValidation:
- `isValidAmount(double amount)` - Validate transaction amount
- `isValidCategory(String category)` - Validate transaction category

#### Public APIs in TransactionFilter:
- `filter(List<Transaction> txs)` - Filter a list of transactions

## Testing

The project includes comprehensive unit tests covering:
- Adding valid and invalid transactions
- Input validation for amounts and categories
- Transaction removal
- Category filtering (matching, case-insensitivity, no matches)
- Amount filtering (threshold comparisons)
- Filter clearing and switching
- Edge cases and boundary conditions

### Running Tests:
```bash
ant test
```

## Design Patterns Used

1. **Model-View-Controller (MVC)**: Separates data (Model), presentation (View), and logic (Controller)
2. **Strategy Pattern**: Used for TransactionFilter implementations, allowing interchangeable filtering algorithms
3. **Immutability**: Transaction objects are immutable to prevent unintended modifications

## Design Principles Applied

- **Single Responsibility Principle**: Each class has one clear purpose
- **Open-Closed Principle**: Filters are open for extension (new filter types can be added) but closed for modification
- **Encapsulation**: Internal data structures are protected from external modification
- **Input Validation**: All user inputs are validated before processing

## Development Notes

### Adding New Features
- To add a new filter type: Implement the `TransactionFilter` interface
- To add new validation rules: Extend the `InputValidation` class
- To add UI components: Modify `ExpenseTrackerView` and wire them in `ExpenseTrackerController`

### Known Limitations
- Single filter limitation: Only one filter can be active at a time
- No data persistence: Transactions are stored in memory only
- Fixed category list: Categories are predefined and cannot be customized

## Future Enhancements
- Support for multiple simultaneous filters
- Data persistence (save/load transactions)
- Export functionality (CSV, PDF)
- Custom categories
- Transaction editing
- Date range filtering
- Charts and visualizations
- Multi-currency support
