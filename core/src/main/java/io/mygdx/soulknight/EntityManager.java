package io.mygdx.soulknight;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

/**
 * Holds and updates all game objects (players, enemies, bullets, …).
 */
public class EntityManager {

    private final Array<Entity> entities = new Array<>();

    /** Add any subclass of Entity. */
    public void addEntity(Entity e) {
        entities.add(e);
    }

    /** Update everything; give the player so enemies can chase him. */
    public void updateAll(float delta, Player player) {
        for (int i = 0; i < entities.size; i++) {
            Entity e = entities.get(i);
            e.update(delta);
            // Simple collision: bullets vs enemies
            if (e instanceof Bullet) {
                Bullet b = (Bullet) e;
                for (int j = 0; j < entities.size; j++) {
                    Entity target = entities.get(j);
                    if (target instanceof Enemy && b.isAlive()
                            && b.collidesWith((Enemy) target)) {
                        ((Enemy) target).takeDamage(b.getDamage());
                        b.destroy();
                    }
                }
            }
        }
        // Remove dead entities (bullets that hit or enemies that died)
        entities.removeIf(e -> !e.isAlive());
    }

    /** Render all entities. */
    public void renderAll(SpriteBatch batch) {
        for (Entity e : entities) {
            e.render(batch);
        }
    }

    /** Dispose assets owned by entities if needed. */
    public void dispose() {
        for (Entity e : entities) {
            e.dispose();
        }
    }

    /** Expose the internal list for external loops (e.g., enemy chase). */
    public Array<Entity> getEntities() {
        return entities;
    }
}
