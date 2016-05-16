package Screens;

import java.awt.Color;
import java.util.HashMap;
import java.util.Random;

import javax.swing.text.Position;

public class InstructionApplier {

	public Object ApplyInstruction(int[] inst) {

		// HERE GOES THE LOGIC TO UPDATE THE BOARD WHICH IS THEN RENDERED
		int initial = 12;
		Random generator = new Random();

		for (int i = 0; i < inst.length; i += 2) {
			if (inst[i + 1] == 1) {
				continue;
			}
			if (!Road.cars.containsKey(inst[i])) {
				System.out.println("nao contem");
				 System.out.println(i);	
				switch (i/2) {
					
					case 0:
						initial = 12;
						Road.cars.put(
								inst[i],
								new Car(new Color(generator.nextInt(255), generator
										.nextInt(255), generator.nextInt(255)), initial,
										i / 2));
						
						break;
					case 1:
						initial = 13;
						Road.cars.put(
								inst[i],
								new Car(new Color(generator.nextInt(255), generator
										.nextInt(255), generator.nextInt(255)), initial,
										i / 2));
						break;
					case 2:
						initial = 14;
						Road.cars.put(
								inst[i],
								new Car(new Color(generator.nextInt(255), generator
										.nextInt(255), generator.nextInt(255)), initial,
										i / 2));
						break;
					case 3:
						initial = 15;
						Road.cars.put(
								inst[i],
								new Car(new Color(generator.nextInt(255), generator
										.nextInt(255), generator.nextInt(255)), initial,
										i / 2));
						break;
					case 6:
						initial = 16;
						System.out.println("erro aq");
						Road.cars.put(
								inst[i],
								new Car(new Color(generator.nextInt(255), generator
										.nextInt(255), generator.nextInt(255)), initial,
										i / 2));
						break;
					case 7:
						initial = 17;
						Road.cars.put(
								inst[i],
								new Car(new Color(generator.nextInt(255), generator
										.nextInt(255), generator.nextInt(255)), initial,
										i / 2));
						break;
					case 8:
						initial = 18;
						Road.cars.put(
								inst[i],
								new Car(new Color(generator.nextInt(255), generator
										.nextInt(255), generator.nextInt(255)), initial,
										i / 2));
						break;
					case 9:
						initial = 19;
						Road.cars.put(
								inst[i],
								new Car(new Color(generator.nextInt(255), generator
										.nextInt(255), generator.nextInt(255)), initial,
										i / 2));
						break;
					default:
						break;
					}
					
	
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

		return null;
	}
}
