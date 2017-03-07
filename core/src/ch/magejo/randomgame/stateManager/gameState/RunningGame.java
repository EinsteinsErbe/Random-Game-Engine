package ch.magejo.randomgame.stateManager.gameState;

/**
 * The actual Game got coded here
 * @author M.Geissbberger
 *
 */
public class RunningGame{

	public RunningGame() {
		
	}
	
	public void update(float delta) {
		// if a window shall be opened -> set paused game
		if(false){
			GameState.pauseGame();
		}else{
			//run game!
		}
	}

	
	public void render() {
		//always render Game (in the Background)	
	}

}
