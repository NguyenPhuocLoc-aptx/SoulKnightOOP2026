import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MainMenuPanel extends JPanel implements ActionListener, MouseMotionListener, MouseListener {

    // Background components
    private BufferedImage backgroundImage;
    private ArrayList<Cloud> clouds;
    private ArrayList<Particle> particles;
    private Random random;

    // Character
    private BufferedImage characterImage;
    private float characterBobOffset = 0;
    private boolean characterBobUp = true;

    // UI Components
    private Rectangle startButtonBounds;
    private Rectangle optionsButtonBounds;
    private Rectangle exitButtonBounds;
    private int hoveredButton = -1;

    // Fonts - PHONG CÁCH PIXEL (CHỮ TO HƠN)
    private Font pixelFontTitle;    // Title lớn
    private Font pixelFontButton;   // Button to
    private Font pixelFontScore;    // Score to
    private Font pixelFontFooter;   // Footer nhỏ

    private JFrame parentFrame;
    private Timer animationTimer;
    private int highestScore = 0;

    public MainMenuPanel(JFrame frame) {
        this.parentFrame = frame;
        this.random = new Random();
        setLayout(null);
        setFocusable(true);
        setPreferredSize(new Dimension(800, 600));

        // Khởi tạo font pixel (chữ to hơn)
        initPixelFonts();

        // Load điểm cao nhất
        loadHighestScore();

        // Khởi tạo button bounds
        startButtonBounds = new Rectangle(300, 340, 200, 55);
        optionsButtonBounds = new Rectangle(300, 405, 200, 50);
        exitButtonBounds = new Rectangle(300, 470, 200, 50);
        // Load resources
        loadBackgroundImage();
        createCharacterImage();
        initClouds();
        initParticles();

        // Mouse listeners
        addMouseMotionListener(this);
        addMouseListener(this);

        // Animation timer
        animationTimer = new Timer(16, e -> {
            updateAnimation();
            repaint();
        });
        animationTimer.start();
    }

    private void initPixelFonts() {
        // Font pixel với kích thước lớn hơn
        pixelFontTitle = new Font("Monospaced", Font.BOLD, 52);
        pixelFontButton = new Font("Monospaced", Font.BOLD, 22);
        pixelFontScore = new Font("Monospaced", Font.BOLD, 18);
        pixelFontFooter = new Font("Monospaced", Font.PLAIN, 13);
    }

    private void loadHighestScore() {
        try {
            File file = new File("save/highestscore.txt");
            if (file.exists()) {
                java.util.Scanner scanner = new java.util.Scanner(file);
                highestScore = scanner.nextInt();
                scanner.close();
            }
        } catch (Exception e) {
            highestScore = 0;
        }
    }

    private void loadBackgroundImage() {
        try {
            File imgFile = new File("res/images/BackGround.png");
            if (imgFile.exists()) {
                backgroundImage = ImageIO.read(imgFile);
                System.out.println("✅ Load background thành công!");
            } else {
                createGradientBackground();
                System.out.println("🎨 Tạo gradient background");
            }
        } catch (IOException e) {
            createGradientBackground();
        }
    }

    private void createGradientBackground() {
        backgroundImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = backgroundImage.createGraphics();

        GradientPaint gp = new GradientPaint(
                0, 0, new Color(10, 20, 40),
                800, 600, new Color(60, 30, 80)
        );
        g.setPaint(gp);
        g.fillRect(0, 0, 800, 600);

        g.setColor(new Color(255, 255, 255, 30));
        for (int i = 0; i < 100; i++) {
            g.fillOval(random.nextInt(800), random.nextInt(300), 2, 2);
        }
        g.dispose();
    }

    private void createCharacterImage() {
        try {
            // Thử load ảnh từ các đường dẫn khác nhau
            File imgFile = new File("res/images/character.png");
            if (!imgFile.exists()) {
                imgFile = new File("res/images/player.png");
            }
            if (!imgFile.exists()) {
                imgFile = new File("res/imgs/character.png");
            }

            if (imgFile.exists()) {
                characterImage = ImageIO.read(imgFile);
                System.out.println("✅ Load ảnh nhân vật thành công!");
            } else {
                // Nếu không có ảnh thì vẽ nhân vật bằng code (dự phòng)
                drawCharacterByCode();
                System.out.println("🎨 Không tìm thấy ảnh nhân vật, dùng code vẽ");
            }
        } catch (IOException e) {
            System.out.println("❌ Lỗi load ảnh nhân vật: " + e.getMessage());
            drawCharacterByCode();
        }
    }

    private void drawCharacterByCode() {
        // Vẽ nhân vật bằng code (giữ nguyên code cũ của bạn)
        characterImage = new BufferedImage(100, 120, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = characterImage.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Áo giáp
        g.setColor(new Color(100, 120, 150));
        g.fillRoundRect(30, 35, 40, 55, 15, 15);

        // Mũ giáp
        g.setColor(new Color(80, 100, 130));
        g.fillRoundRect(25, 15, 50, 35, 12, 12);

        // Mắt
        g.setColor(Color.CYAN);
        g.fillOval(38, 28, 8, 8);
        g.fillOval(54, 28, 8, 8);

        // Áo choàng
        g.setColor(new Color(150, 50, 50));
        g.fillRoundRect(25, 65, 50, 40, 12, 12);

        // Tay cầm kiếm
        g.setColor(new Color(180, 180, 200));
        g.fillRoundRect(65, 45, 12, 50, 4, 4);

        // Lưỡi kiếm
        g.setColor(new Color(200, 220, 255));
        int[] xPoints = {70, 78, 86, 78};
        int[] yPoints = {45, 32, 45, 45};
        g.fillPolygon(xPoints, yPoints, 4);

        // Viền vàng
        g.setColor(new Color(255, 200, 0));
        g.drawRoundRect(30, 35, 40, 55, 15, 15);

        g.dispose();
    }
    private void initClouds() {
        clouds = new ArrayList<>();
        for (int i = 0; i < 8; i++) {  // Tăng số lượng mây lên 8
            clouds.add(new Cloud(
                    random.nextInt(900) - 100,  // Vị trí X ngẫu nhiên (có thể âm)
                    random.nextInt(200),         // Vị trí Y ngẫu nhiên
                    40 + random.nextInt(60),     // Chiều rộng ngẫu nhiên
                    20 + random.nextInt(30),     // Chiều cao ngẫu nhiên
                    0.5f + random.nextFloat() * 1.5f,  // Tốc độ ngẫu nhiên
                    random.nextBoolean()         // Hướng di chuyển ngẫu nhiên
            ));
        }
    }

    private void initParticles() {
        particles = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            particles.add(new Particle(
                    random.nextInt(800),
                    random.nextInt(600),
                    1 + random.nextInt(2),
                    0.5f + random.nextFloat()
            ));
        }
    }

    private void updateAnimation() {
        // Cập nhật mây di chuyển ngẫu nhiên liên tục
        for (Cloud cloud : clouds) {
            if (cloud.movingRight) {
                cloud.x += cloud.speed;
            } else {
                cloud.x -= cloud.speed;
            }

            // Khi mây ra khỏi màn hình thì reset vị trí và đổi hướng
            if (cloud.x > 850) {
                cloud.x = -cloud.width;
                cloud.y = random.nextInt(200);
                // Ngẫu nhiên đổi hướng
                cloud.movingRight = random.nextBoolean();
                cloud.speed = 0.5f + random.nextFloat() * 1.5f;
            } else if (cloud.x + cloud.width < -50) {
                cloud.x = 850;
                cloud.y = random.nextInt(200);
                cloud.movingRight = random.nextBoolean();
                cloud.speed = 0.5f + random.nextFloat() * 1.5f;
            }
        }

        // Cập nhật particle (giữ nguyên)
        for (Particle p : particles) {
            p.y += p.speed;
            if (p.y > 600) {
                p.y = 0;
                p.x = random.nextInt(800);
            }
        }

        // Nhân vật lên xuống (giữ nguyên)
        if (characterBobUp) {
            characterBobOffset += 0.25f;
            if (characterBobOffset >= 6) characterBobUp = false;
        } else {
            characterBobOffset -= 0.25f;
            if (characterBobOffset <= 0) characterBobUp = true;
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Vẽ background
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }

        // Vẽ mây
        for (Cloud cloud : clouds) {
            g2d.setColor(new Color(255, 255, 255, 150));
            g2d.fillOval(cloud.x, cloud.y, cloud.width, cloud.height);
            g2d.fillOval(cloud.x + cloud.width/3, cloud.y - cloud.height/3, cloud.width, cloud.height);
            g2d.fillOval(cloud.x + cloud.width*2/3, cloud.y - cloud.height/4, cloud.width, cloud.height);
        }

        // Vẽ sao
        for (Particle p : particles) {
            int alpha = (int)(80 + Math.sin(System.currentTimeMillis() * 0.005 * p.speed) * 40);
            g2d.setColor(new Color(255, 255, 200, alpha));
            g2d.fillOval(p.x, p.y, 2, 2);
        }

        // Vẽ title (CHỈ SOUL KNIGHT, BỎ CLONE)
        drawTitle(g2d);

        // Vẽ HIGHEST SCORE (TO HƠN)
        drawHighestScore(g2d);

        // Vẽ các nút
        drawButton(g2d, startButtonBounds, "START GAME", hoveredButton == 0);
        drawButton(g2d, optionsButtonBounds, "OPTIONS", hoveredButton == 1);
        drawButton(g2d, exitButtonBounds, "EXIT", hoveredButton == 2);

        // Vẽ nhân vật
        drawCharacterOnButton(g2d);

        // Vẽ footer
        drawFooter(g2d);
    }

    private void drawTitle(Graphics2D g2d) {
        String title = "SOUL KNIGHT";

        int glowSize = 3 + (int)(3 * Math.sin(System.currentTimeMillis() * 0.005));

        g2d.setFont(pixelFontTitle);
        FontMetrics fm = g2d.getFontMetrics();
        int x = (800 - fm.stringWidth(title)) / 2;

        // Glow effect (viền sáng xung quanh)
        for (int i = -glowSize; i <= glowSize; i++) {
            for (int j = -glowSize; j <= glowSize; j++) {
                if (Math.abs(i) + Math.abs(j) <= glowSize) {
                    g2d.setColor(new Color(255, 200, 50, 50 / (Math.abs(i) + 1)));
                    g2d.drawString(title, x + i, 120 + j);
                }
            }
        }

        // Shadow
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.drawString(title, x + 3, 123);

        // Main text
        g2d.setColor(new Color(255, 220, 50));
        g2d.drawString(title, x, 120);
    }

    private void drawHighestScore(Graphics2D g2d) {
        String scoreText = "HIGHEST SCORE: " + highestScore;

        g2d.setFont(pixelFontScore);
        FontMetrics fm = g2d.getFontMetrics();
        int x = (800 - fm.stringWidth(scoreText)) / 2;

        // Shadow
        g2d.setColor(new Color(0, 0, 0, 100));
        g2d.drawString(scoreText, x + 1, 175);

        // Main text
        g2d.setColor(new Color(255, 220, 100));
        g2d.drawString(scoreText, x, 173);
    }

    private void drawButton(Graphics2D g2d, Rectangle btn, String text, boolean hover) {
        // Bóng đổ
        g2d.setColor(new Color(0, 0, 0, 100));
        g2d.fillRoundRect(btn.x + 2, btn.y + 2, btn.width, btn.height, 10, 10);

        // Nền nút
        if (hover) {
            g2d.setColor(new Color(255, 180, 30));
        } else {
            g2d.setColor(new Color(0, 0, 0, 200));
        }
        g2d.fillRoundRect(btn.x, btn.y, btn.width, btn.height, 10, 10);

        // Viền nút
        if (hover) {
            g2d.setColor(new Color(255, 255, 100));
            g2d.setStroke(new BasicStroke(2));
        } else {
            g2d.setColor(new Color(255, 200, 50, 150));
            g2d.setStroke(new BasicStroke(1));
        }
        g2d.drawRoundRect(btn.x, btn.y, btn.width, btn.height, 10, 10);

        // Chữ
        g2d.setFont(pixelFontButton);
        FontMetrics fm = g2d.getFontMetrics();
        int textX = btn.x + (btn.width - fm.stringWidth(text)) / 2;
        int textY = btn.y + (btn.height + fm.getAscent()) / 2 - 3;

        // Đổ bóng chữ
        g2d.setColor(new Color(0, 0, 0, 100));
        g2d.drawString(text, textX + 1, textY + 1);

        // Màu chữ
        if (hover) {
            g2d.setColor(Color.WHITE);
        } else {
            g2d.setColor(new Color(255, 220, 100));
        }
        g2d.drawString(text, textX, textY);
    }

    private void drawCharacterOnButton(Graphics2D g2d) {
        // Bóng dưới chân
        g2d.setColor(new Color(0, 0, 0, 80));
        g2d.fillOval(startButtonBounds.x + startButtonBounds.width/2 - 25,
                startButtonBounds.y - 10, 50, 12);

        int charX = startButtonBounds.x + startButtonBounds.width/2 - 30;
        int charY = startButtonBounds.y - 100 + (int)characterBobOffset;

        g2d.drawImage(characterImage, charX, charY, 80, 96, null);
    }

    private void drawFooter(Graphics2D g2d) {
        String footer = "PRESS START TO BEGIN YOUR ADVENTURE";
        g2d.setFont(pixelFontFooter);
        FontMetrics fm = g2d.getFontMetrics();
        int x = (800 - fm.stringWidth(footer)) / 2;
        g2d.setColor(new Color(200, 200, 150, 180));
        g2d.drawString(footer, x, 590);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        hoveredButton = -1;
        if (startButtonBounds.contains(e.getPoint())) hoveredButton = 0;
        else if (optionsButtonBounds.contains(e.getPoint())) hoveredButton = 1;
        else if (exitButtonBounds.contains(e.getPoint())) hoveredButton = 2;
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (startButtonBounds.contains(e.getPoint())) {
            startGame();
        } else if (optionsButtonBounds.contains(e.getPoint())) {
            JOptionPane.showMessageDialog(this,
                    "OPTIONS\n\nSOUND: ON\nMUSIC: ON\nCONTROLS: WASD + MOUSE",
                    "OPTIONS", JOptionPane.INFORMATION_MESSAGE);
        } else if (exitButtonBounds.contains(e.getPoint())) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "ARE YOU SURE?", "EXIT", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    private void startGame() {
        animationTimer.stop();
        parentFrame.dispose();

        JFrame gameFrame = new JFrame("Soul Knight Clone");
        GamePanel gamePanel = new GamePanel();
        gameFrame.add(gamePanel);
        gameFrame.setSize(800, 600);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setResizable(false);
        gameFrame.setVisible(true);
        gamePanel.requestFocusInWindow();
    }

    @Override public void mouseDragged(MouseEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void actionPerformed(ActionEvent e) {}

    class Cloud {
        int x, y, width, height;
        float speed;
        boolean movingRight;  // Thêm biến hướng di chuyển

        Cloud(int x, int y, int w, int h, float s, boolean right) {
            this.x = x;
            this.y = y;
            this.width = w;
            this.height = h;
            this.speed = s;
            this.movingRight = right;
        }
    }

    class Particle {
        int x, y, speed;
        float alpha;
        Particle(int x, int y, int s, float a) {
            this.x = x; this.y = y; this.speed = s; this.alpha = a;
        }
    }
}