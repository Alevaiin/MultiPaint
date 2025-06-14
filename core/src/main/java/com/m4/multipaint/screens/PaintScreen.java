package com.m4.multipaint.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.m4.multipaint.MultiPaint;
import com.m4.multipaint.drawing.*;

public class PaintScreen implements Screen {
    private final MultiPaint game;
    private final DrawSession session;
    private final User localUser;
    private Vector2 lastDrawPosition;

    public PaintScreen(MultiPaint game) {
        this.game = game;

        int canvasWidth = (int) game.viewport.getWorldWidth();
        int canvasHeight = (int) game.viewport.getWorldHeight();

        Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        this.session = new DrawSession(canvas);

        this.localUser = new User("local", Color.BLACK, 5);
        this.session.addUser(localUser);
    }

    @Override
    public void render(float delta) {
        updateBrushSettings();
        handleInput();

        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        game.batch.begin();
        session.getCanvas().render(game.batch);
        game.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.isTouched() && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            Vector2 current = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            game.viewport.project(current);

            if (lastDrawPosition != null) {
                DrawAction action = new DrawAction(localUser, (int) lastDrawPosition.x, (int) lastDrawPosition.y, (int) current.x, (int) current.y);
                session.applyAction(action);
            } else {
                session.getCanvas().drawBrush(localUser.getBrush(), (int) current.x, (int) current.y);
            }
            lastDrawPosition = current;
        } else {
            lastDrawPosition = null;
        }
    }

    private void updateBrushSettings() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) ) {
            localUser.setBrushSize(localUser.getBrushSize() + 1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            localUser.setBrushSize(localUser.getBrushSize() - 1);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            Color current = localUser.getColor();
            Color newColor = current.equals(Color.BLACK) ? Color.WHITE : Color.BLACK;
            localUser.setColor(newColor);
        }
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override public void show() {}
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}

    @Override
    public void dispose() {
        session.getCanvas().dispose();
    }
}
