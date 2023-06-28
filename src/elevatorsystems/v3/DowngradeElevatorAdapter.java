package elevatorsystems.v3;

import java.util.Objects;

import elevatorsystems.Wrappeer;
import elevatorsystems.v1.Elevator;
import elevatorsystems.v2.ElevatorDoors;

/**
 * Adapts v3 elevator instances to also provide behavior of previous version elevators (v1..v2).
 *
 * @author davorpa
 */
public class DowngradeElevatorAdapter
		implements 	Wrappeer<FlooredElevator>,
					FlooredElevator, Elevator
{
	protected final FlooredElevator target;

	protected ElevatorHelper helper;


	public DowngradeElevatorAdapter(FlooredElevator target) {
		super();
		this.target = Objects.requireNonNull(
				target, "'target' elevator instance must not be null");
		this.helper = Objects.requireNonNull(initElevatorHelper(),
				"'elevator.helper' initialized by initElevatorHelper() must not be null");
	}


	protected ElevatorHelper initElevatorHelper() {
		return new ElevatorHelper(this.target);
	}


	@Override
	public FlooredElevator unwrap() {
		return this.target;
	}


	@Override
	public int getCurrentFloor() {
		return this.target.getCurrentFloor();
	}


	@Override
	public ElevatorDoors<FlooredElevator> getDoors() {
		return this.target.getDoors();
	}


	@Override
	public void up() {
		synchronized (this) {
			goToFloor(getCurrentFloor() + 1);
		}
	}


	@Override
	public void down() {
		synchronized (this) {
			goToFloor(getCurrentFloor() - 1);
		}
	}


	@Override
	public void goToFloor(int targetFloor) {
		synchronized (this) {
			this.target.goToFloor(targetFloor);
		}
	}
}
