import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;

public class FontLoader {
    public static Font loadFont(Class<?> currentClass, String path, float size) {
        try {
            InputStream is = currentClass.getResourceAsStream(path);

            if (is == null) {
                System.out.println("Font is not found: " + path);
                return new Font("Arial", Font.BOLD, (int) size);
            }

            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);

            return font.deriveFont(Font.BOLD, size);
        } catch (Exception e) {
            e.printStackTrace();
            return new Font("Arial", Font.BOLD, (int) size);
        }
    }
}