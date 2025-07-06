package com.m4.multipaint.drawing;


import com.badlogic.gdx.graphics.Color;

public abstract class DrawAction
{
    protected final int startX;
    protected final int startY;
    protected final Color color;
    protected final int size;


    public DrawAction(Color color, int size, int startX, int startY)
    {
        this.size = size;
        this.color = color;
        this.startX = startX;
        this.startY = startY;
    }

    public abstract void apply(Canvas canvas);

    @Override
    public String toString()
    {
        return String.format("%s|%s|%s|%s", this.color, this.size, startX, startY);
    }
}
