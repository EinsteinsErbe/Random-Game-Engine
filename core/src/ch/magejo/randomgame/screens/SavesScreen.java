package ch.magejo.randomgame.screens;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.async.AsyncTask;

import ch.magejo.randomgame.Main;
import ch.magejo.randomgame.input.IconMenuStage;
import ch.magejo.randomgame.mecanics.text.ButtonNames;
import ch.magejo.randomgame.mecanics.world.World;
import ch.magejo.randomgame.utils.FileSystem;

public class SavesScreen extends abstractScreen{

	private IconMenuStage mainStage;

	private String clicked;

	public SavesScreen(Main game) {
		super(game);
		mainStage = new IconMenuStage(ButtonNames.Back, FileSystem.getSaveFiles(), game);
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

		if(mainStage.isClicked(ButtonNames.Back)){
			changeScreen(ScreenList.MainMenu, mainStage);
		}

		clicked = mainStage.getClicked();

		if(clicked != null){
			File f = FileSystem.getSaveFile(clicked, World.WORLDFILE);
			if(f.exists()){
				changeViaLoadScreen(new LoadingScreen(game, ScreenList.Game, true, new AsyncTask<Void>() {
					@Override
					public Void call() throws Exception {
						game.setWorld(f);
						game.getWorld().load();
						return null;
					}
				}), mainStage);
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
