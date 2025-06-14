package com.m4.multipaint.drawing;

public class DrawAction {
    private final User user;
    private final int startX;
    private final int startY;
    private final int endX;
    private final int endY;

    public DrawAction(User user, int startX, int startY, int endX, int endY) {
        this.user = user;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public void apply(Canvas canvas) {
        canvas.drawLine(user.getBrush(), startX, startY, endX, endY);
    }
}
