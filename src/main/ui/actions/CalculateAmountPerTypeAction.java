package ui.actions;

import model.Logger;
import model.Pie;
import model.Transaction;
import ui.TransactionLogger;
import ui.PieChartDrawer;

import javax.swing.*;
import java.awt.event.ActionEvent;

/*
 * Represents a CalculateAmountPerTypeAction.
 */
public class CalculateAmountPerTypeAction extends AbstractAction {
    private TransactionLogger controller;
    private Logger logger;
    private JDialog dialog;
    private JPanel panel;
    private JTextField year;
    private int yearField;

    // MODIFIES: this
    // EFFECTS: constructs a CalculateAmountPerTypeAction
    public CalculateAmountPerTypeAction(TransactionLogger controller) {
        super("Amount Spent on Each Transaction Type");
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        logger = controller.getLog();
        dialog = new JDialog();
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        processPieChart();
    }

    // EFFECTS: processes creating a pie chart
    public void processPieChart() {
        JPanel inputs;
        int result;

        inputs = initializeTextFieldsYear();
        result = JOptionPane.showConfirmDialog(null, inputs, "Please enter information",
                JOptionPane.OK_CANCEL_OPTION);
        createPieChart(year, result);
    }

    // EFFECTS: creates the pie chart
    public void createPieChart(JTextField year, int result) {
        if (result == JOptionPane.OK_OPTION) {
            translateYearFields(year);

            if (yearField >= 0) {
                panel.add(new PieChartDrawer(new Pie(logger, yearField)));
                initializePanelLabels();
                dialog.add(panel);
                dialog.setSize(500, 575);
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        }
    }

    // EFFECTS: initialize Panel texts
    public void initializePanelLabels() {
        panel.add(new JLabel("Grocery(Blue): $"
                + logger.totalSpecificTransactionTypePerYear(Transaction.TransactionType.GROCERY, yearField)));
        panel.add(new JLabel("Entertainment(Red): $"
                + logger.totalSpecificTransactionTypePerYear(Transaction.TransactionType.ENTERTAINMENT, yearField)));
        panel.add(new JLabel("Food(Green): $"
                + logger.totalSpecificTransactionTypePerYear(Transaction.TransactionType.FOOD, yearField)));
        panel.add(new JLabel("Shopping(Purple): $"
                + logger.totalSpecificTransactionTypePerYear(Transaction.TransactionType.SHOPPING, yearField)));
        panel.add(new JLabel("Other(Yellow): $"
                + logger.totalSpecificTransactionTypePerYear(Transaction.TransactionType.OTHER, yearField)));
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
    // EFFECTS: translates year JTextField into integer
    public void translateYearFields(JTextField year) {
        yearField = Integer.parseInt(year.getText());
    }




}


