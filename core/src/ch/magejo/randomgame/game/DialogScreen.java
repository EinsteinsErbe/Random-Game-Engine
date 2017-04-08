package ch.magejo.randomgame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ch.magejo.randomgame.Main;
import ch.magejo.randomgame.gui.dialogs.DialogWindow;
import ch.magejo.randomgame.mecanics.entity.creatures.Creature;
import ch.magejo.randomgame.mecanics.entity.creatures.charakters.Charakter;
import ch.magejo.randomgame.mecanics.text.DialogManager;
import ch.magejo.randomgame.mecanics.trade.TradeManager;

public class DialogScreen implements Screen{

	private Main game;
	private DialogWindow dialog;
	private Creature target;
	private Charakter player;

	public DialogScreen(Main game, TextureRegion screenShot, Creature target, Charakter player) {
		this.game = game;
		this.target = target;
		this.player = player;
		this.dialog = new DialogWindow(game, screenShot);
		game.addEvent("opened Dialog with " + target.getName(), new Color(0.2f, 0.75f, 0.2f, 0.8f));
		this.dialog.openDialog(target, player);
	}

	public void reopenDialog(){
		dialog.reSetInputMultiplexer();
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
			if(TradeManager.isOpen()){
				dialog.dispose();
				game.addEvent("closed Dialog", new Color(0.2f, 0.75f, 0.2f, 0.8f));
				game.getGameState().changeActiveGameScreen(new TradeScreen(game, game.getGameState().makeScreenShot(true), (Charakter) target, player));
			}
		}else{
			dialog.dispose();
			game.addEvent("closed Dialog", new Color(0.2f, 0.75f, 0.2f, 0.8f));
			game.getGameState().unpauseGame();
		}

		game.getEventLogger().render(game.getBatch());
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