package source.Panel;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        loadBackgroundImage(imagePath);
    }

    private void loadBackgroundImage(String imagePath) {
        URL imageUrl = findImageUrl(imagePath);
        if (imageUrl == null) {
            System.out.println("Image is not found: " + imagePath);
            return;
        }
        ImageIcon imageIcon = new ImageIcon(imageUrl);
        backgroundImage = imageIcon.getImage();
    }

    private URL findImageUrl(String imagePath) {
        return getClass().getResource(imagePath);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
    }

    private void drawBackground(Graphics g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}