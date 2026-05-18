package io.mygdx.soulknight.endgame.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import io.mygdx.soulknight.endgame.service.FontLoader;
import io.mygdx.soulknight.endgame.service.FontService;

public class GameTextButton extends TextButton {
    private Color backgroundColor;
    private Color borderColor;
    private ShapeRenderer shapeRenderer;
    private float roundRadius;

    public GameTextButton(String text) {
        this(text, new FontLoader());
    }

    public GameTextButton(String text, FontService fontService) {
        super(text, createStyle(fontService));
        setupButton();
    }

    private static TextButtonStyle createStyle(FontService fontService) {
        TextButtonStyle style = new TextButtonStyle();
        style.font = fontService.loadFont("Font/Neverwinter Bold.otf", 30f);
        style.fontColor = Color.WHITE;
        return style;
    }

    private void setupButton() {
        backgroundColor = new Color(20f / 255f, 35f / 255f, 40f / 255f, 220f / 255f);
        borderColor = new Color(180f / 255f, 220f / 255f, 230f / 255f, 170f / 255f);
        shapeRenderer = new ShapeRenderer();
        roundRadius = 8f;

        pad(8f, 20f, 8f, 20f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();

        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());

        drawButtonBackground(parentAlpha);

        batch.begin();

        super.draw(batch, parentAlpha);
    }

    private void drawButtonBackground(float parentAlpha) {
        float x = getX();
        float y = getY();
        float width = getWidth();
        float height = getHeight();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(
            backgroundColor.r,
            backgroundColor.g,
            backgroundColor.b,
            backgroundColor.a * parentAlpha
        );
        fillRoundRect(x, y, width, height, roundRadius);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(
            borderColor.r,
            borderColor.g,
            borderColor.b,
            borderColor.a * parentAlpha
        );
        drawRoundRect(x + 2f, y + 2f, width - 5f, height - 5f, roundRadius);
        shapeRenderer.end();
    }

    private void fillRoundRect(float x, float y, float width, float height, float radius) {
        shapeRenderer.rect(x + radius, y, width - radius * 2f, height);
        shapeRenderer.rect(x, y + radius, width, height - radius * 2f);

        shapeRenderer.circle(x + radius, y + radius, radius);
        shapeRenderer.circle(x + width - radius, y + radius, radius);
        shapeRenderer.circle(x + radius, y + height - radius, radius);
        shapeRenderer.circle(x + width - radius, y + height - radius, radius);
    }

    private void drawRoundRect(float x, float y, float width, float height, float radius) {
        shapeRenderer.line(x + radius, y, x + width - radius, y);
        shapeRenderer.line(x + width, y + radius, x + width, y + height - radius);
        shapeRenderer.line(x + width - radius, y + height, x + radius, y + height);
        shapeRenderer.line(x, y + height - radius, x, y + radius);

        shapeRenderer.arc(x + radius, y + radius, radius, 180f, 90f);
        shapeRenderer.arc(x + width - radius, y + radius, radius, 270f, 90f);
        shapeRenderer.arc(x + width - radius, y + height - radius, radius, 0f, 90f);
        shapeRenderer.arc(x + radius, y + height - radius, radius, 90f, 90f);
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
