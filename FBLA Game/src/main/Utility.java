package main;

public class Utility{
  public static boolean rectangleOverlap(double[] rec1, double[] rec2) {
		double x1 = rec1[0], y1 = rec1[1], x2 = rec1[2], y2 = rec1[3];
		double x3 = rec2[0], y3 = rec2[1], x4 = rec2[2], y4 = rec2[3];
		
		return (x1 < x4) && (x3 < x2) && (y1 < y4) && (y3 < y2);
	}
}
