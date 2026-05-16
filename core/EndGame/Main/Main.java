package source.Main;
import javax.swing.SwingUtilities;
import source.Frame.GameFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GameFrame();
        });
    }
}