package managers;

import java.io.File;
import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;
import org.newdawn.slick.openal.Audio;

public class FileManager {
	final public static String Res_Folder = "res/";
	
	final public static HashMap<String, Sound> Sounds = new HashMap<String, Sound>();
	final public static HashMap<String, Image> Images = new HashMap<String, Image>();
	
	public static void CountResFiles() {
		
	}
	public static void LoadFiles() {
		LoadResFiles();
	}
	
	// Load All PNG and OGG Files in the Res folder
	private static void LoadResFiles() {
		System.out.println(" --- Loading Images and Sounds --- ");

		LoadDirectory(new File(Res_Folder), Images, Sounds);
		
		System.out.println(" --- " + Sounds.size() + " Sound Files Successfully Loaded ---");
		System.out.println(" --- " + Images.size() + " Images Successfully Loaded ---");
	}
	private static void LoadDirectory(File dir, HashMap<String, Image> images, HashMap<String, Sound> sounds) {
		for (final File f : dir.listFiles()) {
			if(f.isDirectory()) {
				System.out.println("New Directory Found: " + f.getName());
				LoadDirectory(f, images, sounds);
			} else {
				try {
					System.out.println("Attempting to Load File: " + f.getName());
					String[] split = f.getName().split("\\.");
					
					if(split[1].toLowerCase().equals("png")) {
						images.put(split[0], new Image(f.getPath()));
						System.out.println("Loaded File as Image");
					} else if(split[1].toLowerCase().equals("ogg")) {
						sounds.put(split[0], new Sound(f.getPath()));
						System.out.println("Loaded File as Sound");
					} else {
						System.out.println("File Loading Failed");
					}
				} catch(Exception e) {
					System.out.println(e.getMessage());
					System.out.println("Failed to Load File");
				}
			}
		}
	}
	
}