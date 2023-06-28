package elevatorsystems.v1_5;

import elevatorsystems.v1.HasFloorIndicator;

/**
 * Building elevators gamma are elevators installed in a building.
 * <p>
 * A building is in charge of provide a range of valid floors where the elevator can move itself in.
 *
 * @author davorpa
 */
public interface IsBuildingElevator extends HasFloorIndicator, HasBuilding
{

}
