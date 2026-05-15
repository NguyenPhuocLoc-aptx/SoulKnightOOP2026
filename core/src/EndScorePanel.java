import java.awt.Image;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

public class EndScorePanel extends JPanel {
    private JLabel scoreLabel;
    private JLabel highScoreLabel;
    private Image backgroundImage;

    public EndScorePanel(final GameFrame frame) {
        setLayout(new BorderLayout());

        ImageIcon img = new ImageIcon(getClass().getResource("/assets/BackgroundGame/Fungal.jpg"));
        backgroundImage = img.getImage();

        GlowLabel titleLabel = new GlowLabel("Game Finished");
        titleLabel.setForeground(new Color(45, 30, 55));
        titleLabel.setOutlineColor(Color.WHITE);
        titleLabel.setGlowColor(Color.WHITE);
        titleLabel.setFont(FontLoader.loadFont(getClass(), "/Font/Neverwinter/Neverwinter Bold.otf", 100f));
        titleLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(50, 0, 10, 0));
        titleLabel.setOpaque(false);

        add(titleLabel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new GridLayout(4, 1, 12, 12));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(70, 290, 100, 290));

        scoreLabel = new JLabel("score: 0", SwingConstants.CENTER);
        scoreLabel.setForeground(Color.BLACK);
        scoreLabel.setFont(FontLoader.loadFont(getClass(), "/Font/Neverwinter/Neverwinter Bold.otf", 90f));
        scoreLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(-40, 0, 10, 0));
        scoreLabel.setOpaque(false);

        highScoreLabel = new JLabel("highscore: 0", SwingConstants.CENTER);
        highScoreLabel.setForeground(Color.BLACK);
        highScoreLabel.setFont(FontLoader.loadFont(getClass(), "/Font/Neverwinter/Neverwinter Bold.otf", 85f));
        highScoreLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(-29, 0, 10, 0));
        highScoreLabel.setOpaque(false);

        MenuButton playAgain = new MenuButton("Play Again");
        MenuButton mainMenu = new MenuButton("Main Menu");

        playAgain.addActionListener(e -> frame.startGame());
        mainMenu.addActionListener(e -> frame.showMainMenu());

        mainPanel.add(scoreLabel);
        mainPanel.add(highScoreLabel);
        mainPanel.add(playAgain);
        mainPanel.add(mainMenu);

        add(mainPanel, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public void setScore(int score, int highScore) {
        scoreLabel.setText("Score: " + score);
        highScoreLabel.setText("Highscore: " + highScore);
    }
}