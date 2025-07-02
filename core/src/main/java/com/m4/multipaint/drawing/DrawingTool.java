package com.m4.multipaint.drawing;

public enum DrawingTool
{

    BRUSH("Brush", 1),
    LINE("Line", 2),
    RECTANGLE("Rectangle", 3),
    CIRCLE("Circle", 4);

    public final String label;
    public final int value;

    private DrawingTool(String label, int value)
    {
        this.label = label;
        this.value = value;
    }
}
