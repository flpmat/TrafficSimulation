package Screens;

import java.awt.Color;
import java.util.HashMap;
import java.util.Random;
import java.util.Map.Entry;

import javax.swing.text.Position;

import org.omg.CORBA.Current;

public class InstructionApplier {

	public Object ApplyInstruction(int[] inst) {

		int initial = 12;
		boolean skip = false;
		Random generator = new Random();
		for (int i = 0; i < inst.length; i += 2) {
			if (inst[i + 1] == 1) {
				continue;
			}
			int pos = i;
			for (Entry<Integer, Car> entry : Road.cars.entrySet()) {
				if (entry.getValue().currentPos == 0 && i == 0) {
					if(Road.bottleneckP0.size() == 0){
						pos = 40;
					}else if (Road.bottleneckP0.size() == 1){
						pos = 42;
					}
					
					
					System.out.println("ocupado");

					Road.bottleneckP0.add(inst[i]);
					
					
				}				
			}
			
			if (!Road.cars.containsKey(inst[i]) ) {
				System.out.println("nao contem");
				System.out.println(i);
				switch (i / 2) {

				case 0:
					initial = 12;
					Road.cars.put(
							inst[i],
							new Car(new Color(generator.nextInt(255), generator
									.nextInt(255), generator.nextInt(255)),
									initial, pos / 2));

					break;
				case 1:
					initial = 13;
					Road.cars.put(
							inst[i],
							new Car(new Color(generator.nextInt(255), generator
									.nextInt(255), generator.nextInt(255)),
									initial, pos / 2));
					break;
				case 2:
					initial = 14;
					Road.cars.put(
							inst[i],
							new Car(new Color(generator.nextInt(255), generator
									.nextInt(255), generator.nextInt(255)),
									initial, pos / 2));
					break;
				case 3:
					initial = 15;
					Road.cars.put(
							inst[i],
							new Car(new Color(generator.nextInt(255), generator
									.nextInt(255), generator.nextInt(255)),
									initial, pos / 2));
					break;
				case 6:
					initial = 16;
					System.out.println("erro aq");
					Road.cars.put(
							inst[i],
							new Car(new Color(generator.nextInt(255), generator
									.nextInt(255), generator.nextInt(255)),
									initial, pos / 2));
					break;
				case 7:
					initial = 17;
					Road.cars.put(
							inst[i],
							new Car(new Color(generator.nextInt(255), generator
									.nextInt(255), generator.nextInt(255)),
									initial, pos / 2));
					break;
				case 8:
					initial = 18;
					Road.cars.put(
							inst[i],
							new Car(new Color(generator.nextInt(255), generator
									.nextInt(255), generator.nextInt(255)),
									initial, pos / 2));
					break;
				case 9:
					initial = 19;
					Road.cars.put(
							inst[i],
							new Car(new Color(generator.nextInt(255), generator
									.nextInt(255), generator.nextInt(255)),
									initial, pos / 2));
					break;
				default:
					break;
				}

			} else {
				System.out.println("pos " + pos);
				Road.cars.get(inst[i]).currentPos = Road.cars.get(inst[i]).nextPosition;
				System.out
						.println("CURRENT:"
								+ Road.position[Road.cars.get(inst[i]).currentPos * 2 + 1]);
				Road.cars.get(inst[i]).nextPosition = pos / 2;
				System.out
						.println("NEXT:"
								+ Road.position[Road.cars.get(inst[i]).currentPos * 2 + 1]);
			}
			
			/*
			 * EVERYTIME A CAR IS MOVED FROM 0 TO 4, THE CARS INCLUDED IN THE QUEUE WILL
			 * MOVE FORWARD (THEIR POSITIONS IS UPDATED SO WHEN THE NEXT RENDERING TAKES
			 * PLACE THEY'RE MOVED
			 * 
			 */
			
			if(Road.cars.get(inst[i]).currentPos == 0 && Road.cars.get(inst[i]).nextPosition == 8){
				Road.cars.get(Road.bottleneckP0.element()).currentPos = 
			}

			
			System.out.println(Road.cars.get(inst[i]).nextPosition);
		}
		Road.road.newStateAvailable = true;

		return null;
	}
}
