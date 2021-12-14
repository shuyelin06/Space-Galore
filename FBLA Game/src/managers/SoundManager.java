package managers;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Sound;
import org.newdawn.slick.openal.DeferredSound;

public class SoundManager {

	public static final Map<String, Sound> Sounds = new HashMap<>();
	private static Sound backgroundMusic;

	private SoundManager() { throw new IllegalStateException("Utility class"); }
	
	// Play a sound effect
	public static void playSoundEffect(String name) { Sounds.get(name).play(); }

	// Stop any existing background music, and play new background music
	public static void playBackgroundMusic(String name) {
		try {
			// Later, have it so the existing background music fades out
			backgroundMusic.stop();
		} catch (Exception ignored) {}
		finally {
			backgroundMusic = Sounds.get(name);

			if(backgroundMusic != null) {
				backgroundMusic.loop();
			}
		}
		
	}
	public static void stopBackgroundMusic() {
		try{
			backgroundMusic.stop();
		} catch(Exception ignored) {}
	}
}