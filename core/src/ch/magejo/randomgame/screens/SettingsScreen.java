package ch.magejo.randomgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import ch.magejo.randomgame.Main;
import ch.magejo.randomgame.generator.text.Language;
import ch.magejo.randomgame.input.MenuStage;
import ch.magejo.randomgame.mecanics.text.ButtonNames;
import ch.magejo.randomgame.utils.FileSystem;

public class SettingsScreen extends abstractScreen{

	private MenuStage mainStage;
	private static int languageID = 0;

	public SettingsScreen(Main game) {
		super(game);

		ButtonNames[] buttons = {ButtonNames.Controlls, ButtonNames.Language, ButtonNames.Sound, ButtonNames.Grafics, ButtonNames.Clear, ButtonNames.Back};

		setLanguage(Language.values()[languageID]);
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

		if(mainStage.isClicked(ButtonNames.Language)){
			languageID++;
			if(languageID >= Language.values().length){
				languageID = 0;
			}
			setLanguage(Language.values()[languageID]);
			mainStage.init();
		}

		if(mainStage.isClicked(ButtonNames.Clear)){
			FileSystem.deleteRootFolder();
		}

		if(mainStage.isClicked(ButtonNames.Back)){
			changeScreen(ScreenList.MainMenu, mainStage);
		}
	}

	private void setLanguage(Language l){
		game.getTextGenerator().setLanguage(l);
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
