import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class PausePanel {
    private boolean paused;

    public PausePanel() {
        this.paused = false;
    }

    public void togglePause() {
        paused = !paused;
    }

    public boolean isPaused() {
        return paused;
    }

    public void draw(Graphics g, int width, int height) {
        if (paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, width, height);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("PAUSED", width / 2 - 90, height / 2);

            g.setFont(new Font("Arial", Font.PLAIN, 18));
            g.drawString("Press P to continue", width / 2 - 90, height / 2 + 40);
        }
    }
}