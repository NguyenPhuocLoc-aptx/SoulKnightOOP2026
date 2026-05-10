package io.mygdx.soulknight;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import io.mygdx.soulknight.screens.PlayScreen;

/**
 * Starts the LibGDX application and switches to the PlayScreen.
 */
public class GameMain extends Game {

    @Override
    public void create() {
        // Switch to the main gameplay screen
        setScreen(new PlayScreen(this));
    }

    @Override
    public void render() {
        // Clear screen (black) before each frame
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
