package ui.tabs;

import ui.TransactionLogger;

import javax.swing.*;
import java.awt.*;

/*
 * Represents an abstract Tab class.
 */
public abstract class Tab extends JPanel {

    private TransactionLogger controller;
    private static final String INIT_GREETING = "What would you like to do?";
    private JLabel greeting;

    // REQUIRES: BudgetingCalendar controller that holds this tab
    public Tab(TransactionLogger controller) {
        this.controller = controller;
    }

    // MODIFIES: this
    // EFFECTS: places greeting
    public void placeGreeting() {
        greeting = new JLabel(INIT_GREETING, JLabel.CENTER);
        greeting.setSize(WIDTH, HEIGHT / 3);
        this.add(greeting);
    }

    // EFFECTS: creates buttons and places them
    public abstract void placeButtons();


    // EFFECTS: creates and returns row with button included
    public JPanel formatButtonRow(JButton b) {
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.setPreferredSize(new Dimension(100, 50));
        p.add(b);

        return p;
    }

    // MODIFIES: c
    // EFFECTS: formats and returns row with JLabel and JComboBox
    public JPanel formatLabel(JLabel l, JComboBox<String> c, JButton b) {
        JPanel outerP = new JPanel();
        JPanel p = new JPanel();

        outerP.setLayout(new BorderLayout());
        c.setPreferredSize(new Dimension(100, 25));
        p.setLayout(new FlowLayout());
        p.add(l);
        p.add(c);
        p.add(b);

        outerP.add(p, BorderLayout.NORTH);

        return outerP;
    }

    // EFFECTS: returns the BudgetingCalendar controller for this tab
    public TransactionLogger getController() {
        return controller;
    }
}
