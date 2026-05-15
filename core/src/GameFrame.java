import javax.swing.JFrame;

public class GameFrame extends JFrame implements EndScoreActions {
    private Interface endPanel;

    public GameFrame() {
        setTitle("End Background");
        setSize(1112, 803);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        endPanel = new Interface(this);
        endPanel.setScore(0, 0);

        setContentPane(endPanel);
        setVisible(true);
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