package elevatorsystems.v2;

import java.util.Objects;

import elevatorsystems.v1.SimpleElevator;

/**
 * An elevator gamma with the ability of move itself freely
 * but needs close its doors before each movement (go up/down).
 *
 * @author davorpa
 */
public class SimpleElevatorWithDoors extends SimpleElevator
		implements 	IsElevatorWithDoors<SimpleElevatorWithDoors>
{
	public final ElevatorDoors<SimpleElevatorWithDoors> doors;


	public SimpleElevatorWithDoors() {
		this(0);
	}

	public SimpleElevatorWithDoors(int currentFloor) {
		super(currentFloor);
		this.doors = Objects.requireNonNull(initDoors(),
				"'elevator.doors' initialized by initDoors() must not be null");
	}


	protected ElevatorDoors<SimpleElevatorWithDoors> initDoors() {
		return new ElevatorDoors<>(this);
	}


	@Override
	public ElevatorDoors<SimpleElevatorWithDoors> getDoors() {
		return this.doors;
	}
}
