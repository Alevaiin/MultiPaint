package com.m4.multipaint.drawing;

import java.util.HashMap;
import java.util.Map;

public class DrawSession {
    private final Canvas canvas;
    private final Map<String, User> users;

    public DrawSession(Canvas canvas) {
        this.canvas = canvas;
        this.users = new HashMap<>();
    }

    public void addUser(User user) {
        users.put(user.getId(), user);
    }

    public void applyAction(DrawAction action) {
        action.apply(canvas);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public User getUser(String id) {
        return users.get(id);
    }
}
