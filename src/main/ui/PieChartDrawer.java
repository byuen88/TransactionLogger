package ui;

import model.Pie;
import model.PieSlice;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/*
 * Represents a date.
 */
public class PieChartDrawer extends JComponent {
    Pie pie;
    ArrayList<PieSlice> slices;

    // MODIFIES: this
    // EFFECTS: constructs a PieChartDrawer
    public PieChartDrawer(Pie pie) {
        this.pie = pie;
        slices = pie.getSlices();
    }

    // EFFECTS: processes drawing a pie chart
    public void paint(Graphics g) {
        drawPie((Graphics2D) g, getBounds(), slices);
    }

    // EFFECTS: draws a pie chart
    void drawPie(Graphics2D g, Rectangle area, ArrayList<PieSlice> slices) {
        double total = 0.0D;

        for (PieSlice slice : slices) {
            total += slice.getValue();
        }

        double curValue = 0.0;
        int startAngle;
        for (PieSlice slice : slices) {
            startAngle = (int) (curValue * 360 / total);
            int arcAngle = (int) (slice.getValue() * 360 / total);

            g.setColor(slice.getColor());
            g.fillArc(area.x, area.y, area.width, area.height, startAngle, arcAngle);
            curValue += slice.getValue();
        }
    }
}
