package source.Panel;

import source.Button.MenuButton;
import source.Frame.GameFrame;
import source.Label.GlowLabel;
import source.Loader.FontLoader;
import source.Contract.EndScoreActions;
import source.Contract.ScoreView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class EndScorePanel extends JPanel implements ScoreView {
    private JLabel scoreLabel;
    private JLabel highScoreLabel;
    private Image backgroundImage;

    public EndScorePanel(final GameFrame frame) {
        this((EndScoreActions) frame);
    }

    public EndScorePanel(final EndScoreActions actions) {
        setLayout(new BorderLayout());

        loadBackground();
        addTitleLabel();
        addMainContent(actions);
    }

    private void loadBackground() {
        ImageIcon img = new ImageIcon(getClass().getResource("/assets/BackgroundGame/Hollow.jpg"));
        backgroundImage = img.getImage();
    }

    private void addTitleLabel() {
        GlowLabel titleLabel = new GlowLabel("Game Finished");

        titleLabel.setForeground(new Color(45, 30, 55));
        titleLabel.setOutlineColor(Color.WHITE);
        titleLabel.setGlowColor(Color.WHITE);
        titleLabel.setFont(FontLoader.loadFont(getClass(), "/Font/Neverwinter Bold.otf", 100f));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 10, 0));
        titleLabel.setOpaque(false);

        add(titleLabel, BorderLayout.NORTH);
    }

    private void addMainContent(final EndScoreActions actions) {
        JPanel mainPanel = createMainPanel();

        scoreLabel = createScoreLabel();
        highScoreLabel = createHighScoreLabel();

        MenuButton playAgain = createMenuButton("Play Again");
        MenuButton mainMenu = createMenuButton("Main Menu");

        playAgain.addActionListener(e -> actions.startGame());
        mainMenu.addActionListener(e -> actions.showMainMenu());

        mainPanel.add(scoreLabel);
        mainPanel.add(highScoreLabel);
        mainPanel.add(playAgain);
        mainPanel.add(mainMenu);

        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new GridLayout(4, 1, 12, 12));

        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(70, 290, 100, 290));

        return mainPanel;
    }

    private JLabel createScoreLabel() {
        JLabel label = new JLabel("score: 0", SwingConstants.CENTER);

        label.setForeground(Color.BLACK);
        label.setFont(FontLoader.loadFont(getClass(), "/Font/Neverwinter Bold.otf", 90f));
        label.setBorder(BorderFactory.createEmptyBorder(-40, 0, 10, 0));
        label.setOpaque(false);

        return label;
    }

    private JLabel createHighScoreLabel() {
        JLabel label = new JLabel("highscore: 0", SwingConstants.CENTER);

        label.setForeground(Color.BLACK);
        label.setFont(FontLoader.loadFont(getClass(), "/Font/Neverwinter Bold.otf", 85f));
        label.setBorder(BorderFactory.createEmptyBorder(-29, 0, 10, 0));
        label.setOpaque(false);

        return label;
    }

    private MenuButton createMenuButton(String text) {
        return new MenuButton(text);
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

    @Override
    public void setScore(int score, int highScore) {
        scoreLabel.setText("score: " + score);
        highScoreLabel.setText("highscore: " + highScore);
    }
}