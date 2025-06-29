package com.m4.multipaint.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.m4.multipaint.MultiPaint;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;


public class MainMenuScreen implements Screen
{

    final MultiPaint game;

    public MainMenuScreen(MultiPaint game){
        this.game = game;
    }
    @Override
    public void show()
    {

    }

    @Override
    public void render(float delta)
    {
        ScreenUtils.clear(Color.BLACK);

        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        game.font.getData().setScale(2f);

        GlyphLayout layout1 = new GlyphLayout(game.font, "Welcome to Drop!!!");
        GlyphLayout layout2 = new GlyphLayout(game.font, "Tap anywhere to begin!");

        float x1 = (game.viewport.getWorldWidth() - layout1.width) / 2;
        float x2 = (game.viewport.getWorldWidth() - layout2.width) / 2;

        float baseY = (game.viewport.getWorldHeight() / 2) + layout1.height;
        float spacing = layout1.height * 2;

        game.batch.begin();
        game.font.draw(game.batch, "Welcome to Drop!!!", x1, baseY);
        game.font.draw(game.batch, "Tap anywhere to begin!", x2, baseY - spacing);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new PaintScreen(game));
            dispose();
        }

    }

    @Override
    public void resize(int width, int height)
    {
        game.viewport.update(width, height, true);
    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void dispose()
    {

    }
}
