package managers;

import java.util.HashMap;

import org.newdawn.slick.Sound;

public class SoundManager {
	final public static HashMap<String, Sound> Sounds = new HashMap<>();
	private static Sound backgroundMusic;
	
	// Play a sound effect
	public static void playSoundEffect(String name) {
		Sounds.get(name).play();
	}

	// Stop any existing background music, and play new background music
	public static void playBackgroundMusic(String name) {
		try {
			// Later, have it so the existing background music fades out
			backgroundMusic.stop();
		} catch(Exception e) {}
		finally {
			backgroundMusic = Sounds.get(name);
			backgroundMusic.loop();
		}
		
	}
}