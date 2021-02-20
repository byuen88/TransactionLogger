package ui.tabs;

import model.Logger;
import ui.TransactionLogger;
import ui.actions.AddTransactionAction;
import ui.actions.CreateTransactionLimitAction;

import javax.swing.*;
import java.awt.*;

/*
 * Represents the HomeTab.
 */
public class HomeTab extends Tab {
    private Logger calendar;
    private TransactionLogger controller;

    // MODIFIES: this
    // EFFECTS: constructs a home tab for console with buttons and a greeting
    public HomeTab(TransactionLogger controller) {
        super(controller);
        this.controller = controller;

        setLayout(new GridLayout(3, 1));

        placeGreeting();
        placeButtons();
    }

    // EFFECTS: sets the calendar
    public void setCalendar(Logger calendar) {
        this.calendar = calendar;
    }

    /*
     * EFFECTS: creates Add and Remove Transaction buttons and Create transaction limit button
     *          that creates a pop-up when clicked
     */
    @Override
    public void placeButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JButton b1 = new JButton(new AddTransactionAction(controller));
        JButton b2 = new JButton(new CreateTransactionLimitAction(controller));

        buttonPanel.add(formatButtonRow(b1));
        buttonPanel.add(formatButtonRow(b2));

        this.add(buttonPanel);
    }




}
