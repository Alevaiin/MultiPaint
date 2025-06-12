package com.m4.multipaint.drawing;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Canvas {
    private Pixmap pixmap;
    private Texture texture;
    private final int width;
    private final int height;

    public Canvas(int width, int height) {
        this.width = width;
        this.height = height;

        pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        pixmap.setColor(Color.BLACK);
        texture = new Texture(pixmap);
    }

    public void drawBrush(Brush brush, int x, int y) {
        brush.apply(pixmap, x, y);
        refreshTexture();
    }

    public void drawLine(Brush brush, int x0, int y0, int x1, int y1) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx - dy;

        while (true) {
            brush.apply(pixmap, x0, y0);
            if (x0 == x1 && y0 == y1) break;
            int e2 = 2 * err;
            if (e2 > -dy) { err -= dy; x0 += sx; }
            if (e2 < dx) { err += dx; y0 += sy; }
        }

        refreshTexture();
    }

    private void refreshTexture() {
        if (texture != null) texture.dispose();
        texture = new Texture(pixmap);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, 0, 0);
    }

    public void dispose() {
        pixmap.dispose();
        texture.dispose();
    }
}
