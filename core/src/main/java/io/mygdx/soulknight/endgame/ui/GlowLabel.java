package io.mygdx.soulknight.endgame.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

public class GlowLabel extends Label {
    private Color glowColor;
    private Color outlineColor;
    private Color foregroundColor;

    public GlowLabel(String text, BitmapFont font) {
        super(text, createStyle(font));
        setupLabel();
    }

    private static LabelStyle createStyle(BitmapFont font) {
        LabelStyle style = new LabelStyle();
        style.font = font;
        style.fontColor = Color.WHITE;
        return style;
    }

    private void setupLabel() {
        glowColor = new Color(1f, 240f / 255f, 160f / 255f, 1f);
        outlineColor = new Color(1f, 220f / 255f, 90f / 255f, 1f);
        foregroundColor = new Color(Color.WHITE);
        setAlignment(Align.center);
    }

    public void setGlowColor(Color glowColor) {
        this.glowColor = new Color(glowColor);
    }

    public void setOutlineColor(Color outlineColor) {
        this.outlineColor = new Color(outlineColor);
    }

    public void setForeground(Color foregroundColor) {
        this.foregroundColor = new Color(foregroundColor);
    }

    public Color getForeground() {
        return new Color(foregroundColor);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float originalX = getX();
        float originalY = getY();
        Color oldColor = new Color(getColor());

        drawGlow(batch, parentAlpha, originalX, originalY);
        drawOutline(batch, parentAlpha, originalX, originalY);
        drawMainText(batch, parentAlpha, originalX, originalY);

        setPosition(originalX, originalY);
        setColor(oldColor);
    }

    private void drawGlow(Batch batch, float parentAlpha, float x, float y) {
        drawLayer(batch, parentAlpha, glowColor, 55f / 255f, x - 3, y);
        drawLayer(batch, parentAlpha, glowColor, 55f / 255f, x + 3, y);
        drawLayer(batch, parentAlpha, glowColor, 55f / 255f, x, y - 3);
        drawLayer(batch, parentAlpha, glowColor, 55f / 255f, x, y + 3);

        drawLayer(batch, parentAlpha, glowColor, 90f / 255f, x - 2, y);
        drawLayer(batch, parentAlpha, glowColor, 90f / 255f, x + 2, y);
        drawLayer(batch, parentAlpha, glowColor, 90f / 255f, x, y - 2);
        drawLayer(batch, parentAlpha, glowColor, 90f / 255f, x, y + 2);
    }

    private void drawOutline(Batch batch, float parentAlpha, float x, float y) {
        drawLayer(batch, parentAlpha, outlineColor, 1f, x - 1, y);
        drawLayer(batch, parentAlpha, outlineColor, 1f, x + 1, y);
        drawLayer(batch, parentAlpha, outlineColor, 1f, x, y - 1);
        drawLayer(batch, parentAlpha, outlineColor, 1f, x, y + 1);
    }

    private void drawMainText(Batch batch, float parentAlpha, float x, float y) {
        drawLayer(batch, parentAlpha, foregroundColor, 1f, x, y);
    }

    private void drawLayer(Batch batch, float parentAlpha, Color color, float alpha, float x, float y) {
        setPosition(x, y);
        setColor(color.r, color.g, color.b, alpha * parentAlpha);
        super.draw(batch, parentAlpha);
    }
}
