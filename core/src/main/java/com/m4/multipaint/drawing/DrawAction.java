package com.m4.multipaint.drawing;


import com.badlogic.gdx.graphics.Color;

public class DrawAction {
    private final User user;
    private final int startX;
    private final int startY;
    private final int endX;
    private final int endY;
    private final Color color;
    private final int size;

    @Deprecated //No tiene sentido q identifiquemos de que usuario vino la accion, lo depreco para no romper lo q ya esta hecho
    public DrawAction(User user, int startX, int startY, int endX, int endY) {
        this.user = user;
        this.color = user.getColor();
        this.size = user.getBrushSize();
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public DrawAction(Color color, int size, int startX, int startY, int endX, int endY) {
        this.size = size;
        this.user = null;
        this.color = color;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public void apply(Canvas canvas) {
        canvas.drawLine(color, size, startX, startY, endX, endY);
    }

    @Override
    public String toString()
    {
        return String.format("%s|%d|%s|%s|%s|%s",Color.argb8888(this.color),this.size,startX,startY,endX,endY);
    }
}
