package io.mygdx.soulknight;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** Minimal common interface for everything that lives in the world. */
public abstract class Entity {
    protected float x, y;
    protected boolean alive = true;

    public Entity(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public abstract void update(float delta);
    public abstract void render(SpriteBatch batch);
    public abstract void dispose();

    public boolean isAlive() {
        return alive;
    }

    public float getX() { return x; }
    public float getY() { return y; }
}
