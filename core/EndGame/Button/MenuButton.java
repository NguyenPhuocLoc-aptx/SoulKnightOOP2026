package source.Button;

import source.Loader.FontLoader;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class MenuButton extends JButton {
    public MenuButton(String text) {
        super(text);
        setupButton();
    }

    private void setupButton() {
        setFont(FontLoader.loadFont(getClass(), "/Font/Neverwinter Bold.otf", 30f));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        turnOnSmoothDrawing(g2);
        drawButtonBackground(g2);

        g2.dispose();

        super.paintComponent(g);
    }

    private void turnOnSmoothDrawing(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    private void drawButtonBackground(Graphics2D g2) {
        g2.setColor(new Color(20, 35, 40, 220));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);

        g2.setColor(new Color(180, 220, 230, 170));
        g2.drawRoundRect(2, 2, getWidth() - 5, getHeight() - 5, 16, 16);
    }
}