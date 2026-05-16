package source.Button;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EndGameButton extends MenuButton {
    private boolean hovering;

    public EndGameButton(String text) {
        super(text);
        setupButton();
        setupMouseEffect();
    }

    private void setupButton() {
        hovering = false;

        setForeground(new Color(235, 248, 255));
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(520, 68));
    }

    private void setupMouseEffect() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hovering = true;
                setForeground(new Color(255, 255, 255));
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hovering = false;
                setForeground(new Color(235, 248, 255));
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        turnOnSmoothDrawing(g2);

        int width = getWidth();
        int height = getHeight();
        int arc = 28;

        if (getModel().isPressed()) {
            g2.translate(0, 2);
        }

        drawButtonShape(g2, width, height, arc);
        drawButtonText(g2, width, height);

        g2.dispose();
    }

    private void turnOnSmoothDrawing(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    }

    private void drawButtonShape(Graphics2D g2, int width, int height, int arc) {
        Color topColor;
        Color bottomColor;
        Color glowColor;
        Color borderColor;

        if (hovering) {
            topColor = new Color(42, 82, 105, 220);
            bottomColor = new Color(8, 20, 35, 235);
            glowColor = new Color(190, 235, 255, 150);
            borderColor = new Color(255, 255, 255, 230);
        } else {
            topColor = new Color(10, 25, 40, 195);
            bottomColor = new Color(2, 8, 18, 220);
            glowColor = new Color(150, 220, 255, 85);
            borderColor = new Color(195, 230, 245, 180);
        }

        g2.setPaint(new GradientPaint(0, 0, topColor, 0, height, bottomColor));
        g2.fillRoundRect(5, 5, width - 10, height - 10, arc, arc);

        g2.setStroke(new BasicStroke(6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(glowColor);
        g2.drawRoundRect(6, 6, width - 12, height - 12, arc, arc);

        g2.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(borderColor);
        g2.drawRoundRect(8, 8, width - 16, height - 16, arc - 4, arc - 4);

        g2.setColor(new Color(255, 255, 255, hovering ? 75 : 45));
        g2.drawLine(30, 14, width - 30, 14);
    }

    private void drawButtonText(Graphics2D g2, int width, int height) {
        g2.setFont(getFont());

        FontMetrics fontMetrics = g2.getFontMetrics(getFont());
        int textX = (width - fontMetrics.stringWidth(getText())) / 2;
        int textY = (height - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent() - 8;

        g2.setColor(new Color(0, 0, 0, 180));
        g2.drawString(getText(), textX + 2, textY + 2);

        g2.setColor(getForeground());
        g2.drawString(getText(), textX, textY);
    }
}