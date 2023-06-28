package elevatorsystems.v3;

import java.util.Objects;

import elevatorsystems.v1_5.Building;
import elevatorsystems.v1_5.IsBuildingElevator;

/**
 * Let's continue to evolve building elevators gamma installed in a building.
 * <p>
 * This kind of elevators have the ability of move itself freely in a range of floors
 * but needs close its doors before each movement (go to desired floor).
 * <p>
 * The valid floors are provided and validated by the aggregated building.
 *
 * @author davorpa
 */
public class BuildingFlooredElevator extends SimpleFlooredElevator implements IsBuildingElevator
{
	private Building building;


	/**
	 * Bean default constructor to be used with setBuilding setter lazily
	 */
	public BuildingFlooredElevator() {
		super(0);
	}

	public BuildingFlooredElevator(Building building) {
		this(building,
			Objects.requireNonNull(building, "'elevator.building' must not be null")
				.getMinFloor());
	}

	public BuildingFlooredElevator(Building building, int currentFloor) {
		super(currentFloor);
		setBuilding(building);
	}


	@Override
	public Building getBuilding() {
		// emulate a final field behavior but with lazily support
		synchronized (this) {
			if (this.building == null) {
				throw new IllegalStateException("'elevator.building' need to be initialized");
			}
			return this.building;
		}
	}


	@Override
	public void setBuilding(Building building) {
		// emulate a final field behavior but with lazily support
		Objects.requireNonNull(building, "'elevator.building' must not be null");
		synchronized (this) {
			if (this.building != null && this.building != building) {
				throw new IllegalStateException("'elevator.building' can not be reasigned since it is a final field");
			}
			if (!building.checkFloorRange(getCurrentFloor())) {
				throw new IllegalArgumentException(
					"El piso por defecto del ascensor está fuera de rango"
				);
			}
			this.building = building;
		}
	}
}
