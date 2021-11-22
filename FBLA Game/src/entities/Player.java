package entities;

import entities.core.Entity;
import main.Engine;
import main.Values;

public class Player extends Entity{
	final public static float Player_X_Spawn = (float) Engine.RESOLUTION_X / 2 / Values.Pixels_Per_Unit;
	final public static float Player_Y_Spawn = (float) Engine.RESOLUTION_Y / 2 / Values.Pixels_Per_Unit;
	
	public Player(){
		super(Player_X_Spawn, Player_Y_Spawn);
		
		this.width = 1f;
		this.height = 1f;
		this.mass = 10f;
		
		this.angle = (float) 0;
	}
}