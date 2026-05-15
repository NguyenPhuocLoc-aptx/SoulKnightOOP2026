import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GameFrame extends JFrame {
    private GamePanel gamePanel;

    public GameFrame() {
        setTitle("Mini Soul Knight OOP");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        gamePanel = new GamePanel();
        add(gamePanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gamePanel.requestFocusInWindow();
            }
        });
    }
}