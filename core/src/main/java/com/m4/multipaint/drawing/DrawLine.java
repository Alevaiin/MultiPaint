package com.m4.multipaint.drawing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class DrawLine extends DrawAction
{
    Vector2 startPosition;
    Vector2 endPosition;

    public DrawLine(Color color, int size, int startX, int startY, int endX, int endY)
    {
        super(color, size, startX, startY);
    }

    public DrawLine(Color color, int size, Vector2 startPosition, Vector2 endPosition){
        super(color,size,0,0);
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    @Override
    public void apply(Canvas canvas)
    {
        ShapeRenderer shape = canvas.getShapeRenderer();
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(color);

        // Dibuja la línea gruesa
        shape.rectLine(startPosition, endPosition, size);

        // Dibuja un círculo en cada extremo para suavizar la unión
        shape.circle(startPosition.x, startPosition.y, size / 2f);
        shape.circle(endPosition.x, endPosition.y, size / 2f);

        shape.end();
    }
}
