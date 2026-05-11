package io.mygdx.soulknight.Sprites.Bullets;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import io.mygdx.soulknight.screens.PlayScreen;
import io.mygdx.soulknight.Sprites.Monster.Monster;
import io.mygdx.soulknight.Tools.Contactable;
import io.mygdx.soulknight.Tools.Info;

public class PlayerBullet extends Bullet{

    public PlayerBullet(World world, Vector2 startPos, Vector2 direction, int damage, int range) {
        super((short) 4, (short) (1 | 8), world, startPos, direction, damage, range);

        info = new Info("bullet");
    }

    @Override
    public void onContact(Contactable object) {
        if (!setToDestroy) {
            markToDestroy();
        }

        Info objInfo = object.getInfo();

        if(objInfo != null)
            if (objInfo.getType() == "monster")
                ((Monster) object).healthUpdate(damage);
    }
}
