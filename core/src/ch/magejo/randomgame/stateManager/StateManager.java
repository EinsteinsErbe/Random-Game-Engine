package ch.magejo.randomgame.stateManager;

import java.util.ArrayList;

import ch.magejo.randomgame.render.Renderer2D;
import ch.magejo.randomgame.stateManager.gameState.GameState;
import ch.magejo.randomgame.utils.Log;

public class StateManager {
	
	public static StateList activeState = StateList.MAINMEU;
	public static ArrayList<State> states = new ArrayList<State>();
	
	public StateManager() {
		states.add(new GameState());
	}
	
	public void update(float f){
		for(State s: states){
			if(activeState.equals(s.getState())){
				s.update(f);
			}
		}
	}
	
	public void render(Renderer2D renderer){
		for(State s: states){
			if(activeState.equals(s.getState())){
				s.render(renderer);
			}
		}
	}
	
	public static void changeState(StateList state){
		Log.printLn("changed to State: " + state.toString(), StateManager.class.getName(), 3);
		activeState = state;
	}

}
