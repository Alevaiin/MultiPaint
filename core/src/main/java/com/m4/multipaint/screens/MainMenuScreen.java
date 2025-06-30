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
import com.m4.multipaint.networking.ServerConnection;


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
                String portText = portField.getText();
                String userName = userNameField.getText();

                if (!isValidIp(ip)) {
                    showErrorDialog("La direccion IP no es válida.");
                    return;
                }

                if (!isValidPort(portText)) {
                    showErrorDialog("El puerto debe ser un numero entre 1 y 65535.");
                    return;
                }

                int port = Integer.parseInt(portText);

                new Thread(() -> {
                    try {
                        startButton.setDisabled(true);
                        startButton.setText("Connecting");
                        ServerConnection testConnection = new ServerConnection(userName, ip, port);
                        testConnection.connect();
                        String response = testConnection.readNextMessage();
                        if (!response.equals("OK")){
                            Gdx.app.postRunnable(() -> {
                                showErrorDialog(response);
                                startButton.setDisabled(false);
                                startButton.setText("Start Drawing");
                            });
                        }else if (testConnection.isConnected()) {
                            Gdx.app.postRunnable(() -> {
                                game.setScreen(new PaintScreen(game, userName, testConnection));
                                dispose();
                            });
                        } else {
                            Gdx.app.postRunnable(() -> {
                                showErrorDialog("No se pudo conectar al servidor, revise los datos de conexion.");
                                startButton.setDisabled(false);
                                startButton.setText("Start Drawing");
                            });
                        }
                    } catch (Exception e) {
                        Gdx.app.postRunnable(() -> {
                            showErrorDialog("No se pudo conectar al servidor, revise los datos de conexion.");
                            startButton.setDisabled(false);
                            startButton.setText("Start Drawing");
                        });
                    }
                }).start();
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

        GlyphLayout layout1 = new GlyphLayout(game.font, "Welcome to MultiPaint!!!");
        GlyphLayout layout2 = new GlyphLayout(game.font, "Enter your details below to start");

        float x1 = (game.viewport.getWorldWidth() - layout1.width) / 2;
        float x2 = (game.viewport.getWorldWidth() - layout2.width) / 2;

        float baseY = (game.viewport.getWorldHeight() * 0.75f);
        float spacing = layout1.height * 2;

        game.batch.begin();
        game.font.draw(game.batch, "Welcome to MultiPaint!!!", x1, baseY);
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

    private boolean isValidIp(String ip) {
        //https://stackoverflow.com/questions/5284147/validating-ipv4-addresses-with-regexp
        return ip.matches("^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$");
    }

    private boolean isValidPort(String portText) {
        try {
            int port = Integer.parseInt(portText);
            return port >= 1 && port <= 65535;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private void showErrorDialog(String message) {
        Dialog dialog = new Dialog("Error", skin);
        dialog.text(message);
        dialog.button("OK");
        dialog.show(stage);
    }
}
