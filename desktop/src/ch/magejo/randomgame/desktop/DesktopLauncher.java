package ch.magejo.randomgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ch.magejo.randomgame.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Random Game";
		config.vSyncEnabled = false;
		config.foregroundFPS = 60;
		config.backgroundFPS = 15;
		config.width = 1024;
		config.height = 720;
		config.fullscreen = true;
		new LwjglApplication(new Main(), config);
	}
}
