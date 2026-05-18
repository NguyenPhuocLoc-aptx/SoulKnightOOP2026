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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import io.mygdx.soulknight.endgame.contract.EndScoreActions;
import io.mygdx.soulknight.endgame.contract.ScoreView;
import io.mygdx.soulknight.endgame.service.FontLoader;
import io.mygdx.soulknight.endgame.service.FontService;
import io.mygdx.soulknight.endgame.ui.BackgroundImage;
import io.mygdx.soulknight.endgame.ui.GameTextButton;
import io.mygdx.soulknight.endgame.ui.GlowLabel;

public class EndScoreScreen extends ScreenAdapter implements ScoreView {
    private Stage stage;
    private SpriteBatch batch;
    private BackgroundImage backgroundImage;

    private Label scoreLabel;
    private Label highScoreLabel;

    private FontService fontService;

    private BitmapFont titleFont;
    private BitmapFont scoreFont;
    private BitmapFont highScoreFont;

    public EndScoreScreen(final EndScoreActions actions) {
        fontService = new FontLoader();

        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();

        loadBackground();
        loadFonts();
        addTitleLabel();
        addMainContent(actions);

        Gdx.input.setInputProcessor(stage);
    }

    private void loadBackground() {
        backgroundImage = new BackgroundImage("BackgroundGame/Hollow.jpg");
    }

    private void loadFonts() {
        titleFont = fontService.loadFont("Font/Neverwinter Bold.otf", 100f);
        scoreFont = fontService.loadFont("Font/Neverwinter Bold.otf", 90f);
        highScoreFont = fontService.loadFont("Font/Neverwinter Bold.otf", 85f);
    }

    private void addTitleLabel() {
        Table root = getRootTable();

        GlowLabel titleLabel = new GlowLabel("Game Finished", titleFont);

        titleLabel.setForeground(new Color(45f / 255f, 30f / 255f, 55f / 255f, 1f));
        titleLabel.setOutlineColor(Color.WHITE);
        titleLabel.setGlowColor(Color.WHITE);

        root.add(titleLabel)
            .expandX()
            .fillX()
            .height(150f)
            .pad(50f, 0f, 10f, 0f);

        root.row();
    }

    private void addMainContent(final EndScoreActions actions) {
        Table root = getRootTable();
        Table mainPanel = createMainPanel();

        scoreLabel = createScoreLabel();
        highScoreLabel = createHighScoreLabel();

        GameTextButton playAgain = createMenuButton("Play Again");
        GameTextButton mainMenu = createMenuButton("Main Menu");

        playAgain.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                actions.startGame();
            }
        });

        mainMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                actions.showMainMenu();
            }
        });

        mainPanel.add(scoreLabel)
            .expandX()
            .fillX()
            .height(90f)
            .pad(-40f, 0f, 10f, 0f);

        mainPanel.row();

        mainPanel.add(highScoreLabel)
            .expandX()
            .fillX()
            .height(85f)
            .pad(-29f, 0f, 10f, 0f);

        mainPanel.row();

        mainPanel.add(playAgain)
            .expandX()
            .fillX()
            .height(68f)
            .padTop(12f);

        mainPanel.row();

        mainPanel.add(mainMenu)
            .expandX()
            .fillX()
            .height(68f)
            .padTop(12f);

        root.add(mainPanel)
            .expand()
            .fill()
            .pad(70f, 290f, 100f, 290f);
    }

    private Table createMainPanel() {
        Table mainPanel = new Table();
        return mainPanel;
    }

    private Label createScoreLabel() {
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = scoreFont;
        style.fontColor = Color.BLACK;

        Label label = new Label("score: 0", style);
        label.setAlignment(Align.center);

        return label;
    }

    private Label createHighScoreLabel() {
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = highScoreFont;
        style.fontColor = Color.BLACK;

        Label label = new Label("highscore: 0", style);
        label.setAlignment(Align.center);

        return label;
    }

    private GameTextButton createMenuButton(String text) {
        return new GameTextButton(text, fontService);
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
        backgroundImage.draw(batch, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    @Override
    public void setScore(int score, int highScore) {
        scoreLabel.setText("score: " + score);
        highScoreLabel.setText("highscore: " + highScore);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();

        backgroundImage.dispose();

        titleFont.dispose();
        scoreFont.dispose();
        highScoreFont.dispose();
    }
}
