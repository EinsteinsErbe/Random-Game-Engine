package ch.magejo.randomgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ch.magejo.randomgame.Main;
import ch.magejo.randomgame.game.GameState;
import ch.magejo.randomgame.generator.Generator;
import ch.magejo.randomgame.input.MenuStage;
import ch.magejo.randomgame.input.RGTextField;
import ch.magejo.randomgame.mecanics.text.ButtonNames;
import ch.magejo.randomgame.render.Renderer2D;
import ch.magejo.randomgame.utils.FileSystem;
import ch.magejo.randomgame.utils.SaveSystem;
import ch.magejo.randomgame.utils.math.CustomRandom;

public class GeneratorScreen extends abstractScreen{
	
	private Renderer2D renderer;

	private MenuStage mainStage;
	
	private RGTextField seedField;
	
	private CustomRandom random;
	
	private int seedFieldAddres;
	
	public GeneratorScreen(Main game) {
		super(game);
		
		random = new CustomRandom((long) ((Math.random()*Long.MAX_VALUE)));
		
		ButtonNames[] buttons = {ButtonNames.Generate, ButtonNames.Advanced, ButtonNames.Refresh, ButtonNames.Back};

		mainStage = new MenuStage(buttons, game, 100);
		
		seedField = new RGTextField("" + random.getNextSeed());
		
		seedField.addListener(new ClickListener(){
	        public void clicked(InputEvent e, float x, float y) {
	            seedField.setText("");
	        }
		});
		
		float textFieldwidth = (game.getScreenSize().x/2);
		float textFieldHeight = 100;
		
		seedField.setBounds((game.getScreenSize().x/2) - textFieldwidth /2, game.getScreenSize().y-200, textFieldwidth, textFieldHeight);
		
		seedFieldAddres = mainStage.addTextbox(seedField);
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
		game.getEventLogger().render(game.getBatch());
	}

	private void update(float delta) {
		mainStage.act();
		
		if(mainStage.isClicked(ButtonNames.Back)){
			changeScreen(ScreenList.MainMenu, mainStage);
		}
		
		if(mainStage.isClicked(ButtonNames.Refresh)){
			mainStage.getTextField(seedFieldAddres).setText("" + random.nextLong());
		}
		
		if(mainStage.isClicked(ButtonNames.Generate)){
			long seed;
			try {
				seed = Long.parseLong(mainStage.getTextField(seedFieldAddres).getText());
				game.logInfo("generate new world with seed: " + seed, getClass().getName(), 1);
				new Generator().generate("world" + seed, seed);
				game.setWorld(SaveSystem.load(FileSystem.getSaveFile("world" + seed, "world" + seed)));
				game.setGameState(new GameState(game));
				changeScreen(ScreenList.Game, mainStage);
				
			} catch (Exception e) {
				e.printStackTrace();
				game.logError("seed <" + mainStage.getTextField(seedFieldAddres).getText() + "> isn't a valuable seed!", getClass().getName(), 1);
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
