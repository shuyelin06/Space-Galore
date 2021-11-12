package entities;

public class Enemy extends Entity{
	public Enemy() {
		super(Player.Player_X_Spawn, Player.Player_Y_Spawn + 10f, 20f);
		
		this.sizeX = 2f;
		this.sizeY = 2f;
	}
}