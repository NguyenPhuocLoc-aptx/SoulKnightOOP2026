package io.mygdx.soulknight;

/**
 * Base class for all weapons. Handles cooldown and firing logic.
 */
public abstract class Weapon {

    protected final Player owner;

    // ----- TUNABLE PARAMETERS (set per subclass) -------------------
    protected float fireRate;    // seconds between shots
    protected int   damage;      // per projectile
    protected float bulletSpeed; // world units / second
    protected int   pelletCount; // for shotguns; 1 for pistols
    protected float bulletLife;  // seconds a bullet lives
    // ----------------------------------------------------------------
    private float timeSinceLastShot = 0f;

    public Weapon(Player owner) {
        this.owner = owner;
    }

    /** Called each frame to count down the cooldown. */
    public void update(float delta) {
        timeSinceLastShot += delta;
    }

    /** Attempt to fire – respects fireRate cooldown. */
    public void fire() {
        if (timeSinceLastShot < fireRate) return; // still on cooldown
        timeSinceLastShot = 0f;

        // Mouse world position
        float mouseX = com.badlogic.gdx.Gdx.input.getX();
        float mouseY = com.badlogic.gdx.Gdx.graphics.getHeight()
                       - com.badlogic.gdx.Gdx.input.getY();

        // Direction from player centre to mouse
        com.badlogic.gdx.math.Vector2 dir = new com.badlogic.gdx.math.Vector2(
                mouseX - owner.getX(),
                mouseY - owner.getY()).nor();

        // Spawn bullets – spread for shotgun
        for (int i = 0; i < pelletCount; i++) {
            float spread = (float) ((Math.random() - 0.5) * Math.toRadians(10)); // ±5°
            float angleDeg = (float) Math.toDegrees(Math.atan2(dir.y, dir.x))
                              + (float) Math.toDegrees(spread);
            Bullet b = new Bullet(owner.getX(), owner.getY(),
                    angleDeg, bulletSpeed, damage, bulletLife);
            EntityManagerHolder.get().addEntity(b);
        }
    }
}
