package com.m4.multipaint.drawing;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;

public class Canvas {
    private Pixmap pixmap;
    private Texture texture;
    private boolean dirty = true;

    List<DrawAction> drawHistory;

    public Canvas(int width, int height) {

        pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        pixmap.setColor(Color.BLACK);
        texture = new Texture(pixmap);
    }

    public void drawBrush(Brush brush, int x, int y) {
        brush.apply(pixmap, x, y);
        dirty = true;
    }

    public void drawLine(Brush brush, int startX, int startY, int endX, int endY) {
        int deltaX = Math.abs(endX - startX);
        int deltaY = Math.abs(endY - startY);
        int stepX = startX < endX ? 1 : -1;
        int stepY = startY < endY ? 1 : -1;
        int error = deltaX - deltaY;

        while (true) {
            brush.apply(pixmap, startX, startY);
            if (startX == endX && startY == endY) break;

            int doubleError = 2 * error;

            if (doubleError > -deltaY) {
                error -= deltaY;
                startX += stepX;
            }

            if (doubleError < deltaX) {
                error += deltaX;
                startY += stepY;
            }
        }

        dirty = true;
    }


    private void refreshTexture() {
        if (texture != null) texture.dispose();
        texture = new Texture(pixmap);
    }

    public void render(SpriteBatch batch) {
        if (dirty) {
            if (texture != null) texture.dispose();
            texture = new Texture(pixmap);
            dirty = false;
        }

        batch.draw(texture, 0, 0);
    }

    public void dispose() {
        pixmap.dispose();
        texture.dispose();
    }

    public void applyAction(DrawAction action) {
        action.apply(this);
        drawHistory.add(action);
    }

    public Pixmap getPixmap() {
        return pixmap;
    }

}
