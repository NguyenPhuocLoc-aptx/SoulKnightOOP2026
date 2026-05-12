package io.mygdx.soulknight.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.mygdx.soulknight.Scenes.Hud;
import io.mygdx.soulknight.SoulKnightGame;
import io.mygdx.soulknight.Sprites.Bullets.Bullet;
import io.mygdx.soulknight.Sprites.Monster.Chaser;
import io.mygdx.soulknight.Sprites.Monster.Monster;
import io.mygdx.soulknight.Sprites.Monster.Shooter;
import io.mygdx.soulknight.Sprites.Player;
import io.mygdx.soulknight.Sprites.WinArea;
import io.mygdx.soulknight.Tools.B2WorldCreator;
import io.mygdx.soulknight.Tools.WorldContactListener;

import java.util.ArrayList;

public class PlayScreen implements Screen {
    private final SoulKnightGame game;

    //  viewport
    private static OrthographicCamera camera;
    private final Viewport gamePort;
    private final Hud hud;

    private final TiledMap map; // reference to the map
    private final OrthogonalTiledMapRenderer renderer; // render the map to screen

    //    create box2d world, b2s variables
    private final World world;
    private final Box2DDebugRenderer b2dr;
    private final Player player;
    private final Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
    private static final ArrayList<Body> bodiesToDestroy = new ArrayList<>();

    private final Music music;
    public boolean win = false;

    public PlayScreen(SoulKnightGame game){
        //put the String to the pack file of image package
        TextureAtlas atlas  = new TextureAtlas("Weapons.pack"); //adding weapons pack
        this.game = game;

        camera = new OrthographicCamera(); //create the camera that follow the knight
        // maintain virtual aspect ratio to make player screen viewport fixed
        gamePort = new FitViewport(SoulKnightGame.V_WIDTH, SoulKnightGame.V_HEIGHT, camera);
        hud = new Hud(game.batch); //create HUD screen to display scores/timers/level

        TmxMapLoader mapLoader = new TmxMapLoader(); // load map tmx to the game
        map = mapLoader.load("map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        camera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        // set up a non-gravitational environment, state remains still at first
        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        new B2WorldCreator(world, map);

        //create knight in game world
        player = new Player(world, mousePos, camera);

        new Shooter(world, camera, new Vector2(600, 115));
        new Chaser(world, camera, new Vector2(380, 150));
        new Shooter(world, camera, new Vector2(380, 60));
        new Shooter(world, camera, new Vector2(200, 150));
        new Chaser(world, camera, new Vector2(750, 115));

        hud.setPlayer(player);

        WorldContactListener worldContactListener = new WorldContactListener();
        world.setContactListener(worldContactListener);

        music = SoulKnightGame.manager.get("audio/music/Dungeon.mp3");
        music.setLooping(true);
        music.play();

        new WinArea(world, this);
    }

    public static OrthographicCamera getCamera(){
        return camera;
    }

    public void update(float dt){
//        take 1 step
        world.step(1/60f, 6, 2);

        Bullet.updateAll();
        player.update(dt);
        Monster.updateAll(dt);

//        attach game camera to players.x coordinate, the camera move horizontally
        camera.position.x = player.b2body.getPosition().x;

        mousePos.x = Gdx.input.getX();
        mousePos.y = Gdx.input.getY();
        camera.unproject(mousePos);

        camera.update();
        renderer.setView(camera);

        hud.update(dt);
    }

    private void destroyBodies(){
        if(!world.isLocked())
            for(Body body : bodiesToDestroy)
                world.destroyBody(body);

        bodiesToDestroy.clear();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        if (!player.getGameOver()) {

            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            // render game map
            renderer.render();

            update(delta);
            Bullet.render();

            hud.stage.draw();
            gameOver();
            win();
        }
    }

    private void gameOver(){
        if (player.getGameOver()){
            Gdx.app.log("GameOver", "");
            music.stop();
            Monster.destroyAll();
            Bullet.destroyAll();
            destroyBodies();
            game.setScreen(new GameOverScreen(game));
            dispose();
        }
    }

    public void win(){
        if(win){
            music.stop();
            Monster.destroyAll();
            Bullet.destroyAll();
            destroyBodies();
            Gdx.app.log("You win", "");
            player.gameOver();
            game.setScreen(new WinScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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
        map.dispose();
        renderer.dispose();
        b2dr.dispose();
        hud.dispose();
        world.dispose();
    }
}
