package com.m4.multipaint.networking;

import com.badlogic.gdx.graphics.Color;
import com.m4.multipaint.drawing.DrawAction;
import com.m4.multipaint.drawing.DrawLine;

public class MessageParser
{

    public static DrawAction parseDrawAction(String message)
    {
        String[] fields = message.split("\\|");
        Color color = Color.valueOf(fields[0]);
        int size = Integer.parseInt(fields[1]);
        int startX = Integer.parseInt(fields[2]);
        int startY = Integer.parseInt(fields[3]);
        int endX = Integer.parseInt(fields[4]);
        int endY = Integer.parseInt(fields[5]);
        return new DrawLine(color, size, startX, startY, endX, endY);
    }
}
