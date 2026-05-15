import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Insets;
import java.awt.Color;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class GlowLabel extends JLabel {
    private Color glowColor = new Color(255, 240, 160);
    private Color outlineColor = new Color(255, 220, 90);

    public GlowLabel(String text) {
        super(text, SwingConstants.CENTER);
        setOpaque(false);
    }

    public void setGlowColor(Color glowColor) {
        this.glowColor = glowColor;
    }

    public void setOutlineColor(Color outlineColor) {
        this.outlineColor = outlineColor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        Insets insets = getInsets();

        GlyphVector gv = getFont().createGlyphVector(g2.getFontRenderContext(), getText());
        Shape textShape = gv.getOutline();

        Rectangle2D bounds = textShape.getBounds2D();

        double availableWidth = getWidth() - insets.left - insets.right;
        double availableHeight = getHeight() - insets.top - insets.bottom;

        double x = insets.left + (availableWidth - bounds.getWidth()) / 2 - bounds.getX();
        double y = insets.top + (availableHeight - bounds.getHeight()) / 2 - bounds.getY();

        g2.translate(x, y);

        g2.setStroke(new BasicStroke(12f));
        g2.setColor(new Color(glowColor.getRed(), glowColor.getGreen(), glowColor.getBlue(), 55));
        g2.draw(textShape);

        g2.setStroke(new BasicStroke(7f));
        g2.setColor(new Color(glowColor.getRed(), glowColor.getGreen(), glowColor.getBlue(), 90));
        g2.draw(textShape);

        g2.setStroke(new BasicStroke(3f));
        g2.setColor(outlineColor);
        g2.draw(textShape);

        g2.setColor(getForeground());
        g2.fill(textShape);

        g2.dispose();
    }
}