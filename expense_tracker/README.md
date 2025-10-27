# hw1- Manual Review

The homework will be based on this project named "Expense Tracker",where users will be able to add/remove daily transaction. 

## Compile

````markdown
# Expense Tracker (Homework)

This small Java Swing application lets users add and view transactions (amount, category, date). This repository includes an implementation of a filtering feature (Strategy pattern) that lets you filter the transactions shown by category or by a minimum amount.

New functionality
- Filter transactions by Category (case-insensitive exact match)
- Filter transactions by Amount (show transactions with amount >= threshold)
- UI: a filter selector and parameter field have been added to the main window; click "Apply Filter" to activate the selected filter. Select "None" to clear filters.

Build and run

Preferred build (Ant):
```bash
cd expense_tracker
ant clean compile    # compile sources into bin/
ant test             # run unit tests
ant document         # generate javadoc into jdoc/
```

Run the GUI (after compile):
```bash
cd expense_tracker
java -cp bin ExpenseTrackerApp
```

Java runtime notes
- If you get an UnsupportedClassVersionError (compiled with newer JDK), either install a newer JDK or recompile to target your runtime.
- Example: run with Homebrew OpenJDK 25 if present:
```bash
/opt/homebrew/opt/openjdk/bin/java -cp bin ExpenseTrackerApp
```
Or add OpenJDK to your PATH:
```bash
echo 'export PATH="/opt/homebrew/opt/openjdk/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
```

Documentation
- API Javadoc is generated into `jdoc/` by `ant document`.

Development notes
- Filters are implemented under `src/model/filter/`:
	- `TransactionFilter` (interface)
	- `CategoryFilter` (filter by category)
	- `AmountFilter` (filter by minimum amount)
- Input validation is performed via `controller/InputValidation` for both adding transactions and filter parameters.

````
