package io.mygdx.soulknight.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.mygdx.soulknight.utils.Assets;

/**
 * Simple projectile that moves in a straight line and disappears after a timeout.
 */
public class Bullet extends Entity {

    // ----- TUNABLE PARAMETERS -----------------------------------------
    private final float speed;   // set per weapon
    private final int   damage;  // set per weapon
    private final float maxLife; // seconds before despawn
    // -----------------------------------------------------------------
    private final Texture texture; // placeholder for bullet sprite
    private final Vector2 velocity;
    private float life = 0f;

    public Bullet(float startX, float startY,
                  float angleDeg, float speed,
                  int damage, float maxLife) {
        super(startX, startY);
        this.speed   = speed;
        this.damage  = damage;
        this.maxLife = maxLife;

        // INSERT_IMAGE_HERE: Bullet Sprite
        texture = Assets.bulletTexture;

        float rad = (float) Math.toRadians(angleDeg);
        velocity = new Vector2((float) Math.cos(rad),
                               (float) Math.sin(rad)).nor().scl(this.speed);
    }

    @Override
    public void update(float delta) {
        x += velocity.x * delta;
        y += velocity.y * delta;
        life += delta;
        if (life > maxLife) alive = false;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, x - texture.getWidth() / 2f,
                         y - texture.getHeight() / 2f);
    }

    @Override
    public void dispose() {
        // textures managed in Assets
    }

    public int getDamage() {
        return damage;
    }

    /** Simple circle hit test – adjust radius if needed. */
    public boolean collidesWith(Enemy enemy) {
        float bulletRadius = texture.getWidth() / 2f;
        float enemyRadius = 16f; // Giả sử quái vật rộng 32px, bán kính là 16
        float collisionDist = bulletRadius + enemyRadius;

        float dx = x - enemy.getX();
        float dy = y - enemy.getY();
        return (dx * dx + dy * dy) <= (collisionDist * collisionDist);
    }

    /** Mark bullet as destroyed (e.g., after hitting an enemy). */
    public void destroy() {
        alive = false;
    }
}
