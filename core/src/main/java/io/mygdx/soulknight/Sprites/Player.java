package io.mygdx.soulknight.Sprites;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import io.mygdx.soulknight.Tools.Contactable;
import io.mygdx.soulknight.Tools.Gun;
import io.mygdx.soulknight.Tools.Info;

public class Player extends Sprite implements Contactable {
    public World world;
    public Body b2body;
    private final OrthographicCamera camera;
    public static Vector2 currentPos;
    private final Vector2 startPos;

    private final Vector3 mousePos;

    private final SpriteBatch spriteBatch = new SpriteBatch();
    private final Texture texture = new Texture("Knight_Monster.png");
    private final Sprite sprite = new Sprite(texture, 91, 15, 16, 16);

    private final Info info;

    private float timer;
    private final float ICD = 0.6f;
    private float ICDTimer;
    private boolean isAttacked = false;

    public int health = 10;
    private float switchTimer = 0;
    private Gun currentGun;
    private final Pistol pistol = new Pistol();
    private final Shotgun shotgun = new Shotgun();

    public boolean gameOver = false;

    public Player (World world, Vector3 mousePos, OrthographicCamera camera){
        this(world, mousePos, camera, new Vector2(100, 100));
    }

    public Player (World world, Vector3 mousePos, OrthographicCamera camera, Vector2 startPos){
        this.world = world;
        this.mousePos = mousePos;
        this.camera = camera;
        this.startPos = new Vector2(startPos);

        defineCharacter();

        info = new Info("player");

        currentGun = pistol;
    }

    @Override
    public Info getInfo() {
        return info;
    }

    public int getHealth(){
        return health;
    }

    public void setAttacked(boolean attacked) {
        isAttacked = attacked;
    }

    public boolean getGameOver(){
        return gameOver;
    }

    public void defineCharacter(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(startPos);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);
        currentPos = b2body.getWorldCenter();

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7);

        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = 2;

        b2body.createFixture(fixtureDef).setUserData(this);
    }

    public void update(float dt) {
        currentPos = b2body.getWorldCenter();
        setPosition(currentPos.x, currentPos.y);

        if(!gameOver) {
            handleInput();
            attack(dt);
            takeDamage(dt);
            switchWeapon(dt);
        }
    }

    public void render(){
        spriteBatch.setProjectionMatrix(camera.combined);
        sprite.setPosition(currentPos.x - 8, currentPos.y - 8);

        spriteBatch.begin();
        sprite.draw(spriteBatch);
        spriteBatch.end();
    }

    //  the camera will follow the knight
    public void handleInput(){
        final float SPEED_ACCELERATION = 16f;
        final float MAX_SPEED = 85f;

        Vector2 currentSpeed = b2body.getLinearVelocity();

        boolean dKeyPressed = Gdx.input.isKeyPressed(Input.Keys.D);
        boolean sKeyPressed = Gdx.input.isKeyPressed(Input.Keys.S);
        boolean aKeyPressed = Gdx.input.isKeyPressed(Input.Keys.A);
        boolean wKeyPressed = Gdx.input.isKeyPressed(Input.Keys.W);

        b2body.setLinearDamping(10);

        if (dKeyPressed && currentSpeed.x <= MAX_SPEED)
            b2body.applyLinearImpulse(new Vector2(SPEED_ACCELERATION, 0), currentPos, true);

        if (aKeyPressed && currentSpeed.x >= -MAX_SPEED)
            b2body.applyLinearImpulse(new Vector2(-SPEED_ACCELERATION, 0), currentPos, true);

        if (wKeyPressed && currentSpeed.y <= MAX_SPEED)
            b2body.applyLinearImpulse(new Vector2(0, SPEED_ACCELERATION), currentPos, true);

        if (sKeyPressed && currentSpeed.y >= -MAX_SPEED)
            b2body.applyLinearImpulse(new Vector2(0, -SPEED_ACCELERATION), currentPos, true);
    }

    void attack(float dt){
        timer += dt;

        if(Gdx.input.isTouched() && timer > currentGun.getFirerate() && !gameOver){
            timer = 0;
            currentGun.fire(world, currentPos, new Vector2(mousePos.x, mousePos.y).scl(-1).add(currentPos).scl(-1).nor());
        }
    }

    void switchWeapon(float dt){
        switchTimer += dt;

        if(Gdx.input.isKeyPressed(Input.Keys.Q) && switchTimer > 0.2f) {
            switchTimer = 0;

            if (currentGun instanceof Shotgun)
                currentGun = pistol;
            else
                currentGun = shotgun;
        }
    }

    void takeDamage(float dt){
        ICDTimer += dt;
        if(isAttacked && ICDTimer > ICD){
            healthUpdate(1);
            ICDTimer = 0;
        }
    }

    public void healthUpdate(int damage){
        if(health > 0) {
            health -= damage;
        }

        if(health == 0)
            gameOver();
    }

    public void gameOver(){
        gameOver = true;
    }

    @Override
    public void onContact(Contactable object) {
        Info objInfo = object.getInfo();

        if(objInfo != null) {
            if ("monster".equals(objInfo.getType()))
                setAttacked(true);

            if("enemyBullet".equals(objInfo.getType()))
                healthUpdate(1);
        }
    }

    @Override
    public void offContact(Contactable object) {
        Info objInfo = object.getInfo();

        if(objInfo != null) {
            if ("monster".equals(objInfo.getType()))
                setAttacked(false);
        }
    }
}
