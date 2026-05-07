package io.mygdx.soulknight;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.mygdx.soulknight.screens.GameScreen;

/**
 * SoulKnightGame — application entry point.
 *
 * Owns the single shared SpriteBatch (expensive to create; one per app).
 * Delegates all rendering to the active Screen.
 */
public class SoulKnightGame extends Game {

    // Shared across all screens — created once, disposed once
    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        // Start directly on the GameScreen for now
        // Phase 5 will introduce a MainMenuScreen here instead
        setScreen(new GameScreen(batch));
    }

    @Override
    public void dispose() {
        super.dispose();   // disposes the active screen
        batch.dispose();
    }
}
