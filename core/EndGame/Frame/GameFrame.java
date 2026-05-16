package source.Frame;

import source.Panel.EndGamePanel;
import source.Contract.EndScoreActions;

import javax.swing.JFrame;

public class GameFrame extends JFrame implements EndScoreActions {
    private EndGamePanel endPanel;

    public GameFrame() {
        setupFrame();
        showEndPanel();

        setVisible(true);
    }

    private void setupFrame() {
        setTitle("End Background");
        setSize(1112, 803);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void showEndPanel() {
        endPanel = new EndGamePanel(this);
        endPanel.setScore(0, 0);

        setContentPane(endPanel);
    }

    @Override
    public void startGame() {
        System.out.println("Play Again");
    }

    @Override
    public void showMainMenu() {
        System.out.println("Main Menu");
    }
}