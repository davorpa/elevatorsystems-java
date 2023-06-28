package elevatorsystems.v3;

import java.util.Objects;

import elevatorsystems.v1.ElevatorHelper.Movement;
import elevatorsystems.v1.FloorIndicatorSupport;
import elevatorsystems.v2.ElevatorDoors;

/**
 * An elevator gamma with the ability of move itself freely to any floor directly.
 * <p>
 * Due to have doors, the elevator system's engine forces to this kind of elevators
 * to close its doors before any movement (go to a desired floor).
 *
 * @author davorpa
 */
public class SimpleFlooredElevator extends FloorIndicatorSupport implements FlooredElevator
{
	public final ElevatorDoors<FlooredElevator> doors;


	public SimpleFlooredElevator() {
		this(0);
	}

	public SimpleFlooredElevator(int currentFloor) {
		super(currentFloor);
		this.doors = Objects.requireNonNull(initDoors(),
				"'elevator.doors' initialized by initDoors() must not be null");
	}


	@Override
	protected ElevatorHelper initElevatorHelper() {
		return new ElevatorHelper(this);
	}


	protected ElevatorDoors<FlooredElevator> initDoors() {
		return new ElevatorDoors<>(this);
	}


	@Override
	public ElevatorDoors<FlooredElevator> getDoors() {
		return this.doors;
	}


	@Override
	public void goToFloor(int targetFloor) {
		synchronized (this) {
			Movement movement;
			if (	// at targetFloor... so stop check loop
					(movement = this.helper.resolveMovement(targetFloor)) != Movement.STOP
					&&
					canMove(targetFloor))
			{
				int movementDelta = this.helper.resolveMovementDelta(targetFloor);
				System.out.format("\uf05a El ascensor va a %s...%n", movement.verbLabel);
				while (movementDelta != 0) { // while not at desired floor
					movementDelta--;
					int floor = incrementAndGetCurrentFloor(
							currentFloor -> targetFloor > currentFloor ? 1 : -1);
					System.out.format("\uf05a Ascensor: %s %d%n", movement.icon, floor);
				}
			}
		}
	}
}
