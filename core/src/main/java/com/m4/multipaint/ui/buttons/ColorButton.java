package com.m4.multipaint.ui.buttons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.m4.multipaint.drawing.User;


public class ColorButton extends TextButton
{
    public ColorButton(Color color, String text, User user, Skin skin, ColorsGrid grid)
    {
        super(text, skin);
        ColorButton thisButton = this;
        this.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                user.setColor(color);
                grid.setSelectedButton(thisButton);
            }
        });
    }
}
