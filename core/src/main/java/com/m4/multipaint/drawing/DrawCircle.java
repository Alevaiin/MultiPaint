package com.m4.multipaint.drawing;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class DrawCircle extends DrawAction
{

    Vector2 center;
    private final float radius;

    public DrawCircle(Color color, int size, Vector2 center, float radius){
        super(color,size,0,0);
        this.center = center;
        this.radius = radius;
    }

    @Override
    public void apply(Canvas canvas)
    {
        ShapeRenderer shape = canvas.getShapeRenderer();
        shape.begin(ShapeRenderer.ShapeType.Filled);

        int segments = 100;
        float angleStep = 360f / segments;
        float outerR = radius;
        float innerR = radius - size;

        shape.setColor(color);

        for (int i = 0; i < segments; i++) {
            float angle1 = (float)Math.toRadians(i * angleStep);
            float angle2 = (float)Math.toRadians((i + 1) * angleStep);

            float x1o = center.x + outerR * (float)Math.cos(angle1);
            float y1o = center.y + outerR * (float)Math.sin(angle1);
            float x2o = center.x + outerR * (float)Math.cos(angle2);
            float y2o = center.y + outerR * (float)Math.sin(angle2);

            float x1i = center.x + innerR * (float)Math.cos(angle1);
            float y1i = center.y + innerR * (float)Math.sin(angle1);
            float x2i = center.x + innerR * (float)Math.cos(angle2);
            float y2i = center.y + innerR * (float)Math.sin(angle2);

            shape.triangle(x1o, y1o, x2o, y2o, x1i, y1i);
            shape.triangle(x2o, y2o, x2i, y2i, x1i, y1i);
        }

        shape.end();
    }
}
