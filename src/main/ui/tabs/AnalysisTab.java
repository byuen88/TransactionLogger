package ui.tabs;

import model.Logger;
import ui.TransactionLogger;
import ui.actions.CalculateAmountPerTypeAction;
import ui.actions.CalculateDisplayAction;
import ui.actions.CalculateTotalAction;

import javax.swing.*;
import java.awt.*;

/*
 * Represents the AnalysisTab.
 */
public class AnalysisTab extends Tab {
    private TransactionLogger controller;
    private Logger calendar;

    // MODIFIES: this
    // EFFECTS: constructs an analysis tab for console with buttons and a greeting
    public AnalysisTab(TransactionLogger controller) {
        super(controller);
        this.controller = controller;

        setLayout(new GridLayout(2, 1));

        placeGreeting();
        placeButtons();
    }

    // EFFECTS: sets the calendar
    public void setCalendar(Logger calendar) {
        this.calendar = calendar;
    }

    // EFFECTS: creates JComboBox and Buttons that creates a pop-up when clicked
    @Override
    public void placeButtons() {
        String[] dayMonthYear = {"Day", "Month", "Year"};
        String[] dayMonthYearAll = {"Day", "Month", "Year", "All"};
        JPanel buttonPanel = new JPanel();
        JComboBox<String> displayTransaction = new JComboBox<>(dayMonthYearAll);
        JComboBox<String> totalAmount = new JComboBox<>(dayMonthYear);
        JButton calculateDisplay = new JButton(new CalculateDisplayAction(controller,
                displayTransaction));
        JButton calculateTotal = new JButton(new CalculateTotalAction(controller,
                totalAmount));
        JButton amountType = new JButton(new CalculateAmountPerTypeAction(controller));

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(formatLabel(new JLabel("Display all transactions in a"), displayTransaction, calculateDisplay));
        buttonPanel.add(formatLabel(new JLabel("Calculate total amount spent in a"), totalAmount, calculateTotal));
        buttonPanel.add(formatButtonRow(amountType));

        this.add(buttonPanel);
    }
}
