package Screens;

import java.awt.Color;

public class Car {

	int id;
	Color color;
	int currentPos, nextPosition;

	Car(Color color, int currentPos, int nextPositon) {
		this.color = color;
		this.nextPosition = nextPositon;
		this.currentPos = currentPos;

	}
}
