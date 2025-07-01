package com.m4.multipaint.ui.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class FullScreenButton extends TextButton
{
    private boolean isFullScreen = false;

    public FullScreenButton(Skin skin){
        super("Enter Fullscreen", skin);
        this.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!isFullScreen) {
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                    isFullScreen = true;
                    setText("Exit Fullscreen");
                } else {
                    Gdx.graphics.setWindowedMode(1280, 720);
                    isFullScreen = false;
                    setText("Enter Fullscreen");
                }
            }
        });
    }
}
