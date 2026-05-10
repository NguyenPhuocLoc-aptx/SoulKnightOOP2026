package io.mygdx.soulknight.weapons;

import io.mygdx.soulknight.entities.Player;

/**
 * Slow, spread weapon that fires multiple pellets per shot.
 */
public class Shotgun extends Weapon {

    public Shotgun(Player owner) {
        super(owner);
        fireRate   = 0.8f;   // slower rate
        damage     = 6;      // per pellet (total ~18‑30)
        bulletSpeed= 300f;   // slower bullet
        pelletCount= 5;      // you can tweak to 3‑5
        bulletLife = 1.2f;
    }
}
