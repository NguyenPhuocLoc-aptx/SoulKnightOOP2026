package io.mygdx.soulknight.endgame.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import io.mygdx.soulknight.endgame.service.FontService;

public class EndGameButton extends GameTextButton {
    private static final float BUTTON_WIDTH = 400f;
    private static final float BUTTON_HEIGHT = 90f;
    private static final float TEXT_Y_OFFSET = 30f;

    private boolean hovering;
    private Texture normalTexture;
    private Texture hoverTexture;
    private GlyphLayout glyphLayout;

    public EndGameButton(String text) {
        super(text);
        setupButton();
    }

    public EndGameButton(String text, FontService fontService) {
        super(text, fontService);
        setupButton();
    }

    private void setupButton() {
        hovering = false;
        glyphLayout = new GlyphLayout();

        setSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        normalTexture = createButtonTexture(false);
        hoverTexture = createButtonTexture(true);
    }

    public void updateSizeByText() {
        setSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        if (normalTexture != null) {
            normalTexture.dispose();
        }

        if (hoverTexture != null) {
            hoverTexture.dispose();
        }

        normalTexture = createButtonTexture(false);
        hoverTexture = createButtonTexture(true);
    }

    @Override
    public float getPrefWidth() {
        return BUTTON_WIDTH;
    }

    @Override
    public float getPrefHeight() {
        return BUTTON_HEIGHT;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        hovering = isOver();

        float moveY = 0f;

        if (isPressed()) {
            moveY = -2f;
        }

        drawButtonShape(batch, parentAlpha, moveY);
        drawButtonText(batch, parentAlpha, moveY);
    }

    private void drawButtonShape(Batch batch, float parentAlpha, float moveY) {
        Texture currentTexture;

        if (hovering) {
            currentTexture = hoverTexture;
        } else {
            currentTexture = normalTexture;
        }

        Color oldColor = batch.getColor();

        batch.setColor(1f, 1f, 1f, parentAlpha);
        batch.draw(currentTexture, getX(), getY() + moveY, getWidth(), getHeight());

        batch.setColor(oldColor);
    }

    private void drawButtonText(Batch batch, float parentAlpha, float moveY) {
        BitmapFont font = getStyle().font;
        String text = getText().toString();

        glyphLayout.setText(font, text);

        float textX = getX() + (getWidth() - glyphLayout.width) / 2f;

        float textY = getY()
            + (getHeight() + glyphLayout.height) / 2f
            + TEXT_Y_OFFSET
            + moveY;

        font.setColor(0f, 0f, 0f, 180f / 255f * parentAlpha);
        font.draw(batch, text, textX + 2f, textY - 2f);

        if (hovering) {
            font.setColor(1f, 1f, 1f, parentAlpha);
        } else {
            font.setColor(235f / 255f, 248f / 255f, 255f / 255f, parentAlpha);
        }

        font.draw(batch, text, textX, textY);
    }

    private Texture createButtonTexture(boolean hover) {
        int scale = 3;
        int width = (int) BUTTON_WIDTH * scale;
        int height = (int) BUTTON_HEIGHT * scale;

        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);

        Color topColor;
        Color bottomColor;
        Color glowColor;
        Color borderColor;
        Color highlightColor;

        if (hover) {
            topColor = new Color(42f / 255f, 82f / 255f, 105f / 255f, 220f / 255f);
            bottomColor = new Color(8f / 255f, 20f / 255f, 35f / 255f, 235f / 255f);
            glowColor = new Color(190f / 255f, 235f / 255f, 255f / 255f, 150f / 255f);
            borderColor = new Color(1f, 1f, 1f, 230f / 255f);
            highlightColor = new Color(1f, 1f, 1f, 75f / 255f);
        } else {
            topColor = new Color(10f / 255f, 25f / 255f, 40f / 255f, 195f / 255f);
            bottomColor = new Color(2f / 255f, 8f / 255f, 18f / 255f, 220f / 255f);
            glowColor = new Color(150f / 255f, 220f / 255f, 255f / 255f, 85f / 255f);
            borderColor = new Color(195f / 255f, 230f / 255f, 245f / 255f, 180f / 255f);
            highlightColor = new Color(1f, 1f, 1f, 45f / 255f);
        }

        drawGradientRoundRect(
            pixmap,
            5 * scale,
            5 * scale,
            width - 10 * scale,
            height - 10 * scale,
            14 * scale,
            topColor,
            bottomColor
        );

        drawRoundBorder(
            pixmap,
            6 * scale,
            6 * scale,
            width - 12 * scale,
            height - 12 * scale,
            14 * scale,
            6 * scale,
            glowColor
        );

        drawRoundBorder(
            pixmap,
            8 * scale,
            8 * scale,
            width - 16 * scale,
            height - 16 * scale,
            12 * scale,
            3 * scale,
            borderColor
        );

        drawLine(
            pixmap,
            30 * scale,
            14 * scale,
            width - 30 * scale,
            14 * scale,
            1 * scale,
            highlightColor
        );

        Texture texture = new Texture(pixmap);
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        pixmap.dispose();

        return texture;
    }

    private void drawGradientRoundRect(Pixmap pixmap, int x, int y, int width, int height,
                                       int radius, Color topColor, Color bottomColor) {
        for (int py = y; py < y + height; py++) {
            float percent = (float) (py - y) / height;
            Color currentColor = mixColor(topColor, bottomColor, percent);

            for (int px = x; px < x + width; px++) {
                if (isInsideRoundRect(px, py, x, y, width, height, radius)) {
                    blendPixel(pixmap, px, py, currentColor);
                }
            }
        }
    }

    private void drawRoundBorder(Pixmap pixmap, int x, int y, int width, int height,
                                 int radius, int thickness, Color color) {
        for (int py = y; py < y + height; py++) {
            for (int px = x; px < x + width; px++) {
                boolean insideOuter = isInsideRoundRect(px, py, x, y, width, height, radius);

                boolean insideInner = isInsideRoundRect(
                    px,
                    py,
                    x + thickness,
                    y + thickness,
                    width - thickness * 2,
                    height - thickness * 2,
                    radius - thickness
                );

                if (insideOuter && !insideInner) {
                    blendPixel(pixmap, px, py, color);
                }
            }
        }
    }

    private void drawLine(Pixmap pixmap, int x1, int y1, int x2, int y2,
                          int thickness, Color color) {
        for (int y = y1; y < y1 + thickness; y++) {
            for (int x = x1; x <= x2; x++) {
                blendPixel(pixmap, x, y, color);
            }
        }
    }

    private boolean isInsideRoundRect(int px, int py, int x, int y,
                                      int width, int height, int radius) {
        int right = x + width;
        int bottom = y + height;

        if (px < x || px >= right || py < y || py >= bottom) {
            return false;
        }

        int centerX = px;
        int centerY = py;

        if (px < x + radius) {
            centerX = x + radius;
        } else if (px >= right - radius) {
            centerX = right - radius - 1;
        }

        if (py < y + radius) {
            centerY = y + radius;
        } else if (py >= bottom - radius) {
            centerY = bottom - radius - 1;
        }

        int dx = px - centerX;
        int dy = py - centerY;

        return dx * dx + dy * dy <= radius * radius;
    }

    private Color mixColor(Color topColor, Color bottomColor, float percent) {
        float r = topColor.r + (bottomColor.r - topColor.r) * percent;
        float g = topColor.g + (bottomColor.g - topColor.g) * percent;
        float b = topColor.b + (bottomColor.b - topColor.b) * percent;
        float a = topColor.a + (bottomColor.a - topColor.a) * percent;

        return new Color(r, g, b, a);
    }

    private void blendPixel(Pixmap pixmap, int x, int y, Color sourceColor) {
        Color destinationColor = new Color();
        Color.rgba8888ToColor(destinationColor, pixmap.getPixel(x, y));

        float sourceAlpha = sourceColor.a;
        float destinationAlpha = destinationColor.a;

        float finalAlpha = sourceAlpha + destinationAlpha * (1f - sourceAlpha);

        if (finalAlpha <= 0f) {
            return;
        }

        float finalRed = (sourceColor.r * sourceAlpha
            + destinationColor.r * destinationAlpha * (1f - sourceAlpha)) / finalAlpha;

        float finalGreen = (sourceColor.g * sourceAlpha
            + destinationColor.g * destinationAlpha * (1f - sourceAlpha)) / finalAlpha;

        float finalBlue = (sourceColor.b * sourceAlpha
            + destinationColor.b * destinationAlpha * (1f - sourceAlpha)) / finalAlpha;

        pixmap.setColor(finalRed, finalGreen, finalBlue, finalAlpha);
        pixmap.drawPixel(x, y);
    }

    @Override
    public void dispose() {
        super.dispose();

        if (normalTexture != null) {
            normalTexture.dispose();
        }

        if (hoverTexture != null) {
            hoverTexture.dispose();
        }
    }
}
