package com.m4.multipaint.drawing;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;

public class Brush {
    private Color color;
    private int radius;

    public Brush(Color color, int radius) {
        this.color = color;
        this.radius = radius;
    }

    public void apply(Pixmap pixmap, int x, int y) {
        pixmap.setColor(color);
        pixmap.fillCircle(x, y, radius);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
