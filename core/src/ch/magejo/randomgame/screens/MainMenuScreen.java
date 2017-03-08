package ch.magejo.randomgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import ch.magejo.randomgame.Main;
import ch.magejo.randomgame.input.MenuStage;
import ch.magejo.randomgame.mecanics.text.ButtonNames;
import ch.magejo.randomgame.render.Renderer2D;

public class MainMenuScreen implements Screen{
	
	private Renderer2D renderer;

	private Main game;

	private MenuStage mainStage;
	
	public MainMenuScreen(Main game) {
		this.game = game;
		
		ButtonNames[] buttons = {ButtonNames.Continue, ButtonNames.Generator, ButtonNames.Load, ButtonNames.Settings, ButtonNames.Close};

		mainStage = new MenuStage(buttons, game);
		mainStage.open();
		mainStage.setDisabled(ButtonNames.Continue, true);
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		update(delta);
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		mainStage.render();		
	}

	private void update(float delta) {
		mainStage.act();
		
		if(mainStage.isClicked(ButtonNames.Load)){
			game.getInputMultiplexer().removeProcessor(mainStage);
			game.changeScreen(ScreenList.Game);
		}
	}

	@Override
	public void resize(int width, int height) {
		mainStage.init(game);		
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
