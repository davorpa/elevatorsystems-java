package elevatorsystems.v1;

import elevatorsystems.v1.ElevatorHelper.Movement;

/**
 * An elevator gamma with the ability of move itself freely.
 *
 * @author davorpa
 */
public class SimpleElevator extends FloorIndicatorSupport implements Elevator
{
	public SimpleElevator() {
		super();
	}

	public SimpleElevator(int currentFloor) {
		super(currentFloor);
	}


	@Override
	public void up() {
		synchronized (this) {
			int floor = getCurrentFloor() + 1;
			if (canMove(floor)) {
				System.out.format("\uf05a El ascensor está %s de piso...%n", Movement.UP.abverbLabel);
				setCurrentFloor(floor);
			}
		}
	}


	@Override
	public void down() {
		synchronized (this) {
			int floor = getCurrentFloor() - 1;
			if (canMove(floor)) {
				System.out.format("\uf05a El ascensor está %s de piso...%n", Movement.DOWN.abverbLabel);
				setCurrentFloor(floor);
			}
		}
	}
}
