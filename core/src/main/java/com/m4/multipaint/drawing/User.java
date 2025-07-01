package com.m4.multipaint.drawing;

import com.badlogic.gdx.graphics.Color;

public class User
{
    private final String id;
    private final Brush brush;
    private DrawingTool currentTool;

    public User(String id, Color color, int size) {
        this.id = id;
        this.brush = new Brush(size, color);
        this.currentTool = DrawingTool.BRUSH;
    }

    public String getId() {
        return id;
    }

    public Brush getBrush() {
        return brush;
    }

    public Color getColor() {
        return this.brush.getColor();
    }

    public void setColor(Color color) {
        this.brush.setColor(color);
    }

    public int getBrushSize() {
        return this.brush.getSize();
    }

    public void setBrushSize(int brushSize) {
        if( brushSize < 1)
            return;
        this.brush.setSize(brushSize);
    }

    public void setCurrentTool(DrawingTool drawingTool){
        this.currentTool = drawingTool;
    }

    public DrawingTool getCurrentTool(){
        return this.currentTool;
    }

}
