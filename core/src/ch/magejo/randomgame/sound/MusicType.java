package ch.magejo.randomgame.sound;

public enum MusicType {

	MENU(1f), OVERWORLD(1f), INTERIOR(1f);
	
	private float volume;
	
	MusicType(float volume){
		this.volume = volume;
	}
	
	public float getVolume(){
		return volume;
	}
	
	public void setVolume(float volume){
		this.volume = volume;
	}
}
