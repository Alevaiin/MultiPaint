package com.m4.multipaint.drawing;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;

public class Brush {
    private int size;
    private Color color;

    public Brush(int size, Color color) {
        this.size = size;
        this.color = color;
    }

    public void apply(Pixmap pixmap, int x, int y) {
        pixmap.setColor(color);
        pixmap.fillCircle(x, y, size);
    }

    public int getSize() {
        return size;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public void setSize(int size){
        this.size = size;
    }
}
