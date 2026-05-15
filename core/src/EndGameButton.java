import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.RenderingHints;
import java.awt.GradientPaint;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EndGameButton extends MenuButton {
    private boolean hover = false;

    public EndGameButton(String text) {
        super(text);

        setForeground(new Color(235, 248, 255));
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(520, 68));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hover = true;
                setForeground(new Color(255, 255, 255));
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
                setForeground(new Color(235, 248, 255));
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        int w = getWidth();
        int h = getHeight();
        int arc = 28;

        if (getModel().isPressed()) {
            g2.translate(0, 2);
        }

        Color topColor;
        Color bottomColor;
        Color glowColor;
        Color borderColor;

        if (hover) {
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

        g2.setPaint(new GradientPaint(0, 0, topColor, 0, h, bottomColor));
        g2.fillRoundRect(5, 5, w - 10, h - 10, arc, arc);

        g2.setStroke(new BasicStroke(6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(glowColor);
        g2.drawRoundRect(6, 6, w - 12, h - 12, arc, arc);

        g2.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(borderColor);
        g2.drawRoundRect(8, 8, w - 16, h - 16, arc - 4, arc - 4);

        g2.setColor(new Color(255, 255, 255, hover ? 75 : 45));
        g2.drawLine(30, 14, w - 30, 14);

        FontMetrics fm = g2.getFontMetrics(getFont());
        int textX = (w - fm.stringWidth(getText())) / 2;
        int textY = (h - fm.getHeight()) / 2 + fm.getAscent();

        g2.setFont(getFont());

        g2.setColor(new Color(0, 0, 0, 180));
        g2.drawString(getText(), textX + 2, textY + 2);

        g2.setColor(getForeground());
        g2.drawString(getText(), textX, textY);

        g2.dispose();
    }
}