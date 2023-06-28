package elevatorsystems.v3;

import elevatorsystems.v1.HasFloorIndicator;
import elevatorsystems.v2.IsElevatorWithDoors;

/**
 * Floored elevators are elevators providing doors and target floor access.
 * <p>
 * This kind of elevators can go to any floor directly.
 * <p>
 * Due to have doors, the elevator system's engine forces to this kind of elevators to close its doors before any movement
 * (go to a desired floor).
 *
 * @author davorpa
 */
public interface FlooredElevator extends HasFloorIndicator, IsElevatorWithDoors<FlooredElevator>, WithTargetFloorAccess
{

}
