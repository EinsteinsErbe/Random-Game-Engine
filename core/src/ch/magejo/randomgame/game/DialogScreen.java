package ch.magejo.randomgame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import ch.magejo.randomgame.Main;
import ch.magejo.randomgame.gui.dialogs.DialogWindow;
import ch.magejo.randomgame.mecanics.entity.creatures.Creature;
import ch.magejo.randomgame.mecanics.entity.creatures.charakters.Charakter;
import ch.magejo.randomgame.mecanics.text.DialogManager;

public class DialogScreen implements Screen{
	
	private Main game;
	private DialogWindow dialog;

	public DialogScreen(Main game, Texture screenShot, Creature target, Charakter player) {
		this.game = game;
		this.dialog = new DialogWindow(game, screenShot);
		this.dialog.openDialog(target, player);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(DialogManager.isDialogOpen()){
			dialog.update();
			dialog.render(delta);
		}else{
			dialog.dispose();
			game.setScreen(new RunningGameScreen(game));
		}				
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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