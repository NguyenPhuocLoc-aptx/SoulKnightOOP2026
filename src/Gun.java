public class Gun extends Weapon {

    public Gun(String name, int damage) {
        super(name, damage);
    }

    @Override
    public void shoot() {
        System.out.println(name + " shoots a bullet");
    }
}