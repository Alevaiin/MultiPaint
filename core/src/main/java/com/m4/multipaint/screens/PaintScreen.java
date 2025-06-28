package com.m4.multipaint.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.m4.multipaint.MultiPaint;
import com.m4.multipaint.drawing.*;
import com.m4.multipaint.networking.ServerConnection;


public class PaintScreen implements Screen {
    private final MultiPaint game;
    private final DrawSession session;
    private final User localUser;
    private Vector2 lastDrawPosition;
    private ServerConnection serverConnection;

    private final String serverIp = "192.168.0.19"; //HARDCODEADO
    private final int serverPort = 6666; //HARDCODEADO
    private final String userName = "Alexis"; //HARDCODEADO

    public PaintScreen(MultiPaint game) {
        this.game = game;

        int canvasWidth = (int) game.viewport.getWorldWidth();
        int canvasHeight = (int) game.viewport.getWorldHeight();

        Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        this.session = new DrawSession(canvas);

        this.localUser = new User(userName, Color.BLACK, 5);
        this.session.addUser(localUser);

        serverConnection = new ServerConnection(this.localUser.getId(),serverIp,serverPort);

        Thread thread = new Thread(() -> serverConnection.connect());
        thread.start();
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
            DrawAction action;
            if (lastDrawPosition != null) {
                action = new DrawAction(localUser, (int) lastDrawPosition.x, (int) lastDrawPosition.y, (int) current.x, (int) current.y);
            } else {
                action = new DrawAction(localUser, (int) current.x, (int) current.y, (int) current.x, (int) current.y);
            }
            session.applyAction(action);
            serverConnection.sendActionToServer(action);
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
