import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame menuFrame = new JFrame("Soul Knight Clone - Main Menu");
            MainMenuPanel menuPanel = new MainMenuPanel(menuFrame);
            menuFrame.add(menuPanel);
            menuFrame.setSize(800, 600);
            menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            menuFrame.setLocationRelativeTo(null);
            menuFrame.setVisible(true);
            menuFrame.setResizable(false);
        });
    }
}