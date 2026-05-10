package io.mygdx.soulknight.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Central place to load textures once. Replace the file names with your own assets.
 */
public class Assets {
    public static final Texture playerTexture   = new Texture(Gdx.files.internal("player.png"));
    public static final Texture enemyTexture    = new Texture(Gdx.files.internal("enemy.png"));
    public static final Texture bulletTexture   = new Texture(Gdx.files.internal("bullet.png"));
    public static final Texture blankTexture    = new Texture(Gdx.files.internal("blank.png")); // 1×1 white pixel
}
