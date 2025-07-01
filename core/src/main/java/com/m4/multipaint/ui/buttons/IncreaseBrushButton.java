package com.m4.multipaint.ui.buttons;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.m4.multipaint.drawing.Brush;

public class IncreaseBrushButton extends TextButton
{

    public IncreaseBrushButton(Skin skin, Brush brush){
        super("+ Brush", skin);
        this.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                brush.setSize(brush.getSize() + 1);
            }
        });

    }
}
