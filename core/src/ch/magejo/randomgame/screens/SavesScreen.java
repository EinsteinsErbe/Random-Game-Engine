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

public class SavesScreen extends abstractScreen{

	private Renderer2D renderer;

	private MenuStage mainStage;

	private String clicked;

	public SavesScreen(Main game) {
		super(game);
		mainStage = new MenuStage(ButtonNames.Back, FileSystem.getSaveFiles(), game);
	}

	@Override
	public void show() {
		mainStage.init();
	}

	@Override
	public void render(float delta) {
		update(delta);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		mainStage.render();
	}

	private void update(float delta) {
		mainStage.act();

		if(mainStage.isClicked(ButtonNames.Back)){
			changeScreen(ScreenList.MainMenu, mainStage);
		}

		clicked = mainStage.getClicked();

		if(clicked != null){
			File f = FileSystem.getSaveFile(clicked, World.WORLDFILE);
			if(f.exists()){
				game.setWorld(f);
				game.setGameState(new GameState(game));
				changeScreen(ScreenList.Game, mainStage);
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		mainStage.init();
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {}

}
