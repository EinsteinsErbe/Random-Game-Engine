package ch.magejo.randomgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ch.magejo.randomgame.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Title";
		config.width = 2048;
		config.height = 1440;
		config.fullscreen = true;
		new LwjglApplication(new Main(), config);
	}
}
