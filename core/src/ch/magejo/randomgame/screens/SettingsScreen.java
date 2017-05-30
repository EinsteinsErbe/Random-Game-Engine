package ch.magejo.randomgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import ch.magejo.randomgame.Main;
import ch.magejo.randomgame.generator.text.Language;
import ch.magejo.randomgame.input.MenuStage;
import ch.magejo.randomgame.mecanics.input.Key;
import ch.magejo.randomgame.mecanics.sound.Sounds;
import ch.magejo.randomgame.mecanics.text.ButtonNames;
import ch.magejo.randomgame.utils.FileSystem;
import ch.magejo.randomgame.utils.Log;
import ch.magejo.randomgame.utils.SaveSystem;

public class SettingsScreen extends BaseScreen{

	private MenuStage mainStage;
	private int languageID;

	public SettingsScreen(Main game) {
		super(game);

		ButtonNames[] buttons = {ButtonNames.Controlls, ButtonNames.Language, ButtonNames.Sound, ButtonNames.Grafics, ButtonNames.Clear, ButtonNames.Back};

		mainStage = new MenuStage(buttons, game);

		languageID = game.getTextGenerator().getLanguage().ordinal();
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
		
		if(game.getInputManager().isClicked(Key.ATTACK)){
			float newVolume = Sounds.getVolume();
			if(newVolume < 1){
				newVolume += 0.1f;
			}else{
				newVolume = 1;
			}			
			Sounds.setVolume(newVolume);
		}
		
		if(game.getInputManager().isClicked(Key.BLOCK)){
			float newVolume = Sounds.getVolume();
			if(newVolume > 0){
				newVolume -= 0.1f;
			}else{
				newVolume = 0;
			}				
			Sounds.setVolume(newVolume);
		}
		
		if(game.getInputManager().isSelecting){
			return;
		}

		mainStage.act();

		if(mainStage.isClicked(ButtonNames.Controlls)){
			game.getInputManager().setKey(Key.UP);
		}

		if(mainStage.isClicked(ButtonNames.Language)){
			languageID++;
			if(languageID >= Language.values().length){
				languageID = 0;
			}
			Language l = Language.values()[languageID];
			game.getTextGenerator().setLanguage(l);
			SaveSystem.save(l, FileSystem.createFile("lang.cfg"));
			mainStage.init();
		}

		if(mainStage.isClicked(ButtonNames.Clear)){
			FileSystem.deleteRootFolder();
		}

		if(mainStage.isClicked(ButtonNames.Back)){
			changeScreen(ScreenList.MainMenu, mainStage);
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
