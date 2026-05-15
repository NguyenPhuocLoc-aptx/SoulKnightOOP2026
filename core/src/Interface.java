import java.awt.Image;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Interface extends JPanel implements ScoreView {
    private JLabel scoreLabel;
    private JLabel highScoreLabel;
    private Image endGameBackground;

    public Interface(final GameFrame frame) {
        this(new EndScoreActions() {
            @Override
            public void startGame() {
                frame.startGame();
            }

            @Override
            public void showMainMenu() {
                frame.showMainMenu();
            }
        });
    }

    public Interface(final EndScoreActions frame) {
        setLayout(new BorderLayout());

        ImageIcon end = new ImageIcon(getClass().getResource("/assets/BackgroundGame/Fungal.jpg"));
        System.out.println("Background width: " + end.getIconWidth());
        System.out.println("Background height: " + end.getIconHeight());
        endGameBackground = end.getImage();

        GlowLabel title = new GlowLabel("Game Finished");
        title.setForeground(new Color(40, 38, 52));
        title.setOutlineColor(new Color(255, 255, 255));
        title.setGlowColor(new Color(255, 255, 255));
        title.setFont(loadFont("/Font/Neverwinter/Neverwinter Bold.otf", 130f));
        title.setBorder(javax.swing.BorderFactory.createEmptyBorder(50, 0, 10, 0));
        title.setOpaque(false);
        add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(4, 1, 12, 12));
        center.setOpaque(false);
        center.setBorder(javax.swing.BorderFactory.createEmptyBorder(70, 360, 100, 360));

        scoreLabel = new JLabel("score: 0", SwingConstants.CENTER);
        scoreLabel.setForeground(new Color(0, 0, 0));
        scoreLabel.setFont(loadFont("/Font/Neverwinter/Neverwinter Bold.otf", 110f));
        scoreLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 0, 0, 0));
        scoreLabel.setOpaque(false);

        highScoreLabel = new JLabel("highscore: 0", SwingConstants.CENTER);
        highScoreLabel.setForeground(new Color(0, 0, 0));
        highScoreLabel.setFont(loadFont("/Font/Neverwinter/Neverwinter Bold.otf", 85f));
        highScoreLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(-20, 0, 10, 0));
        highScoreLabel.setOpaque(false);

        EndGameButton playAgainButton = new EndGameButton("play again");
        EndGameButton mainMenuButton = new EndGameButton("main menu");

        playAgainButton.setFont(loadFont("/Font/Neverwinter/Neverwinter Bold.otf", 75f));
        mainMenuButton.setFont(loadFont("/Font/Neverwinter/Neverwinter Bold.otf", 75f));

        playAgainButton.addActionListener(e -> frame.startGame());
        mainMenuButton.addActionListener(e -> frame.showMainMenu());

        center.add(scoreLabel);
        center.add(highScoreLabel);
        center.add(playAgainButton);
        center.add(mainMenuButton);

        add(center, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (endGameBackground != null) {
            g.drawImage(endGameBackground, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private Font loadFont(String path, float size) {
        return FontLoader.loadFont(getClass(), path, size);
    }

    @Override
    public void setScore(int score, int highScore) {
        scoreLabel.setText("score: " + score);
        highScoreLabel.setText("highscore: " + highScore);
    }
}