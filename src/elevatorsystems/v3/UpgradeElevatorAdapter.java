package elevatorsystems.v3;

import java.util.Objects;

import elevatorsystems.Wrappeer;
import elevatorsystems.v1.Elevator;
import elevatorsystems.v2.ElevatorDoors;

/**
 * Adapts v1 elevator instances to provide behavior of further version elevators (v3).
 *
 * @author davorpa
 */
public class UpgradeElevatorAdapter
		implements 	Wrappeer<Elevator>,
					Elevator, FlooredElevator
{
	protected final Elevator target;

	// TODO Infer right ElevatorDoors generic type ???
	public final ElevatorDoors<?> doors;

	protected ElevatorHelper helper;


	public UpgradeElevatorAdapter(Elevator target) {
		super();
		this.target = Objects.requireNonNull(
				target, "'target' elevator instance must not be null");
		this.helper = Objects.requireNonNull(initElevatorHelper(),
				"'elevator.helper' initialized by initElevatorHelper() must not be null");
		this.doors = Objects.requireNonNull(initDoors(),
				"'elevator.doors' initialized by initDoors() must not be null");
	}


	protected ElevatorHelper initElevatorHelper() {
		return new ElevatorHelper(this);
	}


	protected ElevatorDoors initDoors() {
		// inspect for a elevator with doors to reuse doors instances
		ElevatorDoors<?> instance = this.helper.unwrapElevatorDoors();
		if (instance == null) {
			// no elevator with doors, so create ones
			instance = new ElevatorDoors<>(this);
		}
		return instance;
	}


	@Override
	public Elevator unwrap() {
		return this.target;
	}


	@Override
	public int getCurrentFloor() {
		return this.target.getCurrentFloor();
	}


	@Override
	public ElevatorDoors getDoors() {
		return this.doors;
	}


	@Override
	public void up() {
		synchronized (this) {
			this.target.up();
		}
	}


	@Override
	public void down() {
		synchronized (this) {
			this.target.down();
		}
	}


	@Override
	public void goToFloor(int targetFloor) {
		synchronized (this) {
			this.helper.goToFloor(targetFloor);
		}
	}
}
