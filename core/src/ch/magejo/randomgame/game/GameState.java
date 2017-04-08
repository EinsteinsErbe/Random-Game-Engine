package ch.magejo.randomgame.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ch.magejo.randomgame.Main;
import ch.magejo.randomgame.mecanics.entity.creatures.Creature;
import ch.magejo.randomgame.mecanics.entity.creatures.charakters.Charakter;

/**
 * Holds the actual screen of the gamestate. If the screen is changed within the gamestate the other screens
 * are still open in the background and get not deleted
 * @author M.Geissbberger
 *
 */
public class GameState {
	private Main game;
	private Screen activeScreen;
	private RunningGameScreen runningGame;		//running Game gets only created and deleted with the gameState
	private DialogScreen	dialog;

	public GameState(Main game) {
		this.game = game;
		runningGame = new RunningGameScreen(game);
		activeScreen = runningGame;
	}

	public Screen getActiveScreen(){
		return activeScreen;
	}

	public void changeActiveGameScreen(Screen gameScreen){
		activeScreen = null;
		activeScreen = gameScreen;
		game.setScreen(activeScreen);
	}

	public void unpauseGame(){
		activeScreen = null;
		activeScreen = runningGame;
		runningGame.reSetInputFocusOnGame();
		game.setScreen(activeScreen);
	}

	public TextureRegion makeScreenShot(boolean darkedOverlay){
		return runningGame.makeScreenshot(darkedOverlay);
	}

	public DialogScreen openDialog(Creature target, Charakter player){
		dialog = new DialogScreen(game, game.getGameState().makeScreenShot(false), target, player);
		return dialog;
	}

	public DialogScreen openLastDialog(){
		dialog.reopenDialog();
		return dialog;
	}
}
