package io.mygdx.soulknight.managers;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.mygdx.soulknight.entities.Bullet;
import io.mygdx.soulknight.entities.Enemy;
import io.mygdx.soulknight.entities.Entity;
import io.mygdx.soulknight.entities.Player;

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

        // ----- XÓA CÁC THỰC THỂ ĐÃ CHẾT -----
        // Duyệt ngược để an toàn khi gọi removeIndex(i)
        for (int i = entities.size - 1; i >= 0; i--) {
            if (!entities.get(i).isAlive()) {
                entities.removeIndex(i);
            }
        }
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
