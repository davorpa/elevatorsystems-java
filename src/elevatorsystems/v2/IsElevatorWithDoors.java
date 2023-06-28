package elevatorsystems.v2;

import elevatorsystems.v1.HasFloorIndicator;

/**
 * Elevators with doors gamma are elevators that has installed doors as component parts.
 * <p>
 * Normally, the elevator system's engine forces to this kind of elevators to close its doors before any movement
 * (go up/down, desired floor...).
 *
 * @author davorpa
 *
 * @param <E> The class type of the elevator that the doors is composite part of
 */
public interface IsElevatorWithDoors<E extends IsElevatorWithDoors<E>>
		extends HasFloorIndicator, HasDoors<E>
{

}
