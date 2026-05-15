public class SlimeEnemy extends Enemy {

    public SlimeEnemy(int x, int y) {
        super(x, y, 50, 10, 2, 10);
    }

    @Override
    public void move() {
        System.out.println("Slime moves slowly");
    }

    @Override
    public void attack() {
        System.out.println("Slime attacks player");
    }
}