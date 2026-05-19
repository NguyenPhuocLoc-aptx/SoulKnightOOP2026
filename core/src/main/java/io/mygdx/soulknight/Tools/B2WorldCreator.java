package io.mygdx.soulknight.Tools;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class B2WorldCreator implements Contactable{
    private static final String[] DEFAULT_COLLISION_LAYERS = {"Obstacle", "Walls"};

    private final World world;
    private final TiledMap map;

    private final Info info = new Info("wall");

    public B2WorldCreator(World world, TiledMap map){
        this(world, map, DEFAULT_COLLISION_LAYERS);
    }

    public B2WorldCreator(World world, TiledMap map, String... collisionLayerNames){
        this.world = world;
        this.map = map;

        for (String layerName : collisionLayerNames) {
            createRectangle(layerName);
        }
    }

    void createRectangle(String layerName){
        for (MapLayer layer : map.getLayers()) {
            if (!layerName.equals(layer.getName())) {
                continue;
            }

            BodyDef bdef = new BodyDef();
            PolygonShape shape = new PolygonShape();
            FixtureDef fdef = new FixtureDef();
            Body body;

            for(RectangleMapObject object : layer.getObjects().getByType(RectangleMapObject.class)){
                Rectangle rect = object.getRectangle();
                float rotationDegrees = getRotationDegrees(object);
                Vector2 bodyCenter = getTiledRectangleCenter(rect, rotationDegrees);

                bdef.type = BodyDef.BodyType.StaticBody;
                bdef.position.set(bodyCenter);
                bdef.angle = -rotationDegrees * MathUtils.degreesToRadians;

                body = world.createBody(bdef);

                shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
                fdef.shape = shape;
                body.createFixture(fdef).setUserData(this);
            }

            shape.dispose();
        }
    }

    private static float getRotationDegrees(RectangleMapObject object) {
        Object rotationValue = object.getProperties().get("rotation");
        return rotationValue instanceof Number ? ((Number) rotationValue).floatValue() : 0f;
    }

    private static Vector2 getTiledRectangleCenter(Rectangle rect, float rotationDegrees) {
        Vector2 centerOffset = new Vector2(rect.getWidth() / 2, -rect.getHeight() / 2);
        centerOffset.rotateDeg(-rotationDegrees);
        return centerOffset.add(rect.getX(), rect.getY() + rect.getHeight());
    }

    @Override
    public void onContact(Contactable object) {

    }

    @Override
    public void offContact(Contactable object) {

    }

    @Override
    public Info getInfo() {
        return info;
    }
}
