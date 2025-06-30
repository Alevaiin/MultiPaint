package com.m4.multipaint.drawing;

import com.badlogic.gdx.Gdx;
import com.m4.multipaint.networking.ServerConnection;

import java.util.HashMap;
import java.util.Map;

public class DrawSession {
    private Canvas canvas;
    private final Map<String, User> users;

    public DrawSession(Canvas canvas, ServerConnection serverConnection) {
        this.canvas = canvas;
        this.users = new HashMap<>();
        if(serverConnection != null){
            serverConnection.setDrawSession(this);
        }
    }

    public void addUser(User user) {
        users.put(user.getId(), user);
    }

    public void applyAction(DrawAction action) {
        Gdx.app.log("DRAWSESSION", "Applying action: " + action);
        action.apply(canvas);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public User getUser(String id) {
        return users.get(id);
    }

    public void setCanvas(Canvas newCanvas) {
        this.canvas = newCanvas;
    }
}
