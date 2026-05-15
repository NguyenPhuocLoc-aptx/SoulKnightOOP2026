public class BossEnemy extends Enemy {

    public BossEnemy(int x, int y) {
        super(x, y, 200, 25, 1, 50);
    }

    @Override
    public void move() {
        System.out.println("Boss moves slowly");
    }

    @Override
    public void attack() {
        System.out.println("Boss uses strong attack");
    }
}