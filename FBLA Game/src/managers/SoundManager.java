package managers;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Sound;

public class SoundManager {
	public static final Map<String, Sound> Sounds = new HashMap<>();
	private static Sound backgroundMusic;

	private SoundManager() { throw new IllegalStateException("Utility class"); }
	
	// Play a sound effect
	public static void playSoundEffect(String name) {
		Sounds.get(name).play();
	}

	// Stop any existing background music, and play new background music
	public static void playBackgroundMusic(String name) {
		try {
			// Later, have it so the existing background music fades out
			backgroundMusic.stop();
		} catch (Exception ignored) {}
		finally {
			backgroundMusic = Sounds.get(name);
			backgroundMusic.loop();
		}
		
	}
}