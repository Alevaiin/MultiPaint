package com.m4.multipaint.ui.buttons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.m4.multipaint.drawing.User;
import com.m4.multipaint.ui.UserInterface;

public class ColorsGrid
{

    ColorButton blueButton;
    ColorButton redButton;
    ColorButton yellowButton;
    ColorButton blackButton;
    ColorButton greenButton;
    ColorButton whiteButton;

    public ColorsGrid(User user, Skin skin){
        blueButton = new ColorButton(Color.BLUE, "Blue", user, skin, this);
        redButton = new ColorButton(Color.RED, "Red", user, skin, this);
        yellowButton = new ColorButton(Color.YELLOW, "Yellow", user, skin, this);
        blackButton = new ColorButton(Color.BLACK, "Black", user, skin, this);
        greenButton = new ColorButton(Color.GREEN, "Green", user, skin, this);
        whiteButton = new ColorButton(Color.WHITE, "White", user, skin, this);

        setSelectedButton(blackButton);
    }

    public void setSelectedButton(ColorButton selectedColor){
        blueButton.setColor(blueButton == selectedColor? Color.LIGHT_GRAY:Color.WHITE);
        redButton.setColor(redButton == selectedColor? Color.LIGHT_GRAY:Color.WHITE);
        yellowButton.setColor(yellowButton == selectedColor? Color.LIGHT_GRAY:Color.WHITE);
        blackButton.setColor(blackButton == selectedColor? Color.LIGHT_GRAY:Color.WHITE);
        greenButton.setColor(greenButton == selectedColor? Color.LIGHT_GRAY:Color.WHITE);
        whiteButton.setColor(whiteButton == selectedColor? Color.LIGHT_GRAY:Color.WHITE);
    }

    public void addToUI(UserInterface ui){
        ui.add(blueButton).width(80).height(40).pad(5);
        ui.add(redButton).width(80).height(40).pad(5);
        ui.add(yellowButton).width(80).height(40).pad(5);
        ui.add(blackButton).width(80).height(40).pad(5);
        ui.add(greenButton).width(80).height(40).pad(5);
        //ui.add(whiteButton).width(80).height(40).pad(5);
    }
}
