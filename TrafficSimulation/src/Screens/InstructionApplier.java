package Screens;

import java.awt.Color;
import java.util.HashMap;
import java.util.Random;

import javax.swing.text.Position;

public class InstructionApplier {
	// road positions go here
	static final int EMPTY = 0;
	static final int PLAYER = 1;
	static final int COIN = 2;
	static final int NPLAYERS = 3;

	public Object ApplyInstruction(int[] inst) {
		// Instruction Inst = (Instruction)inst;

		// HERE GOES THE LOGIC TO UPDATE THE BOARD WHICH IS THEN RENDERED

		Random generator = new Random();

		for (int i = 0; i < inst.length; i += 2) {
			if (inst[i + 1] == 1) {
				continue;
			}
			if (!Road.cars.containsKey(inst[i])) {
				System.out.println("nao contem");
				System.out.println(inst[i]);
					Road.cars.put(
							inst[i],
							new Car(new Color(generator.nextInt(255), generator
									.nextInt(255), generator.nextInt(255)), 12,
									i / 2));
	
			} else {
				System.out.println("pos " + i);
				Road.cars.get(inst[i]).currentPos = Road.cars.get(inst[i]).nextPosition;
				System.out.println("CURRENT:"
						+ Road.position[Road.cars.get(inst[i]).currentPos*2 + 1]);
				Road.cars.get(inst[i]).nextPosition = i/2;
				System.out.println("NEXT:"
						+ Road.position[Road.cars.get(inst[i]).currentPos*2 + 1]);
			}
			System.out.println(Road.cars.get(inst[i]).nextPosition);
		}
		Road.road.newStateAvailable = true;

		/*
		 * //if spot is avail, place instructed item in spot
		 * if(Game.map.board[Inst.x][Inst.y] == EMPTY){
		 * Game.map.board[Inst.x][Inst.y] = Inst.newState; //if spot is filled
		 * with a coin, move player on to it }else
		 * if(Game.map.board[Inst.x][Inst.y] == COIN){
		 * Game.map.board[Inst.x][Inst.y] = Inst.newState; //add player to score
		 * db if non existant, or increment if(Game.scores.get(Inst.user) !=
		 * null){ if(Game.scores.get(Inst.user) >= 20){ Game.winner = (Inst.user
		 * + " is the winner!"); }else{
		 * Game.scores.put(Inst.user,Game.scores.get(Inst.user)+1); } }else{
		 * Game.scores.put(Inst.user,1); } Game.map.setScore(Game.scores);
		 * System.out.println(Game.scores.get(Inst.user)); }else if
		 * ((Game.map.board[Inst.x][Inst.y] == PLAYER) && (Inst.newState ==
		 * PLAYER) ){ Game.map.board[Inst.x][Inst.y] = NPLAYERS; }
		 * 
		 * // if the moving player has an old position, set to empty
		 * if(!(Inst.oldx == -1)){ if(Game.map.board[Inst.oldx][Inst.oldy] ==
		 * NPLAYERS){ Game.map.board[Inst.oldx][Inst.oldy] = PLAYER; }else {
		 * Game.map.board[Inst.oldx][Inst.oldy] = EMPTY; } }
		 */

		// THIS IS IMPORTANT TO REFRESH THE BOARD
		// Board.newStateAvailable = true
		return null;
	}
}
