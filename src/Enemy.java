public abstract class Enemy extends Character {
    protected int point;

    public Enemy(int x, int y, int hp, int damage, int speed, int point) {
        super(x, y, hp, damage, speed);
        this.point = point;
    }

    public int getPoint() {
        return point;
    }
}