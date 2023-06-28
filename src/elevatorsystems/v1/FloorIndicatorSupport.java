package elevatorsystems.v1;

import java.util.Objects;
import java.util.function.IntUnaryOperator;

/**
 * Base support class to provide the elevators internal state.
 *
 * @author davorpa
 */
public abstract class FloorIndicatorSupport implements HasFloorIndicator
{
	protected ElevatorHelper helper;

	private volatile int currentFloor;


	protected FloorIndicatorSupport() {
		this(0);
	}

	protected FloorIndicatorSupport(int currentFloor) {
		super();
		this.helper = Objects.requireNonNull(initElevatorHelper(),
				"'elevator.helper' initialized by initElevatorHelper() must not be null");
		this.currentFloor = currentFloor;
	}


	protected ElevatorHelper initElevatorHelper() {
		return new ElevatorHelper(this);
	}


	@Override
	public int getCurrentFloor() {
		return this.currentFloor;
	}


	protected void setCurrentFloor(int currentFloor) {
		this.currentFloor = currentFloor;
	}


	protected int incrementAndGetCurrentFloor(IntUnaryOperator deltaProvider) {
		synchronized (this) {
			this.currentFloor += deltaProvider.applyAsInt(this.currentFloor);
			return this.currentFloor;
		}
	}


	protected boolean canMove(int targetFloor) {
		return this.helper.canMove(targetFloor);
	}
}
