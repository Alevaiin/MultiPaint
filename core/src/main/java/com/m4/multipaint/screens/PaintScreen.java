package com.m4.multipaint.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.m4.multipaint.MultiPaint;

public class PaintScreen implements Screen {

    final MultiPaint game;
    Pixmap pixmap;
    Texture pixmaptex;


    public PaintScreen(final MultiPaint game) {
        this.game = game;
        pixmap = new Pixmap( game.viewport.getScreenWidth(), game.viewport.getScreenHeight(), Pixmap.Format.RGBA8888 );
        pixmap.setColor(Color.WHITE);
        pixmap.fillRectangle(0,0,game.viewport.getScreenWidth(),game.viewport.getScreenHeight());
        pixmaptex = new Texture( pixmap );
        this.pixmap.setColor(Color.BLACK);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        input();
        //logic();
        draw();
    }

    private void input() {
        float speed = 4f;
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isTouched() && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            this.pixmap.fillCircle(Gdx.input.getX(), Gdx.input.getY(),5);
            pixmaptex = new Texture( pixmap );
        }
    }

    private void logic() {
    }

    private void draw() {
        game.viewport.apply();
        game.batch.begin();

        float worldWidth = game.viewport.getWorldWidth();
        float worldHeight = game.viewport.getWorldHeight();

        game.batch.draw(pixmaptex, 0, 0, worldWidth, worldHeight);


        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        pixmap.dispose();
    }
}
