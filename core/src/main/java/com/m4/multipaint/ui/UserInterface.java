package com.m4.multipaint.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.m4.multipaint.Constants;
import com.m4.multipaint.drawing.User;
import com.m4.multipaint.ui.buttons.*;

public class UserInterface extends Table implements Disposable
{
    private final Skin skin;
    private final User user;

    public UserInterface(User user)
    {
        this.skin = new Skin(Gdx.files.internal(Constants.UI_SKIN_FILE_NAME));
        this.user = user;

        this.setFillParent(true);
        this.top().left();
        setupUI();
    }

    private void setupUI()
    {
        // Botón de pantalla completa
        FullScreenButton fullScreenButton = new FullScreenButton(skin);

        // Botón para aumentar grosor del pincel
        IncreaseBrushButton increaseBrushButton = new IncreaseBrushButton(skin, user);

        // Botón para disminuir grosor del pincel
        DecreaseBrushButton decreaseBrushButton = new DecreaseBrushButton(skin, user);

        // Herramientas de dibujo
        ToolsGrid toolsGrid = new ToolsGrid(user, skin);

        // Botones de colores
        ColorsGrid colorsGrid = new ColorsGrid(user, skin);

        // Agregar botones a la tabla con espaciado - Primera fila
        this.add(fullScreenButton).width(Constants.BUTTON_WIDTH_BIG).height(Constants.BUTTON_HEIGHT_NORMAL).pad(Constants.BUTTON_PADDING);
        this.add(increaseBrushButton).width(Constants.BUTTON_WIDTH_NORMAL).height(Constants.BUTTON_HEIGHT_NORMAL).pad(Constants.BUTTON_PADDING);
        this.add(decreaseBrushButton).width(Constants.BUTTON_WIDTH_NORMAL).height(Constants.BUTTON_HEIGHT_NORMAL).pad(Constants.BUTTON_PADDING);
        this.row();

        // Segunda fila - Herramientas de dibujo
        toolsGrid.addToUI(this);
        this.row();

        // Tercera fila - Colores
        colorsGrid.addToUI(this);
    }

    @Override
    public void dispose()
    {
        skin.dispose();
    }
}
