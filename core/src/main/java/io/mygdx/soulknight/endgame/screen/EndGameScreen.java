package io.mygdx.soulknight.endgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import io.mygdx.soulknight.SoulKnightGame;
import io.mygdx.soulknight.endgame.contract.EndScoreActions;
import io.mygdx.soulknight.endgame.contract.ScoreView;
import io.mygdx.soulknight.endgame.service.FontLoader;
import io.mygdx.soulknight.endgame.service.FontService;
import io.mygdx.soulknight.endgame.ui.BackgroundImage;
import io.mygdx.soulknight.endgame.ui.EndGameButton;
import io.mygdx.soulknight.endgame.ui.GlowLabel;

public class EndGameScreen extends ScreenAdapter implements ScoreView {
    private Stage stage;
    private SpriteBatch batch;
    private BackgroundImage endGameBackground;

    private Label scoreLabel;
    private Label highScoreLabel;

    private FontService fontService;

    private BitmapFont titleFont;
    private BitmapFont scoreFont;
    private BitmapFont highScoreFont;
    private BitmapFont buttonFont;

    public EndGameScreen(final SoulKnightGame game) {
        this(new EndScoreActions() {
            @Override
            public void startGame() {
                game.startGame();
            }


            @Override
            public void showMainMenu() {
                game.showMainMenu();
            }
        });
    }

    public EndGameScreen(final EndScoreActions actions) {
        fontService = new FontLoader();

        batch = new SpriteBatch();
        stage = new Stage(new FitViewport(1112f, 803f));

        loadBackground();
        loadFonts();
        addTitle();
        addCenterContent(actions);

        Gdx.input.setInputProcessor(stage);
    }

    private void loadBackground() {
        endGameBackground = new BackgroundImage("BackgroundGame/Hollow.jpg");
    }

    private void loadFonts() {
        titleFont = loadFont("Font/Neverwinter Bold.otf", 150f);
        scoreFont = loadFont("Font/Neverwinter Bold.otf", 120f);
        highScoreFont = loadFont("Font/Neverwinter Bold.otf", 100f);
        buttonFont = loadFont("Font/Neverwinter Bold.otf", 75f);
    }

    private void addTitle() {
        Table root = getRootTable();

        GlowLabel title = new GlowLabel("Game Finished", titleFont);

        title.setForeground(new Color(40f / 255f, 38f / 255f, 52f / 255f, 1f));
        title.setOutlineColor(new Color(1f, 1f, 1f, 1f));
        title.setGlowColor(new Color(1f, 1f, 1f, 1f));

        root.add(title)
            .expandX()
            .fillX()
            .height(180f)
            .pad(25f, 0f, 10f, 0f);

        root.row();
    }

    private void addCenterContent(final EndScoreActions actions) {
        scoreLabel = createScoreLabel();
        highScoreLabel = createHighScoreLabel();

        EndGameButton playAgainButton = createButton("play again");
        EndGameButton mainMenuButton = createButton("main menu");

        playAgainButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                actions.startGame();
            }
        });

        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                actions.showMainMenu();
            }
        });

        scoreLabel.setSize(520f, 110f);
        highScoreLabel.setSize(520f, 90f);

        playAgainButton.setSize(
            playAgainButton.getPrefWidth(),
            playAgainButton.getPrefHeight()
        );

        mainMenuButton.setSize(
            mainMenuButton.getPrefWidth(),
            mainMenuButton.getPrefHeight()
        );

        float screenWidth = 1112f;

        float labelX = (screenWidth - 520f) / 2f;
        float playAgainX = (screenWidth - playAgainButton.getPrefWidth()) / 2f;
        float mainMenuX = (screenWidth - mainMenuButton.getPrefWidth()) / 2f;

        scoreLabel.setPosition(labelX, 450f);
        highScoreLabel.setPosition(labelX, 365f);

        playAgainButton.setPosition(playAgainX, 200f);
        mainMenuButton.setPosition(mainMenuX, 100f);

        stage.addActor(scoreLabel);
        stage.addActor(highScoreLabel);
        stage.addActor(playAgainButton);
        stage.addActor(mainMenuButton);
    }

    private Table getRootTable() {
        if (stage.getActors().size == 0) {
            Table root = new Table();
            root.setFillParent(true);
            root.top();
            stage.addActor(root);
            return root;
        }

        return (Table) stage.getActors().first();
    }

    private Label createScoreLabel() {
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = scoreFont;
        style.fontColor = new Color(0f, 0f, 0f, 1f);

        Label label = new Label("score: 0", style);
        label.setAlignment(1);

        return label;
    }

    private Label createHighScoreLabel() {
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = highScoreFont;
        style.fontColor = new Color(0f, 0f, 0f, 1f);

        Label label = new Label("highscore: 0", style);
        label.setAlignment(1);

        return label;
    }

    private EndGameButton createButton(String text) {
        EndGameButton button = new EndGameButton(text, fontService);

        button.getStyle().font = buttonFont;
        button.updateSizeByText();

        return button;
    }

    private BitmapFont loadFont(String path, float size) {
        return fontService.loadFont(path, size);
    }

    @Override
    public void render(float delta) {
        clearScreen();
        drawBackground();

        stage.act(delta);
        stage.draw();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void drawBackground() {
        batch.begin();
        endGameBackground.draw(batch, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void setScore(int score, int highScore) {
        scoreLabel.setText("score: " + score);
        highScoreLabel.setText("highscore: " + highScore);
    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        endGameBackground.dispose();

        titleFont.dispose();
        scoreFont.dispose();
        highScoreFont.dispose();
        buttonFont.dispose();
    }
}
