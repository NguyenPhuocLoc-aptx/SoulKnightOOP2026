package io.mygdx.soulknight;

/**
 * Fast, single‑shot weapon.
 */
public class Pistol extends Weapon {

    public Pistol(Player owner) {
        super(owner);
        fireRate   = 0.15f; // 6 shots per second
        damage     = 10;
        bulletSpeed= 500f; // fast bullet
        pelletCount= 1;
        bulletLife = 2f;   // seconds
    }
}
