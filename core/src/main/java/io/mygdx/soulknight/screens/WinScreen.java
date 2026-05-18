package io.mygdx.soulknight.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.mygdx.soulknight.SoulKnightGame;

public class WinScreen implements Screen {
    private static final float WORLD_WIDTH = 1600f;
    private static final float WORLD_HEIGHT = 900f;

    private final Game game;
    private final Music music;
    private final int currentLevelIndex;

    private final OrthographicCamera camera = new OrthographicCamera();
    private final Viewport viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

    private final SpriteBatch spriteBatch = new SpriteBatch();
    private final Texture textureGO = new Texture("Win.jpg");
    private final Sprite spriteGO = new Sprite(textureGO);

    public WinScreen(Game game, int currentLevelIndex){
        this.game = game;
        this.currentLevelIndex = currentLevelIndex;

        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
        viewport.apply();

        spriteGO.setSize(WORLD_WIDTH, WORLD_HEIGHT);
        spriteGO.setPosition(0f, 0f);

        music = SoulKnightGame.manager.get("audio/music/Easter.mp3", Music.class);
        music.setLooping(true);
        music.play();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()){
            music.stop();
            int nextLevelIndex = currentLevelIndex + 1;
            if(nextLevelIndex < PlayScreen.getLevelCount()){
                game.setScreen(new PlayScreen((SoulKnightGame) game, nextLevelIndex));
            } else {
                game.setScreen(new PlayScreen((SoulKnightGame) game, 0));
            }
            dispose();
        }


        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        spriteBatch.setProjectionMatrix(camera.combined);

        spriteBatch.begin();
        spriteGO.draw(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        textureGO.dispose();
    }
}
