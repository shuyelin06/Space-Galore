package main;

import java.util.List;

import org.newdawn.slick.Input;

public class Values{
	// Will contain all of our major game variables
	final public static int Pixels_Per_Unit = 20; // Defines the number of pixels per unit in our game
	
	// Physics Coefficients
	final public static float Drag_Coefficient = 0.4f;
	final public static float Restitution_Coefficient = 0.5f;
	
	// Where the display manager's center is located on screen 
	final public static float Center_X = Engine.RESOLUTION_X / 2f;
	final public static float Center_Y = Engine.RESOLUTION_Y / 2f;
}