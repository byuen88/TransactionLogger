package model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/*
 * Represents a pie chart.
 */
public class Pie extends JComponent {
    ArrayList<PieSlice> slices;
    Logger logger;
    int year;

    // MODIFIES: this
    // EFFECTS: constructs a Pie
    public Pie(Logger calendar, int year) {
        slices = new ArrayList<>();
        this.logger = calendar;
        this.year = year;
        int count = 1;

        for (Transaction.TransactionType tt : Transaction.TransactionType.values()) {
            slices.add(new PieSlice(calendar.totalSpecificTransactionTypePerYearPercentage(tt, year), mapColor(count)));
            count++;
        }
    }

    // getter
    public ArrayList<PieSlice> getSlices() {
        return slices;
    }

    // EFFECTS: maps the color to its specific integer
    public Color  mapColor(int i) {
        if (i == 1) {
            return new Color(66,133,244);
        } else if (i == 2) {
            return new Color(219,68,55);
        } else if (i == 3) {
            return new Color(15,157,88);
        } else if (i == 4) {
            return new Color(198, 110, 236);
        } else {
            return new Color(244,180, 0);
        }
    }
}
