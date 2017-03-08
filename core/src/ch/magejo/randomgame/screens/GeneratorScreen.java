package ch.magejo.randomgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import ch.magejo.randomgame.Main;
import ch.magejo.randomgame.input.MenuStage;
import ch.magejo.randomgame.mecanics.text.ButtonNames;
import ch.magejo.randomgame.render.Renderer2D;

public class GeneratorScreen extends abstractScreen{
	
	private Renderer2D renderer;

	private MenuStage mainStage;
	
	public GeneratorScreen(Main game) {
		super(game);
		
		ButtonNames[] buttons = {ButtonNames.Advanced, ButtonNames.Refresh, ButtonNames.Back};

		mainStage = new MenuStage(buttons, game);
		mainStage.open();
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
