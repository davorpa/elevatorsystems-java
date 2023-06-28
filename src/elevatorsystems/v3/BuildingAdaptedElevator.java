package elevatorsystems.v3;

import java.util.Objects;

import elevatorsystems.v1_5.Building;
import elevatorsystems.v1_5.BuildingElevator;
import elevatorsystems.v2.ElevatorDoors;

/**
 * Let's continue to evolve building elevators gamma installed in a building.
 *
 * This kind of elevators have the ability of move itself freely in a range of floors
 * but needs close its doors before each movement (go up/down/desired floor).
 *
 * The valid floors are provided and validated by the aggregated building.
 *
 * @author davorpa
 */
public class BuildingAdaptedElevator extends BuildingElevator
		implements 	FlooredElevator
{
	public final ElevatorDoors<FlooredElevator> doors;


	/**
	 * Bean default constructor to be used with setBuilding setter lazily
	 */
	public BuildingAdaptedElevator() {
		super();
		this.doors = Objects.requireNonNull(initDoors(),
				"'elevator.doors' initialized by initDoors() must not be null");
	}

	public BuildingAdaptedElevator(Building building) {
		super(building);
		this.doors = Objects.requireNonNull(initDoors(),
				"'elevator.doors' initialized by initDoors() must not be null");
	}

	public BuildingAdaptedElevator(Building building, int currentFloor) {
		super(building, currentFloor);
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
