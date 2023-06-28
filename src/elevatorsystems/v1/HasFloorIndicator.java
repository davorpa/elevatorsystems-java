package elevatorsystems.v1;

/**
 * Elevators always have the ability to report it current floor where is moving through.
 * <p>
 * This is a interface mark to provide that report operation.
 *
 * @author davorpa
 */
public interface HasFloorIndicator
{
	/**
	 * @return the current floor where the elevator is moving through or stopped currently.
	 */
	int getCurrentFloor();
}
