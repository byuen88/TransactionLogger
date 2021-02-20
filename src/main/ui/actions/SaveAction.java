package ui.actions;

import model.Logger;
import persistence.JsonWriter;
import ui.TransactionLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;

// EFFECTS: represents action to be taken when user wants to save to file
public class SaveAction extends AbstractAction {
    private TransactionLogger controller;
    private Logger logger;
    private JsonWriter jsonWriter;

    // MODIFIES: this
    // EFFECTS: constructs a SaveAction
    public SaveAction(TransactionLogger controller) {
        super("Save");
        this.controller = controller;
        jsonWriter = controller.getJsonWriter();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        logger = controller.getLog();
        try {
            jsonWriter.open();
            jsonWriter.write(logger);
            JOptionPane.showMessageDialog(null, "Saved to: " + controller.getJsonStore());
            jsonWriter.close();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Unable to write to file: " + controller.getJsonStore());
        }
    }
}
