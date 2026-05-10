package io.mygdx.soulknight.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.mygdx.soulknight.*;
import io.mygdx.soulknight.entities.Enemy;
import io.mygdx.soulknight.entities.Entity;
import io.mygdx.soulknight.entities.Player;
import io.mygdx.soulknight.managers.EntityManager;
import io.mygdx.soulknight.managers.EntityManagerHolder;
import io.mygdx.soulknight.utils.Assets;


public class PlayScreen extends ScreenAdapter {

    private final GameMain game;
    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private final EntityManager entityManager;
    private final Player player;

    // --- MAP SETTINGS -------------------------------------------------
    // Map size in world units (you can change to fit your tiles)
    private static final int MAP_WIDTH = 800;
    private static final int MAP_HEIGHT = 600;

    public PlayScreen(GameMain game) {
        this.game = game;
        this.batch = new SpriteBatch();

        camera = new OrthographicCamera(Gdx.graphics.getWidth(),
                                        Gdx.graphics.getHeight());
        camera.setToOrtho(false); // Y+ goes up

        entityManager = new EntityManager();

        // Create the player in the centre of the map
        player = new Player(MAP_WIDTH / 2f, MAP_HEIGHT / 2f);
        entityManager.addEntity(player);

        // Spawn two simple enemies
        entityManager.addEntity(new Enemy(100, 100));
        entityManager.addEntity(new Enemy(700, 500));

        // Make the EntityManager globally reachable for weapons
        EntityManagerHolder.set(entityManager);
    }

    @Override
    public void render(float delta) {
        handleInput();

        // ----- UPDATE ---------------
        entityManager.updateAll(delta, player);

        // Enemies need the player reference to chase
        for (Entity e : entityManager.getEntities()) {
            if (e instanceof Enemy) {
                ((Enemy) e).chase(player, delta);
            }
        }

        // ----- RENDER ---------------
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // Simple background – just a filled rectangle (replace with your map later)
        batch.setColor(0.1f, 0.1f, 0.12f, 1f);
        batch.draw(Assets.blankTexture,
                  0, 0, MAP_WIDTH, MAP_HEIGHT);
        batch.setColor(1f, 1f, 1f, 1f);

        entityManager.renderAll(batch);
        batch.end();
    }


    private void handleInput() {
        // --- PLAYER MOVEMENT (WASD) ------------------------------------
        float dx = 0, dy = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) dy += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) dy -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) dx -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) dx += 1;
        player.move(dx, dy);

        // --- FIRE (left mouse) ---------------------------------------
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            player.tryFire();
        }

        // --- WEAPON SWITCH (Q) ---------------------------------------
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            player.switchWeapon();
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void dispose() {
        batch.dispose();
        entityManager.dispose();
    }
}
