package io.mygdx.soulknight.Sprites.Bullets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.mygdx.soulknight.Tools.Contactable;
import io.mygdx.soulknight.Tools.Info;
import io.mygdx.soulknight.screens.PlayScreen;

import java.util.ArrayList;

public abstract class Bullet extends Sprite implements Contactable {
    protected Info info;
    protected Body bulletBody;
    protected FixtureDef fdef;
    protected World world;
    protected boolean setToDestroy;
    protected boolean destroyed;

    protected int damage;
    private int range;

    protected static ArrayList<Bullet> bullets = new ArrayList<>();
    private static SpriteBatch spriteBatch = new SpriteBatch();
    protected static Texture texture = new Texture("0x72_16x16DungeonTileset.v4.png");
    private static Sprite sprite = new Sprite(texture, 80, 128, 5, 5);

    public Bullet(short catBit, short maskBit, World world, Vector2 startPos, Vector2 direction, int damage, int range){
        super(sprite);
        this.world = world;
        this.damage = damage;
        this.range = range;

        BodyDef bulletDef = new BodyDef();
        bulletDef.position.set(startPos);

        bulletDef.type = BodyDef.BodyType.DynamicBody;
        bulletBody = world.createBody(bulletDef);

        fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(1);
        fdef.filter.categoryBits = catBit;
        fdef.filter.maskBits = maskBit;

        fdef.shape = shape;
        fdef.isSensor = true;
        bulletBody.createFixture(fdef).setUserData(this);

        bulletBody.setLinearVelocity(direction);

        bullets.add(this);
    }

    public void update(){
        if (destroyed) {
            return;
        }
        setPosition(bulletBody.getWorldCenter().x - 2.5f, bulletBody.getWorldCenter().y - 2.5f);
    }

    protected void markToDestroy() {
        setToDestroy = true;
    }

    protected void updateDestroyState() {
        if (setToDestroy && !destroyed && !world.isLocked() && bulletBody != null) {
            world.destroyBody(bulletBody);
            bulletBody = null;
            destroyed = true;
        }
    }

    public abstract void onContact(Contactable object);

    public static void render(){
        spriteBatch.setProjectionMatrix(PlayScreen.getCamera().combined);

        spriteBatch.begin();
        for(Bullet bullet : bullets) {
            if (!bullet.destroyed && !bullet.setToDestroy) {
                bullet.draw(spriteBatch);
            }
        }
        spriteBatch.end();
    }

    public static void updateAll(){
        for (Bullet bullet : bullets) {
            bullet.updateDestroyState();
            if (!bullet.destroyed && !bullet.setToDestroy) {
                bullet.update();
            }
        }
        bullets.removeIf(bullet -> bullet.destroyed);
    }

    public static void destroyAll(){
        for(Bullet bullet : bullets) {
            bullet.markToDestroy();
        }
    }

    public static void clearAll(){
        bullets.clear();
    }

    @Override
    public void offContact(Contactable object) {

    }

    @Override
    public Info getInfo() {
        return info;
    }
}
