package io.mygdx.soulknight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Very simple AI: constantly moves toward the player.
 */
public class Enemy extends Entity {

    // ----- TUNABLE PARAMETERS -----------------------------------------
    /** Movement speed (slower than the player). */
    private static final float SPEED = 120f;   // <-- tweak for difficulty

    /** Enemy health. */
    private static final int MAX_HEALTH = 30;  // <-- adjust as needed

    /** Damage dealt to player on contact (not used yet). */
    private static final int CONTACT_DAMAGE = 10;
    // -----------------------------------------------------------------
    private final Texture texture; // placeholder for enemy sprite
    private int health = MAX_HEALTH;

    public Enemy(float x, float y) {
        super(x, y);
        // INSERT_IMAGE_HERE: Enemy Sprite
        texture = Assets.enemyTexture;
    }

    @Override
    public void update(float delta) {
        // Logic is handled externally via chase(...).
    }

    /** Called each tick with reference to the player. */
    public void chase(Player player, float delta) {
        Vector2 toPlayer = new Vector2(player.getX() - x,
                                       player.getY() - y);
        if (toPlayer.len() > 0) {
            toPlayer.nor().scl(SPEED * delta);
            x += toPlayer.x;
            y += toPlayer.y;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, x - texture.getWidth() / 2f,
                         y - texture.getHeight() / 2f);
    }

    @Override
    public void dispose() {
        // textures owned by Assets, nothing to dispose here.
    }

    /** Apply damage from a bullet. */
    public void takeDamage(int dmg) {
        health -= dmg;
        if (health <= 0) alive = false;
    }
}
