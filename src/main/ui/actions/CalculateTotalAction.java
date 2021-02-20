package ui.actions;

import model.Date;
import model.Logger;
import ui.TransactionLogger;
import ui.exceptions.InvalidDateException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

/* EFFECTS: represents action to be taken when user wants to calculate
   total amount
 */
public class CalculateTotalAction extends AbstractAction {
    private TransactionLogger controller;
    private Logger logger;
    private JComboBox<String> choiceJComboBox;
    private JTextField month;
    private JTextField day;
    private JTextField year;
    private int monthField;
    private int dayField;
    private int yearField;

    // MODIFIES: this
    // EFFECTS: constructs a CalculateTotalAction
    public CalculateTotalAction(TransactionLogger controller, JComboBox<String> choice) {
        super("Calculate");
        this.controller = controller;
        this.choiceJComboBox = choice;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        logger = controller.getLog();
        createCalculateTotalPopup(choiceJComboBox);
    }

    // EFFECTS: processes calculating total amount
    public void createCalculateTotalPopup(JComboBox<String> choiceJComboBox) {
        JPanel inputs;
        int result;

        String choice = (String) choiceJComboBox.getSelectedItem();
        switch (Objects.requireNonNull(choice)) {
            case "Month":
                inputs = initializeTextFieldsMonth();
                result = JOptionPane.showConfirmDialog(null, inputs, "Please enter your information",
                        JOptionPane.OK_CANCEL_OPTION);
                calculateTotalMonth(month, year, result);
                break;
            case "Day":
                inputs = initializeTextFieldsDay();
                result = JOptionPane.showConfirmDialog(null, inputs, "Please enter your information",
                        JOptionPane.OK_CANCEL_OPTION);
                calculateTotalDay(month, day, year, result);
                break;
            default:
                inputs = initializeTextFieldsYear();
                result = JOptionPane.showConfirmDialog(null, inputs, "Please enter your information",
                        JOptionPane.OK_CANCEL_OPTION);
                calculateTotalYear(year, result);
        }
    }

    // MODIFIES: this
    // EFFECTS: initialize JTextField fields for day
    public JPanel initializeTextFieldsMonth() {
        month = new JTextField(5);
        year = new JTextField(5);
        JPanel inputsMonth = new JPanel();

        inputsMonth.add(new JLabel("Month (in digits):"));
        inputsMonth.add(month);
        inputsMonth.add(new JLabel("Year (in digits):"));
        inputsMonth.add(year);

        return inputsMonth;
    }

    // MODIFIES: this
    // EFFECTS: initialize JTextField fields for month
    public JPanel initializeTextFieldsDay() {
        month = new JTextField(5);
        day = new JTextField(5);
        year = new JTextField(5);
        JPanel inputsDay = new JPanel();

        inputsDay.add(new JLabel("Month (in digits):"));
        inputsDay.add(month);
        inputsDay.add(new JLabel("Day (in digits):"));
        inputsDay.add(day);
        inputsDay.add(new JLabel("Year (in digits):"));
        inputsDay.add(year);

        return inputsDay;
    }

    // MODIFIES: this
    // EFFECTS: initialize JTextField fields for year
    public JPanel initializeTextFieldsYear() {
        year = new JTextField(5);
        JPanel inputsYear = new JPanel();

        inputsYear.add(new JLabel("Year (in digits):"));
        inputsYear.add(year);

        return inputsYear;
    }

    // MODIFIES: this
    // EFFECTS: translates month JTextField into integer
    public void translateMonthFields(JTextField month) {
        monthField = Integer.parseInt(month.getText());
    }

    // MODIFIES: this
    // EFFECTS: translates day JTextField into integer
    public void translateDayFields(JTextField day) {
        dayField = Integer.parseInt(day.getText());
    }

    // MODIFIES: this
    // EFFECTS: translates year JTextField into integer
    public void translateYearFields(JTextField year) {
        yearField = Integer.parseInt(year.getText());
    }

    // EFFECTS: checks if date passed in is valid, throw InvalidDateException if invalid. Otherwise, return the Date
    public Date processDate(JTextField month, JTextField day, JTextField year)
            throws InvalidDateException {
        translateMonthFields(month);
        translateDayFields(day);
        translateYearFields(year);

        if (Date.isValidDate(monthField, dayField, yearField)) {
            return logger.getDate(monthField, dayField, yearField);
        } else {
            throw new InvalidDateException();
        }
    }

    // EFFECTS: processes calculation of a month
    public void calculateTotalMonth(JTextField month, JTextField year, int result) {
        if (result == JOptionPane.OK_OPTION) {
            translateMonthFields(month);
            translateYearFields(year);

            if (monthField <= 12 && monthField >= 1) {
                String total = Double.toString(logger.monthTransactionAmount(monthField, yearField));
                JOptionPane.showMessageDialog(null, "Total: $" + total);
            } else {
                JOptionPane.showMessageDialog(null, "You have entered an invalid date");
            }

        }
    }

    // EFFECTS: processes calculation of a day
    public void calculateTotalDay(JTextField month, JTextField day, JTextField year, int result) {
        if (result == JOptionPane.OK_OPTION) {
            try {
                String total = Double.toString(processDate(month, day, year).amountOfTheDay());
                JOptionPane.showMessageDialog(null, "Total: $" + total);
            } catch (InvalidDateException e) {
                JOptionPane.showMessageDialog(null, "You have entered an invalid date");
            }
        }
    }

    // EFFECTS: processes calculation of a year
    public void calculateTotalYear(JTextField year, int result) {
        if (result == JOptionPane.OK_OPTION) {
            translateYearFields(year);

            if (yearField >= 0) {
                String total = Double.toString(logger.totalTransactionAmount(yearField));
                JOptionPane.showMessageDialog(null, "Total: $" + total);
            }
        }
    }
}
