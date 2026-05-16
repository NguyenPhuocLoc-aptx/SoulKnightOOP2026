package source.Panel;

import source.Button.EndGameButton;
import source.Frame.GameFrame;
import source.Label.GlowLabel;
import source.Loader.FontLoader;
import source.Contract.EndScoreActions;
import source.Contract.ScoreView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class EndGamePanel extends JPanel implements ScoreView {
    private JLabel scoreLabel;
    private JLabel highScoreLabel;
    private Image endGameBackground;

    public EndGamePanel(final GameFrame frame) {
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

    public EndGamePanel(final EndScoreActions actions) {
        setLayout(new BorderLayout());

        loadBackground();
        addTitle();
        addCenterContent(actions);
    }

    private void loadBackground() {
        ImageIcon end = new ImageIcon("assets/BackgroundGame/Hollow.jpg");

        System.out.println("Background width: " + end.getIconWidth());
        System.out.println("Background height: " + end.getIconHeight());

        endGameBackground = end.getImage();
    }

    private void addTitle() {
        GlowLabel title = new GlowLabel("Game Finished");

        title.setForeground(new Color(40, 38, 52));
        title.setOutlineColor(new Color(255, 255, 255));
        title.setGlowColor(new Color(255, 255, 255));
        title.setFont(loadFont("/Font/Neverwinter Bold.otf", 130f));
        title.setBorder(BorderFactory.createEmptyBorder(50, 0, 10, 0));
        title.setOpaque(false);

        add(title, BorderLayout.NORTH);
    }

    private void addCenterContent(final EndScoreActions actions) {
        JPanel center = new JPanel(new GridLayout(4, 1, 12, 12));

        center.setOpaque(false);
        center.setBorder(BorderFactory.createEmptyBorder(70, 360, 100, 360));

        scoreLabel = createScoreLabel();
        highScoreLabel = createHighScoreLabel();

        EndGameButton playAgainButton = createButton("play again");
        EndGameButton mainMenuButton = createButton("main menu");

        playAgainButton.addActionListener(e -> actions.startGame());
        mainMenuButton.addActionListener(e -> actions.showMainMenu());

        center.add(scoreLabel);
        center.add(highScoreLabel);
        center.add(playAgainButton);
        center.add(mainMenuButton);

        add(center, BorderLayout.CENTER);
    }

    private JLabel createScoreLabel() {
        JLabel label = new JLabel("score: 0", SwingConstants.CENTER);

        label.setForeground(new Color(0, 0, 0));
        label.setFont(loadFont("/Font/Neverwinter Bold.otf", 110f));
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        label.setOpaque(false);

        return label;
    }

    private JLabel createHighScoreLabel() {
        JLabel label = new JLabel("highscore: 0", SwingConstants.CENTER);

        label.setForeground(new Color(0, 0, 0));
        label.setFont(loadFont("/Font/Neverwinter Bold.otf", 85f));
        label.setBorder(BorderFactory.createEmptyBorder(-20, 0, 10, 0));
        label.setOpaque(false);

        return label;
    }

    private EndGameButton createButton(String text) {
        EndGameButton button = new EndGameButton(text);

        button.setFont(loadFont("/Font/Neverwinter Bold.otf", 75f));

        return button;
    }

    private Font loadFont(String path, float size) {
        return FontLoader.loadFont(getClass(), path, size);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (endGameBackground != null) {
            g.drawImage(endGameBackground, 0, 0, getWidth(), getHeight(), this);
        }
    }

    @Override
    public void setScore(int score, int highScore) {
        scoreLabel.setText("score: " + score);
        highScoreLabel.setText("highscore: " + highScore);
    }
}