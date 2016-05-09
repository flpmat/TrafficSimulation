package Screens;

import java.awt.Color;

public class Car {

	int id;
	Color color;
	// int oldPosX, oldPosY, currentPosX, currentPosY;
	int oldPos, currentPos;

	Car(Color color, int oldPos, int currentPos) {
		this.color = color;
		this.oldPos = oldPos;
		// this.oldPosY = oldPosY;
		this.currentPos = currentPos;
		// this.currentPosY = currentPosY;

	}
}
