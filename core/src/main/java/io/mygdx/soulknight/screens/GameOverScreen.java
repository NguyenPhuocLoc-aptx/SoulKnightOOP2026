package io.mygdx.soulknight.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.mygdx.soulknight.SoulKnightGame;

public class GameOverScreen implements Screen {
    private final Viewport viewport;
    private final Stage stage;

    private final Game game;
    private final Music music;

    private final SpriteBatch spriteBatch;
    private final Texture textureGO;
    private final Sprite spriteGO;

    public GameOverScreen(Game game){
        this.game = game;
        viewport = new FitViewport(SoulKnightGame.V_WIDTH, SoulKnightGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((SoulKnightGame) game).batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("GAME OVER", font);
        Label playAgainLabel = new Label("Click to Play Again", font);

        table.add(gameOverLabel).expandX();
        table.row();
        table.add(new Label("", font)).expandX();
        table.row();
        table.add(playAgainLabel).expandX();

        stage.addActor(table);

        music = SoulKnightGame.manager.get("audio/music/LivingRoom.mp3", Music.class);
        music.setLooping(true);
        music.play();

        spriteBatch = new SpriteBatch();
        textureGO = new Texture("game-over-typography-pic-1600x900.jpg");
        spriteGO = new Sprite(textureGO, 0, 0, 1600, 900);
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

        spriteBatch.begin();
        spriteGO.draw(spriteBatch);
        spriteBatch.end();

        stage.draw();
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
        music.dispose();
        stage.dispose();
        spriteBatch.dispose();
        textureGO.dispose();
    }
}
