import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

public class GamePanel extends JPanel implements KeyListener {
    private Player player;
    private UIHud hud;
    private PausePanel pausePanel;
    private SaveManager saveManager;
    private int currentMap;
    private BufferedImage backgroundImage;

    public GamePanel() {
        this.player = new Player("Knight");
        this.hud = new UIHud(player);
        this.pausePanel = new PausePanel();
        this.saveManager = new SaveManager("save.txt");
        this.currentMap = 1;

        setFocusable(true);
        addKeyListener(this);
        loadBackgroundImage();
    }

    private void loadBackgroundImage() {
        try {
            // Đường dẫn tuyệt đối - chắc chắn hoạt động
            File imgFile = new File("res/images/BackGround.png");
            System.out.println("Đường dẫn ảnh: " + imgFile.getAbsolutePath());

            if (imgFile.exists()) {
                backgroundImage = ImageIO.read(imgFile);
                System.out.println("Load ảnh thành công!");
            } else {
                System.out.println("KHÔNG tìm thấy file ảnh!");
                System.out.println("Hãy tạo thư mục res/images và copy ảnh vào đó");
            }
        } catch (IOException e) {
            System.out.println("Lỗi đọc ảnh: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        } else {
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(java.awt.Color.RED);
            g.drawString("BACKGROUND NOT FOUND!", 50, 50);
        }

        if (hud != null) {
            hud.setCurrentMap(currentMap);
            hud.draw(g);
        }
        if (pausePanel != null) {
            pausePanel.draw(g, getWidth(), getHeight());
        }
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyPressed(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
}