package io.mygdx.soulknight.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.mygdx.soulknight.SoulKnightGame;
import io.mygdx.soulknight.Sprites.Player;

// class Hud displays world time, onscreen-controller button, bullet capacity, health, warnings,...
public class Hud implements Disposable {
    public Stage stage;
    private final Viewport viewport;
    private Player player;

    // what should be display on the HUD screen
    private int worldTimer;
    private float timeCount;

    Label countdownLabel;
    Label healthLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label soulKnightLabel;
    Label coordinateTitleLabel;
    Label coordinateMapLabel;
    Label coordinatePlayerLabel;
    Label coordinateSpawnLabel;
    Label coordinateWinLabel;
    Table coordinateTable;
    Texture coordinateBackgroundTexture;

    public Hud (SpriteBatch sb){
        this(sb, "Dungeon 1");
    }

    public Hud(SpriteBatch sb, String worldName){
//        define tracking variables which are displayed on HUD screen
        worldTimer = 666;
        timeCount = 0;

        viewport = new FitViewport(SoulKnightGame.V_WIDTH, SoulKnightGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top(); // display sprites at the top of the page
        table.setFillParent(true); // set the table to the size of the page

        // setup label display
        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        healthLabel = new Label(String.format("HP %d/10", 0), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("Time", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label(worldName,new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        soulKnightLabel = new Label("Knight", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        coordinateTitleLabel = new Label("Coordinate Panel", new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
        coordinateMapLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        coordinatePlayerLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        coordinateSpawnLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        coordinateWinLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        // add to table
        // expandX: labels will be displayed at the whole screen width
        table.add(soulKnightLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);

        table.row();
        table.add(healthLabel).expandX();
        table.add().expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table);

        coordinateTable = new Table();
        coordinateTable.setPosition(10, 10);
        coordinateTable.pad(6);
        coordinateTable.background(createCoordinateBackground());
        coordinateTable.add(coordinateTitleLabel).left();
        coordinateTable.row();
        coordinateTable.add(coordinateMapLabel).left();
        coordinateTable.row();
        coordinateTable.add(coordinatePlayerLabel).left();
        coordinateTable.row();
        coordinateTable.add(coordinateSpawnLabel).left();
        coordinateTable.row();
        coordinateTable.add(coordinateWinLabel).left();
        coordinateTable.setVisible(false);
        coordinateTable.pack();

        stage.addActor(coordinateTable);
    }

    private TextureRegionDrawable createCoordinateBackground() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0f, 0f, 0f, 0.75f);
        pixmap.fill();
        coordinateBackgroundTexture = new Texture(pixmap);
        pixmap.dispose();
        return new TextureRegionDrawable(new TextureRegion(coordinateBackgroundTexture));
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void update(float dt){
        healthLabel.setText(String.format("HP %d/10", player.getHealth()));

        timeCount += dt;

        if(timeCount > 1){
            timeCount = 0;
            countdownLabel.setText(String.format("%03d", --worldTimer));
        }
    }

    public void setCoordinatePanelVisible(boolean visible) {
        coordinateTable.setVisible(visible);
    }

    public boolean isCoordinatePanelVisible() {
        return coordinateTable.isVisible();
    }

    public void updateCoordinatePanel(
        String levelName,
        String mapPath,
        Vector2 playerPosition,
        Vector2 playerStart,
        Vector2 winAreaCenter
    ) {
        coordinateMapLabel.setText(String.format("Map: %s (%s)", levelName, mapPath));
        coordinatePlayerLabel.setText(String.format("Player: X %.1f | Y %.1f", playerPosition.x, playerPosition.y));
        coordinateSpawnLabel.setText(String.format("Spawn: X %.1f | Y %.1f", playerStart.x, playerStart.y));
        coordinateWinLabel.setText(String.format("Win: X %.1f | Y %.1f", winAreaCenter.x, winAreaCenter.y));
        coordinateTable.pack();
    }

    @Override
    public void dispose() {
        stage.dispose();
        coordinateBackgroundTexture.dispose();
    }
}
