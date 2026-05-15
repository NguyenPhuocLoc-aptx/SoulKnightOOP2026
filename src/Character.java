public abstract class Character {
    protected int x;
    protected int y;
    protected int hp;
    protected int damage;
    protected int speed;

    public Character(int x, int y, int hp, int damage, int speed) {
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.damage = damage;
        this.speed = speed;
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public void takeDamage(int damage) {
        hp -= damage;
        if (hp < 0) {
            hp = 0;
        }
    }

    public abstract void move();
    public abstract void attack();
}