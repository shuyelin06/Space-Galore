package managers;

import java.util.HashMap;

import org.newdawn.slick.Sound;

public class SoundManager {
	private Sound backgroundMusic;
	private HashMap<String, Sound> sounds;
	
	public SoundManager() {
		// Load all sound files in the res/Sound folder
		
		// Set other variables
		backgroundMusic = null;
	}
	
	// Play a sound effect
	public void playSoundEffect(String name) {
		sounds.get(name).play();
	}
	// Stop existing background music, and play new background music
	public void playBackgroundMusic(String name) {
		try {
			backgroundMusic.stop();
		} catch(Exception e) {}
		finally {
			backgroundMusic = sounds.get(name);
			backgroundMusic.loop();
		}
		
	}
}