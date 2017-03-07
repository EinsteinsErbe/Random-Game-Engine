package ch.magejo.randomgame.stateManager;

import ch.magejo.randomgame.render.Renderer2D;

public abstract class State {
	
	private StateList myState;
	
	public State(StateList myState) {
		this.myState = myState;
	}
	
	protected abstract void update(float f);
	
	protected abstract void render(Renderer2D renderer);
	
	public StateList getState(){
		return myState;
	}
}
