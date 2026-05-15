import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class UIHud {
    private Player player;
    private int currentMap;

    public UIHud(Player player) {
        this.player = player;
        this.currentMap = 1;
    }

    public void setCurrentMap(int currentMap) {
        this.currentMap = currentMap;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 18));

        g.drawString("HP: " + player.getHp(), 20, 30);
        g.drawString("Score: " + player.getScore(), 20, 60);
        g.drawString("Map: " + currentMap, 20, 90);
    }
}