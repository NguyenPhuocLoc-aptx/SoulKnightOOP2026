package io.mygdx.soulknight.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import io.mygdx.soulknight.utils.Assets;
import io.mygdx.soulknight.weapons.Pistol;
import io.mygdx.soulknight.weapons.Shotgun;
import io.mygdx.soulknight.weapons.Weapon;

/**
 * Handles movement, health and weapon handling.
 */
public class Player extends Entity {

    // ----- TUNABLE PARAMETERS -----------------------------------------
    /** Movement speed in world units per second. */
    private static final float SPEED = 200f;   // <-- adjust for feel

    /** Player's starting health. */
    private static final int MAX_HEALTH = 100; // <-- tweak as needed
    // -----------------------------------------------------------------
    private int health = MAX_HEALTH;

    private final Texture texture;   // placeholder for player sprite
    private Weapon currentWeapon;
    private final Weapon pistol;
    private final Weapon shotgun;

    public Player(float x, float y) {
        super(x, y);
        // INSERT_IMAGE_HERE: Player Sprite
        texture = Assets.playerTexture;

        pistol   = new Pistol(this);
        shotgun  = new Shotgun(this);
        currentWeapon = pistol;
    }

    /** Move by a normalized direction vector multiplied by SPEED. */
    public void move(float dx, float dy) {
        Vector2 dir = new Vector2(dx, dy);
        if (dir.len() > 0) {
            dir.nor().scl(SPEED * Gdx.graphics.getDeltaTime());
            x += dir.x;
            y += dir.y;
        }
    }

    /** Switch between pistol and shotgun. */
    public void switchWeapon() {
        currentWeapon = (currentWeapon == pistol) ? shotgun : pistol;
    }

    /** Attempt to fire – weapon handles cooldown internally. */
    public void tryFire() {
        currentWeapon.fire();
    }

    @Override
    public void update(float delta) {
        // Player input handled externally.
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, x - texture.getWidth() / 2f,
                         y - texture.getHeight() / 2f);
    }

    @Override
    public void dispose() {
        // Textures are managed centrally in Assets, no disposal needed here.
    }

    /** Reduce health; called by enemies (if you add melee later). */
    public void takeDamage(int dmg) {
        health -= dmg;
        if (health <= 0) alive = false;
    }

    /** Position getter used by weapons to spawn bullets. */
    public Vector2 getCenter() {
        return new Vector2(x, y);
    }

    public float getX() { return x; }
    public float getY() { return y; }
}
