package ch.magejo.randomgame.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import ch.magejo.randomgame.mecanics.sound.SFX;
import ch.magejo.randomgame.mecanics.sound.SoundPlayer;
import ch.magejo.randomgame.mecanics.sound.Sounds;

public class SoundManager implements SoundPlayer{

	private Sound[] sfx;
	private Music[] music;
	private Music runningMusic;
	private float masterVolume = 1;
	private MusicType runningMusicType = null;

	public SoundManager() {
		sfx = new Sound[SFX.values().length];
		sfx[SFX.ATTACK_1.ordinal()] = Gdx.audio.newSound(Gdx.files.internal("Sound/SFX/attack.wav"));
		sfx[SFX.DAMAGE_1.ordinal()] = Gdx.audio.newSound(Gdx.files.internal("Sound/SFX/hurt.wav"));
		sfx[SFX.CLICK_BTN.ordinal()] = Gdx.audio.newSound(Gdx.files.internal("Sound/SFX/klick.wav"));
		sfx[SFX.DIE.ordinal()] = Gdx.audio.newSound(Gdx.files.internal("Sound/SFX/die.wav"));
		music = new Music[MusicType.values().length];
		music[MusicType.MENU.ordinal()] = Gdx.audio.newMusic(Gdx.files.internal("Sound/Menu.mp3"));
		music[MusicType.OVERWORLD.ordinal()] = Gdx.audio.newMusic(Gdx.files.internal("Sound/Overworld.mp3"));
		music[MusicType.INTERIOR.ordinal()] = Gdx.audio.newMusic(Gdx.files.internal("Sound/House.mp3"));

		runningMusic = music[MusicType.MENU.ordinal()];
	}

	@Override
	public void playSound(SFX type, float volume) {
		sfx[type.ordinal()].play(volume*masterVolume);
	}

	public void playMusic(MusicType type){
		if(runningMusic.equals(music[type.ordinal()]) && runningMusic.isPlaying()){
			return;
		}
		runningMusicType = type;
		
		runningMusic.stop();

		runningMusic = music[type.ordinal()];

		runningMusic.setLooping(true);
		runningMusic.play();
		runningMusic.setVolume(type.getVolume()*masterVolume);
	}
	
	

	public void dispose(){
		for(Sound s : sfx){
			s.dispose();
		}
		for(Music m : music){
			m.dispose();
		}
	}

	@Override
	public void setVolume(float volume) {
		this.masterVolume = volume;
		runningMusic.setVolume(runningMusicType.getVolume()*masterVolume);
	}

	@Override
	public float getVolume() {
		return masterVolume;
	}
}
