package io.mygdx.soulknight.Sprites;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.Vector2;
import io.mygdx.soulknight.screens.PlayScreen;
import io.mygdx.soulknight.Tools.Contactable;
import io.mygdx.soulknight.Tools.Info;

public class WinArea implements Contactable {
    private final Info info;
    private final PlayScreen playScreen;

    public WinArea(World world, PlayScreen playScreen){
        this(world, playScreen, new Vector2(896, 136), new Vector2(16, 8));
    }

    public WinArea(World world, PlayScreen playScreen, Vector2 center, Vector2 halfSize){

        this.playScreen = playScreen;

        Body b2body;
        BodyDef bdef = new BodyDef();
        bdef.position.set(center);
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(halfSize.x, halfSize.y);

        fdef.shape = shape;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);
        shape.dispose();

        info = new Info("win");
    }

    @Override
    public void onContact(Contactable object) {
        Info objInfo = object.getInfo();

        if(objInfo != null) {
            if ("player".equals(objInfo.getType()))
                playScreen.win = true;
        }
    }

    @Override
    public void offContact(Contactable object) {

    }

    @Override
    public Info getInfo() {
        return info;
    }
}
