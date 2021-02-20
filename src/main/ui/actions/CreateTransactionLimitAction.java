package ui.actions;

import model.Logger;
import ui.TransactionLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;

    /* EFFECTS: represents action to be taken when user wants to create a
       transaction limit
     */
public class CreateTransactionLimitAction extends AbstractAction {
    private TransactionLogger controller;
    private Logger logger;
    private JTextField limit;

    // MODIFIES: this
    // EFFECTS: constructs a CreateTransactionLimitAction
    public CreateTransactionLimitAction(TransactionLogger controller) {
        super("Create Transaction Limit");
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        logger = controller.getLog();
        processCreateTransactionLimit();
    }

    // MODIFIES: this
    // EFFECTS: processes creating a transaction limit
    public void processCreateTransactionLimit() {
        JPanel inputs = new JPanel();
        limit = new JTextField(5);
        inputs.add(new JLabel("Limit: $"));
        inputs.add(limit);

        int result = JOptionPane.showConfirmDialog(null, inputs,
                "Enter Transaction Limit", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            double limitDouble = Double.parseDouble(limit.getText());
            logger.setLimit(limitDouble);
        }
    }
}