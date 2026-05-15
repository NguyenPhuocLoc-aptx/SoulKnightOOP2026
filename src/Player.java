public class Player {
    private String name;
    private int hp;
    private int score;

    public Player(String name) {
        this.name = name;
        this.hp = 100;
        this.score = 0;
    }

    public void addScore(int point) {
        score += point;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void showScore() {
        System.out.println("Player Score: " + score);
    }

    public void takeDamage(int damage) {
        hp -= damage;

        if (hp < 0) {
            hp = 0;
        }
    }

    public void heal(int amount) {
        hp += amount;

        if (hp > 100) {
            hp = 100;
        }
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void checkHP() {
        System.out.println("Player HP: " + hp);

        if (hp <= 0) {
            playerDied();
        }
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public void playerDied() {
        System.out.println(name + " died!");
        System.out.println("Game Over!");
    }
}