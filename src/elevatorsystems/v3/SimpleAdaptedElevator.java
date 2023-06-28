package elevatorsystems.v3;

import java.util.Objects;

import elevatorsystems.v1.SimpleElevator;
import elevatorsystems.v2.ElevatorDoors;

/**
 * Let's continue to evolve simple elevators gamma.
 * <p>
 * This kind of elevators have the ability of move itself freely
 * but needs close its doors before each movement (go up/down/desired floor).
 *
 * @author davorpa
 */
public class SimpleAdaptedElevator extends SimpleElevator
		implements 	FlooredElevator
{
	public final ElevatorDoors<FlooredElevator> doors;


	public SimpleAdaptedElevator() {
		super();
		this.doors = Objects.requireNonNull(initDoors(),
				"'elevator.doors' initialized by initDoors() must not be null");
	}

	public SimpleAdaptedElevator(int currentFloor) {
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
			((ElevatorHelper) this.helper).goToFloor(targetFloor);
		}
	}
}
