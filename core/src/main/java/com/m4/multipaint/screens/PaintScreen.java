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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.m4.multipaint.ui.UserInterface;
import com.m4.multipaint.ui.buttons.FullScreenButton;


public class PaintScreen implements Screen {
    private final MultiPaint game;
    private final DrawSession session;
    private final User localUser;
    private Vector2 lastDrawPosition;
    private final Stage stage;
    private final Skin skin;
    private TextButton increaseBrushButton;
    private TextButton decreaseBrushButton;
    private TextButton brushToolButton;
    private TextButton lineToolButton;
    private TextButton rectangleToolButton;
    private TextButton circleToolButton;
    private TextButton blueColorButton;
    private TextButton redColorButton;
    private TextButton yellowColorButton;
    private TextButton blackColorButton;
    private TextButton greenColorButton;
    private ServerConnection serverConnection;
    private boolean wasLeftButtonPressed = false;

    // Enum para las herramientas de dibujo

    private Vector2 shapeStartPosition;


    public PaintScreen(MultiPaint game, String userName, ServerConnection serverConnection) {
        this.game = game;

        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        this.serverConnection = serverConnection;

        this.skin = new Skin(Gdx.files.internal("uiskin.json"));

        int canvasWidth = Gdx.graphics.getDisplayMode().width;
        int canvasHeight = Gdx.graphics.getDisplayMode().height;

        Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        this.session = new DrawSession(canvas, serverConnection);

        this.localUser = new User(userName, Color.BLACK, 5);
        this.session.addUser(localUser);

        UserInterface userInterface = new UserInterface(localUser);
        stage.addActor(userInterface);
        //setupUI();

        serverConnection.start(); //Lanzo hilo para escuchar desde el server
    }

    private void setupUI() {
        // Crear tabla para organizar los botones de la barra de herramientas
        Table toolbarTable = new Table();
        toolbarTable.setFillParent(true);
        toolbarTable.top().left();

        // Botón de pantalla completa
        FullScreenButton fullScreenButton = new FullScreenButton(skin);

        // Botón para aumentar grosor del pincel
        increaseBrushButton = new TextButton("+ Brush", skin);
        increaseBrushButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                localUser.setBrushSize(localUser.getBrushSize() + 1);
            }
        });

        // Botón para disminuir grosor del pincel
        decreaseBrushButton = new TextButton("- Brush", skin);
        decreaseBrushButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int currentSize = localUser.getBrushSize();
                if (currentSize > 1) {
                    localUser.setBrushSize(currentSize - 1);
                }
            }
        });

        // Herramientas de dibujo
        brushToolButton = new TextButton("Brush", skin);
        brushToolButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setCurrentTool(DrawingTool.BRUSH);
            }
        });

        lineToolButton = new TextButton("Line", skin);
        lineToolButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setCurrentTool(DrawingTool.LINE);
            }
        });

        rectangleToolButton = new TextButton("Rectangle", skin);
        rectangleToolButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setCurrentTool(DrawingTool.RECTANGLE);
            }
        });

        circleToolButton = new TextButton("Circle", skin);
        circleToolButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setCurrentTool(DrawingTool.CIRCLE);
            }
        });

        // Botones de colores
        blueColorButton = new TextButton("Blue", skin);
        blueColorButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setCurrentColor(Color.BLUE);
            }
        });

        redColorButton = new TextButton("Red", skin);
        redColorButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setCurrentColor(Color.RED);
            }
        });

        yellowColorButton = new TextButton("Yellow", skin);
        yellowColorButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setCurrentColor(Color.YELLOW);
            }
        });

        blackColorButton = new TextButton("Black", skin);
        blackColorButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setCurrentColor(Color.BLACK);
            }
        });

        greenColorButton = new TextButton("Green", skin);
        greenColorButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setCurrentColor(Color.GREEN);
            }
        });

        // Agregar botones a la tabla con espaciado - Primera fila
        toolbarTable.add(fullScreenButton).width(120).height(40).pad(5);
        toolbarTable.add(increaseBrushButton).width(80).height(40).pad(5);
        toolbarTable.add(decreaseBrushButton).width(80).height(40).pad(5);
        toolbarTable.row();

        // Segunda fila - Herramientas de dibujo
        toolbarTable.add(brushToolButton).width(80).height(40).pad(5);
        toolbarTable.add(lineToolButton).width(80).height(40).pad(5);
        toolbarTable.add(rectangleToolButton).width(80).height(40).pad(5);
        toolbarTable.add(circleToolButton).width(80).height(40).pad(5);
        toolbarTable.row();

        // Tercera fila - Colores
        toolbarTable.add(blueColorButton).width(80).height(40).pad(5);
        toolbarTable.add(redColorButton).width(80).height(40).pad(5);
        toolbarTable.add(yellowColorButton).width(80).height(40).pad(5);
        toolbarTable.add(blackColorButton).width(80).height(40).pad(5);
        toolbarTable.add(greenColorButton).width(80).height(40).pad(5);

        // Establecer herramienta inicial y color inicial
        setCurrentTool(DrawingTool.BRUSH);
        setCurrentColor(Color.BLACK);

        stage.addActor(toolbarTable);
    }

    private void setCurrentColor(Color color) {
        localUser.setColor(color);

        // IMPORTANTE: Actualizar el color del Pixmap del canvas

        // Resetear todos los botones de color
        blueColorButton.setColor(Color.WHITE);
        redColorButton.setColor(Color.WHITE);
        yellowColorButton.setColor(Color.WHITE);
        blackColorButton.setColor(Color.WHITE);
        greenColorButton.setColor(Color.WHITE);

        // Resaltar color activo
        if (color.equals(Color.BLUE)) {
            blueColorButton.setColor(Color.LIGHT_GRAY);
        } else if (color.equals(Color.RED)) {
            redColorButton.setColor(Color.LIGHT_GRAY);
        } else if (color.equals(Color.YELLOW)) {
            yellowColorButton.setColor(Color.LIGHT_GRAY);
        } else if (color.equals(Color.BLACK)) {
            blackColorButton.setColor(Color.LIGHT_GRAY);
        } else if (color.equals(Color.GREEN)) {
            greenColorButton.setColor(Color.LIGHT_GRAY);
        }
    }
    private void setCurrentTool(DrawingTool tool) {

        // Resetear todos los botones
        brushToolButton.setColor(Color.WHITE);
        lineToolButton.setColor(Color.WHITE);
        rectangleToolButton.setColor(Color.WHITE);
        circleToolButton.setColor(Color.WHITE);

        // Resaltar herramienta activa
        switch (tool) {
            case BRUSH:
                brushToolButton.setColor(Color.LIGHT_GRAY);
                break;
            case LINE:
                lineToolButton.setColor(Color.LIGHT_GRAY);
                break;
            case RECTANGLE:
                rectangleToolButton.setColor(Color.LIGHT_GRAY);
                break;
            case CIRCLE:
                circleToolButton.setColor(Color.LIGHT_GRAY);
                break;
        }
    }

    private void renderShapePreview() {
        if (shapeStartPosition != null && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            Vector2 currentPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            game.viewport.unproject(currentPos);

            // Usar ShapeRenderer para dibujar la previsualización
            game.shapeRenderer.setProjectionMatrix(game.viewport.getCamera().combined);
            game.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            game.shapeRenderer.setColor(localUser.getColor().r, localUser.getColor().g,
                localUser.getColor().b, 0.5f); // Semi-transparente

            switch (localUser.getCurrentTool()) {
                case LINE:
                    game.shapeRenderer.line(shapeStartPosition.x, shapeStartPosition.y,
                        currentPos.x, currentPos.y);
                    break;
                case RECTANGLE:
                    float minX = Math.min(shapeStartPosition.x, currentPos.x);
                    float maxX = Math.max(shapeStartPosition.x, currentPos.x);
                    float minY = Math.min(shapeStartPosition.y, currentPos.y);
                    float maxY = Math.max(shapeStartPosition.y, currentPos.y);
                    game.shapeRenderer.rect(minX, minY, maxX - minX, maxY - minY);
                    break;
                case CIRCLE:
                    float radius = shapeStartPosition.dst(currentPos);
                    game.shapeRenderer.circle(shapeStartPosition.x, shapeStartPosition.y, radius);
                    break;
            }

            game.shapeRenderer.end();
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT);

        //updateBrushSettings();
        handleInput();

        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        game.batch.begin();
        session.getCanvas().render(game.batch);
        game.batch.end();

        // Renderizar previsualización de forma
        renderShapePreview();

        stage.act(delta);
        stage.draw();
    }

    private void handleInput() {
        boolean isLeftButtonPressed = Gdx.input.isButtonPressed(Input.Buttons.LEFT);

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector2 current = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            game.viewport.project(current);

            if (localUser.getCurrentTool() != DrawingTool.BRUSH) {
                shapeStartPosition = new Vector2(current);
                game.viewport.unproject(shapeStartPosition);
            }
        }

        if (Gdx.input.isTouched() && isLeftButtonPressed) {
            Vector2 current = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            game.viewport.project(current);

            if (localUser.getCurrentTool() == DrawingTool.BRUSH) {
                DrawAction action;
                if (lastDrawPosition != null) {
                    action = new DrawAction(localUser.getColor(),localUser.getBrushSize(), (int) lastDrawPosition.x, (int) lastDrawPosition.y, (int) current.x, (int) current.y);
                } else {
                    action = new DrawAction(localUser.getColor(), localUser.getBrushSize(), (int) current.x, (int) current.y, (int) current.x, (int) current.y);
                }
                session.applyAction(action);
                lastDrawPosition = current;
            }
        }

        // Detectar cuando se suelta el botón
        if (wasLeftButtonPressed && !isLeftButtonPressed && shapeStartPosition != null) {
            Vector2 current = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            game.viewport.project(current);

            createShapeAction(shapeStartPosition, current);
            shapeStartPosition = null;
        }

        if (!Gdx.input.isTouched()) {
            lastDrawPosition = null;
        }

        wasLeftButtonPressed = isLeftButtonPressed;
    }

    private void createShapeAction(Vector2 start, Vector2 end) {
        game.viewport.unproject(start);
        game.viewport.project(end);
        switch (localUser.getCurrentTool()) {
            case LINE:
                DrawAction lineAction = new DrawAction(this.localUser.getColor(), localUser.getBrushSize(), (int) start.x, (int) start.y, (int) end.x, (int) end.y);
                session.applyAction(lineAction);
                break;
            case RECTANGLE:
                drawRectangle(start, end);
                break;
            case CIRCLE:
                drawCircle(start, end);
                break;
        }
    }

    private void drawRectangle(Vector2 start, Vector2 end) {
        int minX = (int) Math.min(start.x, end.x);
        int maxX = (int) Math.max(start.x, end.x);
        int minY = (int) Math.min(start.y, end.y);
        int maxY = (int) Math.max(start.y, end.y);

        // Dibujar los cuatro lados del rectángulo
        DrawAction topSide = new DrawAction(localUser.getColor(), localUser.getBrushSize(), minX, maxY, maxX, maxY);
        DrawAction bottomSide = new DrawAction(localUser.getColor(), localUser.getBrushSize(), minX, minY, maxX, minY);
        DrawAction leftSide = new DrawAction(localUser.getColor(), localUser.getBrushSize(), minX, minY, minX, maxY);
        DrawAction rightSide = new DrawAction(localUser.getColor(), localUser.getBrushSize(), maxX, minY, maxX, maxY);

        session.applyAction(topSide);
        session.applyAction(bottomSide);
        session.applyAction(leftSide);
        session.applyAction(rightSide);

        serverConnection.sendActionToServer(topSide);
        serverConnection.sendActionToServer(bottomSide);
        serverConnection.sendActionToServer(leftSide);
        serverConnection.sendActionToServer(rightSide);
    }

    private void drawCircle(Vector2 start, Vector2 end) {
        int centerX = (int) start.x;
        int centerY = (int) start.y;
        int radius = (int) start.dst(end);

        // Aproximar círculo con múltiples líneas
        int segments = Math.max(16, radius / 2);
        Vector2 prevPoint = null;

        for (int i = 0; i <= segments; i++) {
            double angle = (2 * Math.PI * i) / segments;
            int x = centerX + (int) (radius * Math.cos(angle));
            int y = centerY + (int) (radius * Math.sin(angle));

            Vector2 currentPoint = new Vector2(x, y);

            if (prevPoint != null) {
                DrawAction circleSegment = new DrawAction(localUser.getColor(), localUser.getBrushSize(), (int) prevPoint.x, (int) prevPoint.y, x, y);
                session.applyAction(circleSegment);
                serverConnection.sendActionToServer(circleSegment);
            }

            prevPoint = currentPoint;
        }
    }

    private void updateBrushSettings() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) ) {
            localUser.setBrushSize(localUser.getBrushSize() + 1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            int currentSize = localUser.getBrushSize();
            if (currentSize > 1) {
                localUser.setBrushSize(currentSize - 1);
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            Color current = localUser.getColor();
            Color newColor = current.equals(Color.BLACK) ? Color.WHITE : Color.BLACK;
            setCurrentColor(newColor); // Usar setCurrentColor en lugar de localUser.setColor
        }

        // Atajos de teclado para herramientas
        if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            setCurrentTool(DrawingTool.BRUSH);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            setCurrentTool(DrawingTool.LINE);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            setCurrentTool(DrawingTool.RECTANGLE);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
            setCurrentTool(DrawingTool.CIRCLE);
        }
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, false);
        stage.getViewport().update(width, height, true);

        Canvas oldCanvas = session.getCanvas();
        Canvas newCanvas = new Canvas(width, height);

        // Obtener dimensiones del pixmap del canvas anterior
        int oldWidth = oldCanvas.getPixmap().getWidth();
        int oldHeight = oldCanvas.getPixmap().getHeight();

        // Dibujar el canvas anterior escalado al nuevo tamaño
        newCanvas.getPixmap().drawPixmap(oldCanvas.getPixmap(),
            0, 0, oldWidth, oldHeight,
            0, 0, width, height);

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
