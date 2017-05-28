package ch.magejo.randomgame.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import ch.magejo.randomgame.mecanics.sound.SFX;
import ch.magejo.randomgame.mecanics.sound.SoundPlayer;

public class SoundManager implements SoundPlayer{

	private Sound[] sfx;
	private Music[] music;
	private Music runningMusic;

	public SoundManager() {
		sfx = new Sound[SFX.values().length];
		sfx[SFX.ATTACK_1.ordinal()] = Gdx.audio.newSound(Gdx.files.internal("Sound/Pickup.wav"));
		sfx[SFX.DAMAGE_1.ordinal()] = Gdx.audio.newSound(Gdx.files.internal("Sound/Pickup.wav"));

		music = new Music[MusicType.values().length];
		music[MusicType.MENU.ordinal()] = Gdx.audio.newMusic(Gdx.files.internal("Sound/Menu.mp3"));
		music[MusicType.OVERWORLD.ordinal()] = Gdx.audio.newMusic(Gdx.files.internal("Sound/Overworld.mp3"));
		music[MusicType.INTERIOR.ordinal()] = Gdx.audio.newMusic(Gdx.files.internal("Sound/House.mp3"));

		runningMusic = music[MusicType.MENU.ordinal()];
	}

	@Override
	public void playSound(SFX type) {
		sfx[type.ordinal()].play();
	}

	public void playMusic(MusicType type){
		if(runningMusic.equals(music[type.ordinal()]) && runningMusic.isPlaying()){
			return;
		}
		runningMusic.stop();

		runningMusic = music[type.ordinal()];

		runningMusic.setLooping(true);
		runningMusic.play();
	}

	public void dispose(){
		for(Sound s : sfx){
			s.dispose();
		}
		for(Music m : music){
			m.dispose();
		}
	}
}
