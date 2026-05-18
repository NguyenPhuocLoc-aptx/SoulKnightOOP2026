package io.mygdx.soulknight.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import io.mygdx.soulknight.SoulKnightGame;

public class MainMenuScreen implements Screen {

    private final SoulKnightGame game;

    private OrthographicCamera camera;

    private SpriteBatch batch;

    private ShapeRenderer shapeRenderer;

    private Texture backgroundTexture;

    private Texture playerTexture;

    private BitmapFont titleFont;

    private BitmapFont buttonFont;

    private GlyphLayout layout;

    private Vector3 mouse;

    // BUTTONS
    private Rectangle startBtn;

    private Rectangle optionsBtn;

    private Rectangle exitBtn;

    private int hoveredButton = -1;

    // PLAYER FLOAT
    private float playerOffsetY = 0;

    private boolean moveUp = true;

    // SNOW
    private Array<Snow> snows;

    // FIRE
    private Array<FireParticle> fires;

    public MainMenuScreen(SoulKnightGame game) {

        this.game = game;

        camera = new OrthographicCamera();

        camera.setToOrtho(
            false,
            SoulKnightGame.V_WIDTH,
            SoulKnightGame.V_HEIGHT
        );

        batch = new SpriteBatch();

        shapeRenderer = new ShapeRenderer();

        layout = new GlyphLayout();

        mouse = new Vector3();

        // =====================================
        // TEXTURES
        // =====================================

        backgroundTexture =
            new Texture("BackGround.png");

        playerTexture =
            new Texture("Player.png");

        // =====================================
        // FONT
        // =====================================

        FreeTypeFontGenerator generator =
            new FreeTypeFontGenerator(
                Gdx.files.internal(
                    "Font/PressStart2P-Regular.ttf"
                )
            );

        // TITLE FONT
        FreeTypeFontGenerator.FreeTypeFontParameter titleParam =
            new FreeTypeFontGenerator.FreeTypeFontParameter();

        titleParam.size = 22;

        titleParam.color = Color.WHITE;

        titleParam.borderWidth = 2;

        titleParam.borderColor = Color.BLACK;

        titleFont =
            generator.generateFont(titleParam);

        // BUTTON FONT
        FreeTypeFontGenerator.FreeTypeFontParameter buttonParam =
            new FreeTypeFontGenerator.FreeTypeFontParameter();

        buttonParam.size = 8;

        buttonParam.color = Color.WHITE;

        buttonFont =
            generator.generateFont(buttonParam);

        generator.dispose();

        // =====================================
        // BUTTONS
        // =====================================

        int buttonWidth = 120;

        int buttonHeight = 24;

        int centerX =
            (SoulKnightGame.V_WIDTH - buttonWidth) / 2;

        startBtn = new Rectangle(
            centerX,
            98,
            buttonWidth,
            buttonHeight
        );

        optionsBtn = new Rectangle(
            centerX,
            66,
            buttonWidth,
            buttonHeight
        );

        exitBtn = new Rectangle(
            centerX,
            34,
            buttonWidth,
            buttonHeight
        );

        // =====================================
        // SNOW
        // =====================================

        snows = new Array<>();

        for (int i = 0; i < 90; i++) {

            snows.add(
                new Snow(
                    MathUtils.random(
                        0,
                        SoulKnightGame.V_WIDTH
                    ),

                    MathUtils.random(
                        0,
                        SoulKnightGame.V_HEIGHT
                    ),

                    MathUtils.random(
                        0.3f,
                        1.2f
                    )
                )
            );
        }

        // =====================================
        // FIRE
        // =====================================

        fires = new Array<>();

        for (int i = 0; i < 120; i++) {

            fires.add(
                new FireParticle(
                    MathUtils.random(
                        85,
                        285
                    ),

                    SoulKnightGame.V_HEIGHT - 36
                )
            );
        }
    }

    // =====================================
    // UPDATE
    // =====================================

    private void update(float delta) {

        // PLAYER FLOAT
        if (moveUp) {

            playerOffsetY += delta * 8;

            if (playerOffsetY >= 3) {

                moveUp = false;
            }

        } else {

            playerOffsetY -= delta * 8;

            if (playerOffsetY <= 0) {

                moveUp = true;
            }
        }

        // SNOW
        for (Snow snow : snows) {

            snow.y -= snow.speed;

            if (snow.y < 0) {

                snow.y =
                    SoulKnightGame.V_HEIGHT;

                snow.x =
                    MathUtils.random(
                        0,
                        SoulKnightGame.V_WIDTH
                    );
            }
        }

        // FIRE UPDATE
        for (FireParticle fire : fires) {

            fire.y += fire.speed * delta;

            fire.x +=
                MathUtils.random(-18f, 18f) * delta;

            fire.alpha -= delta * 0.7f;

            if (fire.alpha <= 0) {

                fire.x =
                    MathUtils.random(
                        85,
                        285
                    );

                fire.y =
                    SoulKnightGame.V_HEIGHT - 36;

                fire.alpha =
                    MathUtils.random(
                        0.6f,
                        1f
                    );
            }
        }

        // MOUSE
        mouse.set(
            Gdx.input.getX(),
            Gdx.input.getY(),
            0
        );

        camera.unproject(mouse);

        hoveredButton = -1;

        if (startBtn.contains(mouse.x, mouse.y)) {

            hoveredButton = 0;

        } else if (optionsBtn.contains(mouse.x, mouse.y)) {

            hoveredButton = 1;

        } else if (exitBtn.contains(mouse.x, mouse.y)) {

            hoveredButton = 2;
        }

        // CLICK
        if (
            Gdx.input.isButtonJustPressed(
                Input.Buttons.LEFT
            )
        ) {

            if (
                startBtn.contains(
                    mouse.x,
                    mouse.y
                )
            ) {

                game.setScreen(
                    new PlayScreen(game)
                );

            } else if (
                exitBtn.contains(
                    mouse.x,
                    mouse.y
                )
            ) {

                Gdx.app.exit();
            }
        }
    }

    // =====================================
    // RENDER
    // =====================================

    @Override
    public void render(float delta) {

        update(delta);

        ScreenUtils.clear(Color.BLACK);

        camera.update();

        batch.setProjectionMatrix(
            camera.combined
        );

        shapeRenderer.setProjectionMatrix(
            camera.combined
        );

        // =====================================
        // BACKGROUND
        // =====================================

        batch.begin();

        batch.draw(
            backgroundTexture,
            0,
            0,
            SoulKnightGame.V_WIDTH,
            SoulKnightGame.V_HEIGHT
        );

        batch.end();

        // =====================================
        // SNOW
        // =====================================

        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Filled
        );

        shapeRenderer.setColor(
            1,
            1,
            1,
            0.75f
        );

        for (Snow snow : snows) {

            shapeRenderer.rect(
                snow.x,
                snow.y,
                1.5f,
                1.5f
            );
        }

        shapeRenderer.end();

        // =====================================
        // FIRE PARTICLES
        // =====================================

        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Filled
        );

        for (FireParticle fire : fires) {

            // OUTER RED GLOW
            shapeRenderer.setColor(
                1f,
                0.1f,
                0.02f,
                fire.alpha * 0.18f
            );

            shapeRenderer.circle(
                fire.x,
                fire.y,
                fire.size * 2.2f
            );

            // MID ORANGE
            shapeRenderer.setColor(
                1f,
                0.45f,
                0.05f,
                fire.alpha * 0.55f
            );

            shapeRenderer.circle(
                fire.x,
                fire.y,
                fire.size * 1f
            );

            // HOT CORE
            shapeRenderer.setColor(
                1f,
                0.95f,
                0.4f,
                fire.alpha
            );

            shapeRenderer.circle(
                fire.x,
                fire.y,
                fire.size
            );
        }

        shapeRenderer.end();

        // =====================================
        // TITLE
        // =====================================

        batch.begin();

        String title = "SOUL KNIGHT";

        layout.setText(
            titleFont,
            title
        );

        float titleX =
            (SoulKnightGame.V_WIDTH -
                layout.width) / 2;

        float titleY =
            SoulKnightGame.V_HEIGHT - 24;

        // FIRE COLOR ANIMATION
        float time =
            TimeUtils.millis() / 1000f;

        float glow =
            (MathUtils.sin(time * 5f) + 1f) / 2f;

        float r = 1f;

        float g =
            0.3f + glow * 0.7f;

        float b =
            glow * 0.07f;

        // OUTER GLOW
        titleFont.setColor(
            1f,
            0.3f,
            0.05f,
            0.25f
        );

        for (int i = -3; i <= 3; i++) {

            for (int j = -3; j <= 3; j++) {

                titleFont.draw(
                    batch,
                    title,
                    titleX + i,
                    titleY + j
                );
            }
        }

        // SHADOW
        titleFont.setColor(
            0,
            0,
            0,
            1
        );

        titleFont.draw(
            batch,
            title,
            titleX + 3,
            titleY - 3
        );

        // MAIN TITLE
        titleFont.setColor(
            r,
            g,
            b,
            1f
        );

        titleFont.draw(
            batch,
            title,
            titleX,
            titleY
        );

        batch.end();

        // =====================================
        // BUTTONS
        // =====================================

        drawButton(
            startBtn,
            "START GAME",
            hoveredButton == 0
        );

        drawButton(
            optionsBtn,
            "OPTIONS",
            hoveredButton == 1
        );

        drawButton(
            exitBtn,
            "EXIT",
            hoveredButton == 2
        );

        // =====================================
        // PLAYER
        // =====================================

        float pw = 45;

        float ph = 45;

        float px =
            startBtn.x +
                startBtn.width / 2 -
                pw / 2;

        float py =
            startBtn.y +
                startBtn.height +
                4 +
                playerOffsetY;

        batch.begin();

        batch.draw(
            playerTexture,
            px,
            py,
            pw,
            ph
        );

        batch.end();
    }

    private void drawButton(
        Rectangle rect,
        String text,
        boolean hover
    ) {

        // =========================
        // BUTTON BACKGROUND
        // =========================

        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Filled
        );

        // nền nút
        if (hover) {

            shapeRenderer.setColor(
                0.18f,
                0.18f,
                0.18f,
                1f
            );

        } else {

            shapeRenderer.setColor(
                0.05f,
                0.05f,
                0.05f,
                0.92f
            );
        }

        shapeRenderer.rect(
            rect.x,
            rect.y,
            rect.width,
            rect.height
        );

        shapeRenderer.end();

        // =========================
        // BORDER
        // =========================

        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Line
        );

        if (hover) {

            shapeRenderer.setColor(
                1f,
                0.82f,
                0.2f,
                1f
            );

        } else {

            shapeRenderer.setColor(
                0.85f,
                0.65f,
                0.1f,
                1f
            );
        }

        shapeRenderer.rect(
            rect.x,
            rect.y,
            rect.width,
            rect.height
        );

        shapeRenderer.end();

        // =========================
        // TEXT
        // =========================

        batch.begin();

        if (hover) {

            buttonFont.setColor(
                1f,
                1f,
                1f,
                1f
            );

        } else {

            buttonFont.setColor(
                1f,
                0.92f,
                0.7f,
                1f
            );
        }

        layout.setText(
            buttonFont,
            text
        );

        float tx =
            rect.x +
                (rect.width - layout.width) / 2;

        float ty =
            rect.y +
                (rect.height + layout.height) / 2;

        buttonFont.draw(
            batch,
            text,
            tx,
            ty
        );


        batch.end();
    }


    @Override
    public void resize(
        int width,
        int height
    ) {

        camera.setToOrtho(
            false,
            SoulKnightGame.V_WIDTH,
            SoulKnightGame.V_HEIGHT
        );
    }

    @Override
    public void dispose() {

        batch.dispose();

        shapeRenderer.dispose();

        backgroundTexture.dispose();

        playerTexture.dispose();

        titleFont.dispose();

        buttonFont.dispose();
    }

    @Override
    public void show() {}

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    // =====================================
    // SNOW CLASS
    // =====================================

    static class Snow {

        float x;

        float y;

        float speed;

        public Snow(
            float x,
            float y,
            float speed
        ) {

            this.x = x;

            this.y = y;

            this.speed = speed;
        }
    }

    // =====================================
    // FIRE PARTICLE
    // =====================================

    static class FireParticle {

        float x;

        float y;

        float speed;

        float size;

        float alpha;

        public FireParticle(
            float x,
            float y
        ) {

            this.x = x;

            this.y = y;

            this.speed =
                MathUtils.random(
                    15f,
                    30f
                );

            this.size =
                MathUtils.random(
                    0.5f,
                    1.5f
                );

            this.alpha =
                MathUtils.random(
                    0.4f,
                    0.9f
                );
        }
    }
}
