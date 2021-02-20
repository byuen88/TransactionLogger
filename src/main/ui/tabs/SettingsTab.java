package ui.tabs;

import model.Logger;
import ui.TransactionLogger;
import ui.actions.LoadAction;
import ui.actions.SaveAction;

import javax.swing.*;
import java.awt.*;

/*
 * Represents the SettingsTab.
 */
public class SettingsTab extends Tab {
    private Logger calendar;
    private TransactionLogger controller;

    // MODIFIES: this
    // EFFECTS: constructs a settings tab for console with buttons
    public SettingsTab(TransactionLogger controller) {
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

    // EFFECTS: creates save and load buttons
    @Override
    public void placeButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JButton save = new JButton(new SaveAction(controller));
        JButton load = new JButton(new LoadAction(controller));

        buttonPanel.add(formatButtonRow(save));
        buttonPanel.add(formatButtonRow(load));

        this.add(buttonPanel);
    }
}
