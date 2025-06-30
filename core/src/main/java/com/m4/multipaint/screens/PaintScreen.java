package com.m4.multipaint.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.m4.multipaint.MultiPaint;
import com.m4.multipaint.drawing.*;
import com.m4.multipaint.networking.ServerConnection;


import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.IOException;

public class PaintScreen implements Screen {
    private final MultiPaint game;
    private final DrawSession session;
    private final User localUser;
    private Vector2 lastDrawPosition;
    private final Stage stage;
    private final Skin skin;
    private boolean isFullScreen = false;
    private final TextButton fullScreenButton;
    private final ServerConnection serverConnection;


    public PaintScreen(MultiPaint game, String userName, ServerConnection serverConnection) {
        this.game = game;

        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        this.serverConnection = serverConnection;
        serverConnection.start(); //Lanzo hilo para escuchar desde el server

        this.skin = new Skin(Gdx.files.internal("uiskin.json"));

        fullScreenButton = new TextButton("Enter Fullscreen", skin);
        fullScreenButton.setPosition(10, 10);
        fullScreenButton.setSize(200, 50);

        fullScreenButton.addListener(new ChangeListener() {
             @Override
             public void changed(ChangeEvent event, Actor actor) {
                 if (!isFullScreen) {
                     Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                     isFullScreen = true;
                     fullScreenButton.setText("Exit Fullscreen");
                 } else {
                     Gdx.graphics.setWindowedMode(1280, 720);
                     isFullScreen = false;
                     fullScreenButton.setText("Enter Fullscreen");
                 }
             }
         });

        stage.addActor(fullScreenButton);


        int canvasWidth = Gdx.graphics.getDisplayMode().width;
        int canvasHeight = Gdx.graphics.getDisplayMode().height;

        Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        this.session = new DrawSession(canvas);

        this.localUser = new User(userName, Color.BLACK, 5);
        this.session.addUser(localUser);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT);

        updateBrushSettings();
        handleInput();

        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        game.batch.begin();
        session.getCanvas().render(game.batch);
        game.batch.end();

        stage.act(delta);
        stage.draw();
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
        game.viewport.update(width, height, false);
        stage.getViewport().update(width, height, true);

        Canvas oldCanvas = session.getCanvas();
        Canvas newCanvas = new Canvas(width, height);

        newCanvas.getPixmap().drawPixmap(oldCanvas.getPixmap(), 0, 0);
        session.setCanvas(newCanvas);

        oldCanvas.dispose();
    }

    @Override public void show() {}
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}

    @Override
    public void dispose() {
        session.getCanvas().dispose();
        stage.dispose();
        skin.dispose();
        serverConnection.disconnect();
    }
}
