public abstract class Weapon {
    protected String name;
    protected int damage;

    public Weapon(String name, int damage) {
        this.name = name;
        this.damage = damage;
    }

    public abstract void shoot();

    public int getDamage() {
        return damage;
    }
}