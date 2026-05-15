import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class SaveManager {
    private String fileName;

    public SaveManager(String fileName) {
        this.fileName = fileName;
    }

    public void saveGame(GameData data) {
        try {
            FileWriter writer = new FileWriter(fileName);

            writer.write(data.getHp() + "\n");
            writer.write(data.getScore() + "\n");
            writer.write(data.getCurrentMap() + "\n");

            writer.close();

            System.out.println("Game saved successfully.");
        } catch (Exception e) {
            System.out.println("Cannot save game.");
        }
    }

    public GameData loadGame() {
        try {
            File file = new File(fileName);
            Scanner sc = new Scanner(file);

            int hp = sc.nextInt();
            int score = sc.nextInt();
            int currentMap = sc.nextInt();

            sc.close();

            System.out.println("Game loaded successfully.");

            return new GameData(hp, score, currentMap);
        } catch (Exception e) {
            System.out.println("Cannot load game.");
            return new GameData(100, 0, 1);
        }
    }
    public static int loadHighestScore() {
        try {
            File file = new File("save/highestscore.txt");
            if (file.exists()) {
                java.util.Scanner scanner = new java.util.Scanner(file);
                int score = scanner.nextInt();
                scanner.close();
                return score;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void saveHighestScore(int score) {
        try {
            File dir = new File("save");
            if (!dir.exists()) dir.mkdir();

            java.io.PrintWriter writer = new java.io.PrintWriter("save/highestscore.txt");
            writer.print(score);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}