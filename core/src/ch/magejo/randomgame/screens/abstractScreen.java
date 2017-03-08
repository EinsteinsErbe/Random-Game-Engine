package ch.magejo.randomgame.screens;

import com.badlogic.gdx.Screen;

import ch.magejo.randomgame.Main;
import ch.magejo.randomgame.input.MenuStage;

public abstract class abstractScreen implements Screen{
	
	protected Main game;
	
	public abstractScreen(Main game){
		this.game = game;
	}
	
	protected void changeScreen(ScreenList screen, MenuStage mainStage){
		game.getInputMultiplexer().removeProcessor(mainStage);
		mainStage.dispose();
		game.changeScreen(screen);
	}
}
