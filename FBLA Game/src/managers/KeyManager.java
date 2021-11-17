package managers;

import java.util.List;
import java.util.function.Predicate;

import org.newdawn.slick.Input;
import org.newdawn.slick.state.BasicGameState;

import entities.Player;
import gamestates.Game;

public class KeyManager implements Predicate<Integer>{
	final public static List<Integer> Key_Down_List = List.of(Input.KEY_W, Input.KEY_S, Input.KEY_A, Input.KEY_D); 
	
	private Game game;
	private Player player;
	
	private Input input;
	
	public KeyManager(Input input, Game game) {
		this.input = input;
		
		this.game = game;
		this.player = game.getPlayer();
	}
	
	public boolean test(Integer i) {
		return input.isKeyDown(i);
	}
	
	public void keyDown(int key) {
		switch(key) {
			case Input.KEY_W:
				player.accelerateY(2f);
				break;
			case Input.KEY_A:
				player.accelerateX(-2f);
				break;
			case Input.KEY_S:
				player.accelerateY(-2f);
				break;
			case Input.KEY_D:
				player.accelerateX(2f);
				break;
		}
	}
}