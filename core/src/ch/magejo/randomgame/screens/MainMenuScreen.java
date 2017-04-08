package ch.magejo.randomgame.screens;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import ch.magejo.randomgame.Main;
import ch.magejo.randomgame.game.GameState;
import ch.magejo.randomgame.input.MenuStage;
import ch.magejo.randomgame.mecanics.places.World;
import ch.magejo.randomgame.mecanics.text.ButtonNames;
import ch.magejo.randomgame.render.Renderer2D;
import ch.magejo.randomgame.utils.FileSystem;
import ch.magejo.randomgame.utils.SaveSystem;

public class MainMenuScreen extends abstractScreen{

	private MenuStage mainStage;

	public MainMenuScreen(Main game) {
		super(game);

		ButtonNames[] buttons = {ButtonNames.Continue, ButtonNames.Generator, ButtonNames.Load, ButtonNames.Settings, ButtonNames.Close};

		mainStage = new MenuStage(buttons, game);
	}

	@Override
	public void show() {}

	@Override
	public void render(float delta) {
		update(delta);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		mainStage.render();
	}

	private void update(float delta) {
		mainStage.act();

		if(mainStage.isClicked(ButtonNames.Continue)){
			File file = game.getLastWorld();
			if(file!=null){
				if(file.exists()){
					game.setWorld(file);
					game.setGameState(new GameState(game));
					changeScreen(ScreenList.Game, mainStage);
				}
			}
		}

		if(mainStage.isClicked(ButtonNames.Load)){
			changeScreen(ScreenList.Saves, mainStage);
		}

		if(mainStage.isClicked(ButtonNames.Generator)){
			changeScreen(ScreenList.Generator, mainStage);
		}

		if(mainStage.isClicked(ButtonNames.Settings)){
			changeScreen(ScreenList.Settings, mainStage);
		}

		if(mainStage.isClicked(ButtonNames.Close)){
			game.getInputMultiplexer().removeProcessor(mainStage);
			mainStage.dispose();
			game.dispose();
			System.exit(0);
		}
	}

	@Override
	public void resize(int width, int height) {
		mainStage.init();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
