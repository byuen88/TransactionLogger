package ui.actions;

import model.Date;
import model.Logger;
import model.Transaction;
import ui.TransactionLogger;
import ui.exceptions.InvalidDateException;
import ui.exceptions.InvalidTransactionsException;
import ui.exceptions.LimitExceededException;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static model.Transaction.TransactionType.*;

/* EFFECTS: represents action to be taken when user wants to add a
   transaction to the system
*/
public class AddTransactionAction extends AbstractAction {
    private TransactionLogger controller;
    private Logger logger;
    private JTextField month;
    private JTextField day;
    private JTextField year;
    private JTextField name;
    private JTextField amount;
    private JComboBox<Transaction.TransactionType> type;
    private int monthField;
    private int dayField;
    private int yearField;
    private boolean isThereALimit;
    private double limit;

    // MODIFIES: this
    // EFFECTS: constructs the AddTransactionAction
    public AddTransactionAction(TransactionLogger controller) {
        super("Add Transaction");
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        logger = controller.getLog();
        createAddTransactionPopup();
    }

    // EFFECTS: processes adding a transaction
    public void createAddTransactionPopup() {
        JPanel inputs = initializeTextFields();

        int result = JOptionPane.showConfirmDialog(null, inputs,
                "Please Enter your Transaction Information", JOptionPane.OK_CANCEL_OPTION);
        initializeFields();
        if (result == JOptionPane.OK_OPTION) {
            try {
                addTransaction(processDate(), processTran(name, amount, type));
            } catch (InvalidDateException e) {
                JOptionPane.showMessageDialog(null, "You have entered an invalid date");
            } catch (InvalidTransactionsException e) {
                JOptionPane.showMessageDialog(null, "Transaction amount cannot be negative");
            } catch (LimitExceededException e) {
                JOptionPane.showMessageDialog(null, "Transaction cancelled");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: initialize JTextField fields
    public JPanel initializeTextFields() {
        Transaction.TransactionType[] types = {GROCERY, ENTERTAINMENT, FOOD, SHOPPING, OTHER};
        month = new JTextField(5);
        day = new JTextField(5);
        year = new JTextField(5);
        name = new JTextField(5);
        amount = new JTextField(5);
        type = new JComboBox<>(types);
        JPanel inputs = new JPanel();

        inputs.add(new JLabel("Month (in digits):"));
        inputs.add(month);
        inputs.add(new JLabel("Day (in digits):"));
        inputs.add(day);
        inputs.add(new JLabel("Year (in digits):"));
        inputs.add(year);
        inputs.add(new JLabel("Transaction Name:"));
        inputs.add(name);
        inputs.add(new JLabel("Transaction Amount: $"));
        inputs.add(amount);
        inputs.add(new JLabel("Transaction Type:"));
        inputs.add(type);

        return inputs;
    }

    // MODIFIES:
    // EFFECTS: initialize class fields
    public void initializeFields() {
        monthField = Integer.parseInt(month.getText());
        dayField = Integer.parseInt(day.getText());
        yearField = Integer.parseInt(year.getText());
        isThereALimit = logger.isThereALimit();
        limit = logger.getLimit();
    }

    // EFFECTS: checks if date passed in is valid, throw InvalidDateException if invalid. Otherwise, return the Date
    public Date processDate()
            throws InvalidDateException {

        if (Date.isValidDate(monthField, dayField, yearField)) {
            return logger.getDate(monthField, dayField, yearField);
        } else {
            throw new InvalidDateException();
        }
    }

    // EFFECTS: processes transaction fields and returns Transaction
    public Transaction processTran(JTextField nm, JTextField amt, JComboBox<Transaction.TransactionType> t)
            throws LimitExceededException, InvalidTransactionsException {
        String nameField = nm.getText();
        double amountField = Double.parseDouble(amt.getText());
        Transaction.TransactionType typeChoice = (Transaction.TransactionType) t.getSelectedItem();

        if (amountField >= 0) {

            if (limit == 0.0) {
                return new Transaction(nameField, amountField, typeChoice);
            }

            if (isThereALimit && (logger.monthTransactionAmount(monthField, yearField)
                    + amountField) <= limit) {
                return new Transaction(nameField, amountField, typeChoice);
            } else {
                int result1 = JOptionPane.showConfirmDialog(null,
                        "Transaction exceeds monthly limit. Would you like to continue?");

                if (result1 == JOptionPane.OK_OPTION) {
                    return new Transaction(nameField, amountField, typeChoice);
                } else {
                    throw new LimitExceededException();
                }
            }
        } else {
            throw new InvalidTransactionsException();
        }
    }

    // EFFECTS: adds transaction
    public void addTransaction(Date date, Transaction transaction) {
        date.addTransaction(transaction);
    }
}