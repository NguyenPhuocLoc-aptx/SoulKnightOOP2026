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

public class IntroScreen implements Screen {

    private final Game game;
    private final Music music;
    private final OrthographicCamera camera;
    private final Viewport viewport;

    private final SpriteBatch spriteBatch = new SpriteBatch();
    private final Texture textureGO = new Texture("Menu.PNG");
    private final Sprite spriteGO = new Sprite(textureGO, 0, 0, 1600, 900);

    public IntroScreen(Game game){
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new FitViewport(1600f, 900f, camera);
        viewport.apply();

        music = SoulKnightGame.manager.get("audio/music/LivingRoom.mp3", Music.class);
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
            game.setScreen(new PlayScreen((SoulKnightGame) game));
            dispose();
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
    }
}
