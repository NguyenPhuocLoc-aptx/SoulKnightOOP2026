package io.mygdx.soulknight.endgame.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class BackgroundImage {
    private Texture backgroundTexture;

    public BackgroundImage(String imagePath) {
        loadBackgroundImage(imagePath);
    }

    private void loadBackgroundImage(String imagePath) {
        String fixedPath = fixPath(imagePath);
        FileHandle imageFile = Gdx.files.internal(fixedPath);

        if (!imageFile.exists()) {
            System.out.println("Image is not found: " + imagePath);
            return;
        }

        backgroundTexture = new Texture(imageFile);
    }

    private String fixPath(String imagePath) {
        if (imagePath.startsWith("/")) {
            return imagePath.substring(1);
        }
        return imagePath;
    }

    public void draw(Batch batch, float width, float height) {
        if (backgroundTexture != null) {
            batch.draw(backgroundTexture, 0, 0, width, height);
        }
    }

    public void dispose() {
        if (backgroundTexture != null) {
            backgroundTexture.dispose();
        }
    }
}
