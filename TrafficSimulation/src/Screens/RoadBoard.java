package Screens;


import java.io.Serializable;
import java.util.HashMap;

public class RoadBoard implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8579051714176099269L;
	public int roadSize;
	public int[][] road;
	public RoadBoard(int size){
		roadSize = size;
		road = new int[roadSize][roadSize];
	}
	public boolean newStateAvailable;
}
