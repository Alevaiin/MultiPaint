package com.m4.multipaint.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.m4.multipaint.MultiPaint;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class MainMenuScreen implements Screen
{

    final MultiPaint game;
    private Stage stage;
    private Skin skin;

    public MainMenuScreen(MultiPaint game){

        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        this.skin = new Skin(Gdx.files.internal("uiskin.json"));
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label ipLabel = new Label("Server IP", skin);
        TextField ipField = new TextField("192.168.1.37", skin);

        Label portLabel = new Label("Server Port", skin);
        TextField portField = new TextField("6666", skin);

        Label userNameLabel = new Label("User Name", skin);
        TextField userNameField = new TextField("Player 1", skin);

        TextButton startButton = new TextButton("Start Drawing", skin);
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String ip = ipField.getText();
                int port = Integer.parseInt(portField.getText());
                String userName = userNameField.getText();
                game.setScreen(new PaintScreen(game, ip, port, userName));
                dispose();
            }
        });

        table.add(ipLabel).left();
        table.add(ipField).width(200).padBottom(10).row();

        table.add(portLabel).left();
        table.add(portField).width(200).padBottom(10).row();

        table.add(userNameLabel).left();
        table.add(userNameField).width(200).padBottom(10).row();

        table.add(startButton).colspan(2).center();
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

        game.font.getData().setScale(1.5f);

        GlyphLayout layout1 = new GlyphLayout(game.font, "Welcome to Drop!!!");
        GlyphLayout layout2 = new GlyphLayout(game.font, "Enter your details below to start");

        float x1 = (game.viewport.getWorldWidth() - layout1.width) / 2;
        float x2 = (game.viewport.getWorldWidth() - layout2.width) / 2;

        float baseY = (game.viewport.getWorldHeight() * 0.75f);
        float spacing = layout1.height * 2;

        game.batch.begin();
        game.font.draw(game.batch, "Welcome to Drop!!!", x1, baseY);
        game.font.draw(game.batch, "Enter your details below to start", x2, baseY - spacing);
        game.batch.end();

        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height)
    {
        game.viewport.update(width, height, true);
        stage.getViewport().update(width, height, true);
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
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
