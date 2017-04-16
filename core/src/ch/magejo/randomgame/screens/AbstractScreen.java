package ch.magejo.randomgame.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;

import ch.magejo.randomgame.Main;

public abstract class AbstractScreen implements Screen{

	protected Main game;

	public AbstractScreen(Main game){
		this.game = game;
	}

	protected void changeScreen(ScreenList screen, Stage stage){
		game.getInputMultiplexer().removeProcessor(stage);
		stage.dispose();
		game.changeScreen(screen);
	}

	protected void changeViaLoadScreen(Screen screen, Stage stage){
		game.getInputMultiplexer().removeProcessor(stage);
		stage.dispose();
		game.changeViaLoadScreen(screen);
	}
}
