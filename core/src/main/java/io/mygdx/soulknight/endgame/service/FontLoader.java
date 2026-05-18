package io.mygdx.soulknight.endgame.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontLoader implements FontService {

    @Override
    public BitmapFont loadFont(String path, float size) {
        try {
            String fixedPath = fixPath(path);
            FileHandle fontFile = Gdx.files.internal(fixedPath);

            if (!fontFile.exists()) {
                return getDefaultFont(path, size);
            }

            BitmapFont font = createFont(fontFile, size);
            System.out.println("Font is got: " + fixedPath);
            return font;
        } catch (Exception e) {
            return handleFontError(path, size, e);
        }
    }

    private String fixPath(String path) {
        if (path.startsWith("/")) {
            return path.substring(1);
        }
        return path;
    }

    private BitmapFont createFont(FileHandle fontFile, float size) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
            new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = (int) size;

        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();

        return font;
    }

    private BitmapFont getDefaultFont(String path, float size) {
        System.out.println("Font is not found: " + path);

        BitmapFont font = new BitmapFont();
        font.getData().setScale(size / 15f);

        return font;
    }

    private BitmapFont handleFontError(String path, float size, Exception e) {
        System.out.println("Font error: " + path);
        e.printStackTrace();

        BitmapFont font = new BitmapFont();
        font.getData().setScale(size / 15f);

        return font;
    }
}
