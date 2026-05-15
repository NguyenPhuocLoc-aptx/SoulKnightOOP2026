public class GameData {
    private int hp;
    private int score;
    private int currentMap;

    public GameData(int hp, int score, int currentMap) {
        this.hp = hp;
        this.score = score;
        this.currentMap = currentMap;
    }

    public int getHp() {
        return hp;
    }

    public int getScore() {
        return score;
    }

    public int getCurrentMap() {
        return currentMap;
    }
}