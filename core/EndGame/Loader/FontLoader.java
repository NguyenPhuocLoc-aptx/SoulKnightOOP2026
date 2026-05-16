package source.Loader;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;

public class FontLoader {
    private FontLoader() {
    }

    public static Font loadFont(Class<?> clazz, String path, float size) {
        try {
            InputStream inputStream = getFontStream(clazz, path);
            if (inputStream == null) {
                return getDefaultFont(path, size);
            }
            Font font = createFont(inputStream);
            registerFont(font);
            System.out.println("Font is got: " + font.getFontName());
            return font.deriveFont(Font.BOLD, size);
        } catch (Exception e) {
            return handleFontError(path, size, e);
        }
    }

    private static InputStream getFontStream(Class<?> clazz, String path) {
        return clazz.getResourceAsStream(path);
    }

    private static Font createFont(InputStream inputStream) throws Exception {
        return Font.createFont(Font.TRUETYPE_FONT, inputStream);
    }

    private static void registerFont(Font font) {
        GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .registerFont(font);
    }

    private static Font getDefaultFont(String path, float size) {
        System.out.println("Font is not found: " + path);
        return new Font("Arial", Font.BOLD, (int) size);
    }

    private static Font handleFontError(String path, float size, Exception e) {
        System.out.println("Font error: " + path);
        e.printStackTrace();
        return new Font("Arial", Font.BOLD, (int) size);
    }
}