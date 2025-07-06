package com.m4.multipaint.drawing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
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

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

}
