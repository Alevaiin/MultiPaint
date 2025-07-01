package com.m4.multipaint.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.m4.multipaint.drawing.User;
import com.m4.multipaint.ui.buttons.*;

public class UserInterface extends Table
{
    private final Skin skin;
    private final User user;

    public UserInterface(User user){
        this.skin = new Skin(Gdx.files.internal("uiskin.json"));
        this.user = user;

        this.setFillParent(true);
        this.top().left();
        setupUI();
    }

    private void setupUI() {
        // Botón de pantalla completa
        FullScreenButton fullScreenButton = new FullScreenButton(skin);

        // Botón para aumentar grosor del pincel
        IncreaseBrushButton increaseBrushButton = new IncreaseBrushButton(skin, user.getBrush());

        // Botón para disminuir grosor del pincel
        DecreaseBrushButton decreaseBrushButton = new DecreaseBrushButton(skin, user.getBrush());

        // Herramientas de dibujo
        ToolsGrid toolsGrid = new ToolsGrid(user, skin);

        // Botones de colores
        ColorsGrid colorsGrid = new ColorsGrid(user,skin);

        // Agregar botones a la tabla con espaciado - Primera fila
        this.add(fullScreenButton).width(120).height(40).pad(5);
        this.add(increaseBrushButton).width(80).height(40).pad(5);
        this.add(decreaseBrushButton).width(80).height(40).pad(5);
        this.row();

        // Segunda fila - Herramientas de dibujo
        toolsGrid.addToUI(this);
        this.row();

        // Tercera fila - Colores
        colorsGrid.addToUI(this);
    }
}
