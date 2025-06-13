package com.m4.multipaint.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.m4.multipaint.MultiPaint;
import com.m4.multipaint.drawing.Brush;
import com.m4.multipaint.drawing.Canvas;

public class PaintScreen implements Screen {
    final MultiPaint game;
    private final Canvas canvas;
    private Vector2 lastDrawPosition = null;
    private final Brush brush;

    public PaintScreen(final MultiPaint game) {
        this.game = game;
        int width = (int) game.viewport.getWorldWidth();
        int height = (int) game.viewport.getWorldHeight();
        brush = new Brush(Color.BLACK, 1);
        canvas = new Canvas(width, height);
    }

    @Override
    public void render(float delta) {
        handleInput();
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        canvas.render(game.batch);
        game.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.isTouched() && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            Vector2 current = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            game.viewport.project(current);

            if (lastDrawPosition != null) {
                canvas.drawLine(brush, (int) lastDrawPosition.x, (int) lastDrawPosition.y, (int) current.x, (int) current.y);
            } else {
                canvas.drawBrush(brush, (int) current.x, (int) current.y);
            }

            lastDrawPosition = current;
        } else {
            lastDrawPosition = null;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            this.brush.setRadius(this.brush.getRadius() + 1);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            this.brush.setRadius(this.brush.getRadius() - 1);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.B)){
            this.brush.setColor(Color.WHITE);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.N)){
            this.brush.setColor(Color.BLACK);
        }
    }

    @Override public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override public void dispose() {
        canvas.dispose();
    }

    @Override public void show() {}
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}
}
