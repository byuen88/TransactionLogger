package model;

import java.awt.*;

/*
 * Represents a PieSlice in a Pie.
 */
public class PieSlice {
    double value;
    Color color;

    // MODIFIES: this
    // EFFECTS: constructs a PieSlice
    public PieSlice(double value, Color color) {
        this.value = value;
        this.color = color;
    }

    // getters
    public double getValue() {
        return value;
    }

    public Color getColor() {
        return color;
    }
}
