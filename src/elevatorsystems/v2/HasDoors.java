package elevatorsystems.v2;

/**
 * Interface mark indicating that an elevator has doors.
 *
 * @author davorpa
 *
 * @param <E> The class type of the elevator that the doors is composite part of
 */
public interface HasDoors<E extends IsElevatorWithDoors<E>>
{
	ElevatorDoors<E> getDoors();
}
