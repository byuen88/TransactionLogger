package ui.actions;

import model.Date;
import model.Logger;
import model.Transaction;
import ui.TransactionLogger;
import ui.exceptions.InvalidDateException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Objects;

/*
 * Represents a CalculateDisplayAction.
 */
public class CalculateDisplayAction extends AbstractAction {
    private TransactionLogger controller;
    private Logger logger;
    private JComboBox<String> choiceJComboBox;
    private JTextField month;
    private JTextField day;
    private JTextField year;
    private int monthField;
    private int dayField;
    private int yearField;
    private JTable table;
    private JPanel panel;
    private JDialog dialog;
    private DefaultTableModel tableModel;

    // MODIFIES: this
    // EFFECTS: constructs a CalculateDisplayAction
    public CalculateDisplayAction(TransactionLogger controller, JComboBox<String> choice) {
        super("Calculate");
        this.controller = controller;
        this.choiceJComboBox = choice;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        logger = controller.getLog();
        dialog = new JDialog();
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        dialog.setPreferredSize(new Dimension(600, 200));
        panel.setPreferredSize(new Dimension(500, 200));
        processTable(choiceJComboBox);
    }

    // EFFECTS: processes creating a table to display transactions
    public void processTable(JComboBox<String> choiceJComboBox) {
        JPanel inputs;
        int result;

        String choice = (String) choiceJComboBox.getSelectedItem();
        switch (Objects.requireNonNull(choice)) {
            case "Month":
                inputs = initializeTextFieldsMonth();
                result = JOptionPane.showConfirmDialog(null, inputs, "Please enter your information",
                        JOptionPane.OK_CANCEL_OPTION);
                createTableMonth(month, year, result, choice);
                break;
            case "Day":
                inputs = initializeTextFieldsDay();
                result = JOptionPane.showConfirmDialog(null, inputs, "Please enter your information",
                        JOptionPane.OK_CANCEL_OPTION);
                createTableDay(month, day, year, result, choice);
                break;
            default:
                processTableTwo(choiceJComboBox);
        }
    }

    // EFFECTS: processes second switch statement for creating a table
    public void processTableTwo(JComboBox<String> choiceJComboBox) {
        JPanel inputs;
        int result;

        String choice = (String) choiceJComboBox.getSelectedItem();
        if ("Year".equals(Objects.requireNonNull(choice))) {
            inputs = initializeTextFieldsYear();
            result = JOptionPane.showConfirmDialog(null, inputs, "Please enter your information",
                    JOptionPane.OK_CANCEL_OPTION);
            createTableYear(year, result, choice);
        } else {
            createTable(choice);
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

    // EFFECTS: creates table that displays all transactions in a month
    public void createTableMonth(JTextField month, JTextField year, int result, String choice) {
        if (result == JOptionPane.OK_OPTION) {
            translateMonthFields(month);
            translateYearFields(year);

            if (monthField <= 12 && monthField >= 1) {
                createTable(choice);
            }
        }
    }

    // EFFECTS: creates table that displays all transactions in a day
    public void createTableDay(JTextField month, JTextField day, JTextField year, int result, String choice) {
        if (result == JOptionPane.OK_OPTION) {
            try {
                processDate(month, day, year);
                createTable(choice);
            } catch (InvalidDateException e) {
                JOptionPane.showMessageDialog(null, "You have entered an invalid date");
            }
        }
    }

    // EFFECTS: creates table that displays all transactions in a year
    public void createTableYear(JTextField year, int result, String choice) {
        if (result == JOptionPane.OK_OPTION) {
            translateYearFields(year);

            if (yearField >= 0) {
                createTable(choice);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: creates table that displays transactions
    public void createTable(String choice) {
        Object[] columnNames = {"Name", "Amount", "Type", "Date"};
        tableModel = new DefaultTableModel(columnNames, 0);

        table = new JTable(tableModel);
        JTableHeader header = table.getTableHeader();

        table.setPreferredScrollableViewportSize(new Dimension(500, 50));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVisible(true);
        dialog.add(scrollPane);

        processDataArray(choice);
        panel.add(header);
        panel.add(table);
        dialog.setContentPane(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }


    // EFFECTS: processes data array for table
    public void processDataArray(String choice) {
        switch (choice) {
            case "Month":
                createDataArrayMonth(monthField, yearField);
                break;
            case "Day":
                createDataArrayDay(monthField, dayField, yearField);
                break;
            case "Year":
                createDataArrayYear(yearField);
                break;
            default:
                createDataArrayAll();
        }

    }

    // EFFECTS: creates data array for month
    public void createDataArrayMonth(int mth, int yr) {
        logger.sortDates();
        List<Date> dates = logger.getDates();

        for (Date d : dates) {
            if (d.getMonth() == mth && d.getYear() == yr) {
                String month = Integer.toString(d.getMonth());
                String day = Integer.toString(d.getDay());
                String year = Integer.toString(d.getYear());

                for (Transaction t : d.getTransactions()) {
                    String name = t.getTransactionName();
                    String amount = Double.toString(t.getAmount());
                    String type = t.getType().toString();

                    String[] row = {name, amount, type, month + "/" + day + "/" + year};

                    tableModel.addRow(row);
                }
            }
        }
    }

    // EFFECTS: creates data array for day
    public void createDataArrayDay(int mth, int dy, int yr) {
        logger.sortDates();
        List<Date> dates = logger.getDates();

        for (Date d : dates) {
            if (d.getMonth() == mth && d.getDay() == dy && d.getYear() == yr) {
                String month = Integer.toString(d.getMonth());
                String day = Integer.toString(d.getDay());
                String year = Integer.toString(d.getYear());

                for (Transaction t : d.getTransactions()) {
                    String name = t.getTransactionName();
                    String amount = Double.toString(t.getAmount());
                    String type = t.getType().toString();

                    String[] row = {name, amount, type, month + "/" + day + "/" + year};

                    tableModel.addRow(row);
                }
            }
        }
    }

    // EFFECTS: creates data array for year
    public void createDataArrayYear(int yr) {
        logger.sortDates();
        List<Date> dates = logger.getDates();

        for (Date d : dates) {
            if (d.getYear() == yr) {
                String month = Integer.toString(d.getMonth());
                String day = Integer.toString(d.getDay());
                String year = Integer.toString(d.getYear());

                for (Transaction t : d.getTransactions()) {
                    String name = t.getTransactionName();
                    String amount = Double.toString(t.getAmount());
                    String type = t.getType().toString();

                    String[] row = {name, amount, type, month + "/" + day + "/" + year};

                    tableModel.addRow(row);
                }
            }
        }
    }

    // EFFECTS: creates data array for all
    public void createDataArrayAll() {
        logger.sortDates();
        List<Date> dates = logger.getDates();

        for (Date d : dates) {
            String month = Integer.toString(d.getMonth());
            String day = Integer.toString(d.getDay());
            String year = Integer.toString(d.getYear());
            for (Transaction t : d.getTransactions()) {
                String name = t.getTransactionName();
                String amount = Double.toString(t.getAmount());
                String type = t.getType().toString();

                String[] row = {name, amount, type, month + "/" + day + "/" + year};

                tableModel.addRow(row);
            }
        }
    }
}
