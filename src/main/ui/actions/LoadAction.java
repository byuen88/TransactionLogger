package ui.actions;

import model.Logger;
import persistence.JsonReader;
import ui.TransactionLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

/*
 * Represents a LoadAction.
 */
public class LoadAction extends AbstractAction {
    private TransactionLogger controller;
    private JsonReader jsonReader;

    // MODIFIES: this
    // EFFECTS: constructs a LoadAction
    public LoadAction(TransactionLogger controller) {
        super("Load");
        this.controller = controller;
        jsonReader = controller.getJsonReader();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Logger logger = jsonReader.read();
            controller.updateLog(logger);
            JOptionPane.showMessageDialog(null, "Loaded from: " + controller.getJsonStore());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Unable to read from file: " + controller.getJsonStore());
        }
    }
}
