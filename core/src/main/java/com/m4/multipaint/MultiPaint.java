package com.m4.multipaint;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.*;
import com.m4.multipaint.screens.MainMenuScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MultiPaint extends Game
{
    public SpriteBatch batch;
    public BitmapFont font;
    public Viewport viewport;

    @Override
    public void create() {
        batch = new SpriteBatch();

        font = new BitmapFont();
        viewport = new ScreenViewport();

        this.setScreen(new MainMenuScreen(this));
    }

    public void render() {
        super.render(); // important!
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        this.screen.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        super.resize(width, height);
    }
}
