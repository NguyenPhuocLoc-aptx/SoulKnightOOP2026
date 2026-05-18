package io.mygdx.soulknight.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
    private static final int FIRST_LEVEL_INDEX = 0;
    private static final LevelConfig[] LEVELS = {
        new LevelConfig(
            "map1.tmx",
            "Dungeon 1",
            new Vector2(100, 100),
            new Vector2(896, 136),
            new Vector2(16, 8),
            new String[]{"Obstacle", "Walls"},
            new EnemySpawn[]{
                EnemySpawn.shooter(600, 115),
                EnemySpawn.chaser(380, 150),
                EnemySpawn.shooter(380, 60),
                EnemySpawn.shooter(200, 150),
                EnemySpawn.chaser(750, 115)
            }
        ),
        new LevelConfig(
            "map2.tmx",
            "Dungeon 2",
            new Vector2(900, 10),
            new Vector2(48, 12),
            new Vector2(16, 8),
            new String[]{"Obstacle", "Walls"},
            new EnemySpawn[]{
                EnemySpawn.shooter(600, 115),
                EnemySpawn.chaser(380, 150),
                EnemySpawn.shooter(380, 60),
                EnemySpawn.shooter(200, 150),
                EnemySpawn.chaser(750, 115),
                EnemySpawn.shooter(842, 263),
                EnemySpawn.shooter(617, 59),
                EnemySpawn.shooter(103, 134),
                EnemySpawn.shooter(159, 247),
                EnemySpawn.shooter(39, 141),
                EnemySpawn.chaser(582, 262),
                EnemySpawn.chaser(104, 336)
            }
        ),
        new LevelConfig(
            "map3.tmx",
            "Dungeon 3",
            new Vector2(138, 460),
            new Vector2(893, 445),
            new Vector2(16, 8),
            new String[]{"Obstacle", "Walls"},
            new EnemySpawn[]{
                EnemySpawn.shooter(600, 115),
                EnemySpawn.chaser(380, 150),
                EnemySpawn.shooter(380, 60),
                EnemySpawn.shooter(200, 150),
                EnemySpawn.chaser(750, 115),
                EnemySpawn.shooter(154, 343),
                EnemySpawn.shooter(62, 184),
                EnemySpawn.shooter(250, 312),
                EnemySpawn.shooter(584, 264),
                EnemySpawn.chaser(85, 60),
                EnemySpawn.chaser(426, 306),
                EnemySpawn.chaser(458, 467)


            }
        )
    };

    private final SoulKnightGame game;
    private final int levelIndex;
    private final LevelConfig levelConfig;

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
    private boolean collisionDebugVisible = false;

    public PlayScreen(SoulKnightGame game){
        this(game, FIRST_LEVEL_INDEX);
    }

    public PlayScreen(SoulKnightGame game, int levelIndex){
        this.game = game;
        this.levelIndex = Math.max(FIRST_LEVEL_INDEX, Math.min(levelIndex, LEVELS.length - 1));
        levelConfig = LEVELS[this.levelIndex];

        camera = new OrthographicCamera(); //create the camera that follow the knight
        // maintain virtual aspect ratio to make player screen viewport fixed
        gamePort = new FitViewport(SoulKnightGame.V_WIDTH, SoulKnightGame.V_HEIGHT, camera);
        hud = new Hud(game.batch, levelConfig.displayName); //create HUD screen to display scores/timers/level

        TmxMapLoader mapLoader = new TmxMapLoader(); // load map tmx to the game
        map = mapLoader.load(levelConfig.mapPath);
        renderer = new OrthogonalTiledMapRenderer(map);

        camera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        // set up a non-gravitational environment, state remains still at first
        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        new B2WorldCreator(world, map, levelConfig.collisionLayerNames);

        //create knight in game world
        player = new Player(world, mousePos, camera, levelConfig.playerStart);

        for (EnemySpawn enemySpawn : levelConfig.enemySpawns) {
            enemySpawn.create(world, camera);
        }

        hud.setPlayer(player);

        WorldContactListener worldContactListener = new WorldContactListener();
        world.setContactListener(worldContactListener);

        music = SoulKnightGame.manager.get("audio/music/Dungeon.mp3");
        music.setLooping(true);
        music.play();

        new WinArea(world, this, levelConfig.winAreaCenter, levelConfig.winAreaHalfSize);
    }

    public static OrthographicCamera getCamera(){
        return camera;
    }

    public static int getLevelCount() {
        return LEVELS.length;
    }

    public void update(float dt){
//        take 1 step
        world.step(1/60f, 6, 2);

        Bullet.updateAll();
        player.update(dt);
        Monster.updateAll(dt);
        handleCoordinatePanelInput();

//        attach game camera to players.x coordinate, the camera move horizontally
        Vector2 playerCenter = player.b2body.getWorldCenter();
        camera.position.set(playerCenter.x, playerCenter.y, 0);

        mousePos.x = Gdx.input.getX();
        mousePos.y = Gdx.input.getY();
        camera.unproject(mousePos);

        camera.update();
        renderer.setView(camera);

        hud.updateCoordinatePanel(
            levelConfig.displayName,
            levelConfig.mapPath,
            player.b2body.getPosition(),
            levelConfig.playerStart,
            levelConfig.winAreaCenter
        );
        hud.update(dt);
    }

    private void handleCoordinatePanelInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            hud.setCoordinatePanelVisible(!hud.isCoordinatePanelVisible());
            collisionDebugVisible = !collisionDebugVisible;
        }
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

            update(delta);
            renderer.render();
            if (collisionDebugVisible) {
                b2dr.render(world, camera.combined);
            }
            Monster.renderAll();
            player.render();
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
            clearLevelObjects();
            destroyBodies();
            game.setScreen(new GameOverScreen(game));
            dispose();
        }
    }

    public void win(){
        if(win){
            music.stop();
            clearLevelObjects();
            destroyBodies();

            if (hasNextLevel()) {
                Gdx.app.log("Level clear", levelConfig.displayName);
                game.setScreen(new PlayScreen(game, levelIndex + 1));
            } else {
                Gdx.app.log("You win", "");
                player.gameOver();
                game.setScreen(new WinScreen(game, levelIndex));
            }

            dispose();
        }
    }

    private boolean hasNextLevel() {
        return levelIndex + 1 < LEVELS.length;
    }

    private void clearLevelObjects() {
        Monster.destroyAll();
        Bullet.destroyAll();
        Monster.clearAll();
        Bullet.clearAll();
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

    private static class LevelConfig {
        private final String mapPath;
        private final String displayName;
        private final Vector2 playerStart;
        private final Vector2 winAreaCenter;
        private final Vector2 winAreaHalfSize;
        private final String[] collisionLayerNames;
        private final EnemySpawn[] enemySpawns;

        private LevelConfig(
            String mapPath,
            String displayName,
            Vector2 playerStart,
            Vector2 winAreaCenter,
            Vector2 winAreaHalfSize,
            String[] collisionLayerNames,
            EnemySpawn[] enemySpawns
        ) {
            this.mapPath = mapPath;
            this.displayName = displayName;
            this.playerStart = playerStart;
            this.winAreaCenter = winAreaCenter;
            this.winAreaHalfSize = winAreaHalfSize;
            this.collisionLayerNames = collisionLayerNames;
            this.enemySpawns = enemySpawns;
        }
    }

    private static class EnemySpawn {
        private enum Type {
            SHOOTER,
            CHASER
        }

        private final Type type;
        private final Vector2 position;

        private EnemySpawn(Type type, float x, float y) {
            this.type = type;
            position = new Vector2(x, y);
        }

        private static EnemySpawn shooter(float x, float y) {
            return new EnemySpawn(Type.SHOOTER, x, y);
        }

        private static EnemySpawn chaser(float x, float y) {
            return new EnemySpawn(Type.CHASER, x, y);
        }

        private void create(World world, OrthographicCamera camera) {
            if (type == Type.SHOOTER) {
                new Shooter(world, camera, position);
            } else {
                new Chaser(world, camera, position);
            }
        }
    }
}
