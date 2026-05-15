package soulknight;

public class GameManager {
    // ========== ENUM ==========
    public enum GameState {
        MENU, PLAYING, PAUSE, GAME_OVER
    }

    // ========== SINGLETON PATTERN ==========
    private static GameManager instance;

    private boolean isGameRunning;
    private GameState gameState;

    // Private constructor
    private GameManager() {
        isGameRunning = true;
        gameState = GameState.PLAYING;
    }

    // Get instance
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    // ========== METHODS ==========
    public void setGameRunning(boolean running) {
        this.isGameRunning = running;
    }

    public void setGameState(GameState state) {
        this.gameState = state;
    }

    public boolean isGameRunning() {
        return isGameRunning && gameState == GameState.PLAYING;
    }

    public GameState getGameState() {
        return gameState;
    }
}