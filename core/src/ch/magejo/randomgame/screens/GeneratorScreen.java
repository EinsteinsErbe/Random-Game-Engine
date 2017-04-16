package ch.magejo.randomgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.async.AsyncTask;

import ch.magejo.randomgame.Main;
import ch.magejo.randomgame.input.MenuStage;
import ch.magejo.randomgame.input.RGTextField;
import ch.magejo.randomgame.mecanics.text.ButtonNames;
import ch.magejo.randomgame.mecanics.world.World;
import ch.magejo.randomgame.utils.FileSystem;
import ch.magejo.randomgame.utils.math.CustomRandom;

public class GeneratorScreen extends BaseScreen{

	private MenuStage mainStage;

	private RGTextField seedField;
	private RGTextField nameField;

	private CustomRandom random;

	private int seedFieldAddres;
	private int nameFieldAddres;

	public GeneratorScreen(Main game) {
		super(game);

		random = new CustomRandom((long) ((Math.random()*Long.MAX_VALUE)));

		ButtonNames[] buttons = {ButtonNames.Generate, ButtonNames.Advanced, ButtonNames.Refresh, ButtonNames.Back};

		mainStage = new MenuStage(buttons, game);

		seedField = new RGTextField("" + random.nextLong());
		nameField = new RGTextField("New world");

		seedField.addListener(new ClickListener(){
			public void clicked(InputEvent e, float x, float y) {
				seedField.setText("");
			}
		});
		nameField.addListener(new ClickListener(){
			public void clicked(InputEvent e, float x, float y) {
				nameField.setText("");
			}
		});

		float textFieldwidth = (game.getScreenSize().x/2);
		float textFieldHeight = 100;

		seedField.setBounds((game.getScreenSize().x/2) - textFieldwidth /2, game.getScreenSize().y-140, textFieldwidth, textFieldHeight);
		nameField.setBounds((game.getScreenSize().x/2) - textFieldwidth /2, game.getScreenSize().y-320, textFieldwidth, textFieldHeight);

		seedFieldAddres = mainStage.addTextbox(seedField);
		nameFieldAddres = mainStage.addTextbox(nameField);
	}

	@Override
	public void show() {}

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

		if(mainStage.isClicked(ButtonNames.Advanced)){

		}

		if(mainStage.isClicked(ButtonNames.Refresh)){
			mainStage.getTextField(seedFieldAddres).setText("" + random.nextLong());
		}

		if(mainStage.isClicked(ButtonNames.Generate)){
			String name = mainStage.getTextField(nameFieldAddres).getText();
			if(name.isEmpty()){
				return;
			}
			long seed;
			try {
				seed = Long.parseLong(mainStage.getTextField(seedFieldAddres).getText());
			} catch (Exception e) {
				game.logError("seed <" + mainStage.getTextField(seedFieldAddres).getText() + "> isn't a valuable seed!", getClass().getName(), 1);
				return;
			}
			changeViaLoadScreen(new LoadingScreen(game, ScreenList.Game, true, new AsyncTask<Void>() {
				@Override
				public Void call() throws Exception {
					try {
						game.logInfo("generate new world with seed: " + seed, getClass().getName(), 1);
						game.getGenerator().generate(name, seed);
						game.setWorld(FileSystem.getSaveFile(name, World.WORLDFILE));
						game.getWorld().load();
					} catch (Exception e) {
						e.printStackTrace();
						game.logError("GENERATOR ERROR", getClass().getName(), 1);
					}
					return null;
				}
			}), mainStage);
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
