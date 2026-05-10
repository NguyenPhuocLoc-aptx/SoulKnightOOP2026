package io.mygdx.soulknight.lwjgl3;



import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
// ---- ĐÚNG ----
import io.mygdx.soulknight.GameMain;   // lớp GameMain nằm trong module core

public class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return;
        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new GameMain(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();

        // Thay thế cho config.title
        configuration.setTitle("Soul Knight test");

        // Thay thế cho config.width và config.height
        configuration.setWindowedMode(800, 600);

        // Cài đặt icon (nếu bạn đã có file trong thư mục icons)
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");

        return configuration;
    }
}
