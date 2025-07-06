package com.m4.multipaint.drawing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;

public class Canvas
{
    private final FrameBuffer frameBuffer;
    private final int width;
    private final int height;
    private final ShapeRenderer shapeRenderer;


    public Canvas(int width, int height)
    {
        this.width = width;
        this.height = height;
        frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, width, height));
        clear();
    }

    public void clear() {
        frameBuffer.begin();
        Gdx.gl.glClearColor(1, 1, 1, 1); // Fondo blanco
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        frameBuffer.end();
    }

    private ShapeRenderer fillRectangle(int startX, int startY, int width, int height){
        ShapeRenderer shape = new ShapeRenderer();
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.WHITE);
        shape.rect((float) startX, (float) startY, (float) width, (float) height);
        shape.end();
        return shape;
    }


    public void draw(DrawAction drawAction){
        frameBuffer.begin();
        drawAction.apply(this);
        frameBuffer.end();
    }


    public void render(SpriteBatch batch)
    {
        batch.draw(
            frameBuffer.getColorBufferTexture(),
            0, 0, width, height,
            0, 0, 1, 1
        );
    }

    public void dispose()
    {
        this.frameBuffer.dispose();
        this.shapeRenderer.dispose();
    }

    public void copyFrom(Canvas oldCanvas) {
        frameBuffer.begin();
        SpriteBatch batch = new SpriteBatch();
        batch.begin();
        // Dibuja el contenido del canvas viejo escalado al nuevo tamaño
        batch.draw(
            oldCanvas.frameBuffer.getColorBufferTexture(),
            0, 0, width, height, // destino
            0, 0, 1, 1           // u, v, u2, v2
        );
        batch.end();
        batch.dispose();
        frameBuffer.end();
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

}
