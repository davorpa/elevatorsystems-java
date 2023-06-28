package elevatorsystems.v2;

import java.util.Objects;

import elevatorsystems.v1_5.Building;
import elevatorsystems.v1_5.BuildingElevator;

/**
 * Building elevators gamma are elevators installed in a building.
 * <p>
 * This kind of elevators have the ability of move itself freely in a range of floors
 * but needs close its doors before each movement (go up/down).
 * <p>
 * The valid floors are provided and validated by the aggregated building.
 *
 * @author davorpa
 */
public class BuildingElevatorWithDoors extends BuildingElevator
		implements 	IsElevatorWithDoors<BuildingElevatorWithDoors>
{
	public final ElevatorDoors<BuildingElevatorWithDoors> doors;


	/**
	 * Bean default constructor to be used with setBuilding setter lazily
	 */
	public BuildingElevatorWithDoors() {
		super();
		this.doors = Objects.requireNonNull(initDoors(),
				"'elevator.doors' initialized by initDoors() must not be null");
	}

	public BuildingElevatorWithDoors(Building building) {
		super(building);
		this.doors = Objects.requireNonNull(initDoors(),
				"'elevator.doors' initialized by initDoors() must not be null");
	}

	public BuildingElevatorWithDoors(Building building, int currentFloor) {
		super(building, currentFloor);
		this.doors = Objects.requireNonNull(initDoors(),
				"'elevator.doors' initialized by initDoors() must not be null");
	}


	protected ElevatorDoors<BuildingElevatorWithDoors> initDoors() {
		return new ElevatorDoors<>(this);
	}


	@Override
	public ElevatorDoors<BuildingElevatorWithDoors> getDoors() {
		return this.doors;
	}
}
