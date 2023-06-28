package elevatorsystems.v1;

/**
 * Elevators gamma with the only ability of up and down single movements.
 *
 * @author davorpa
 */
public interface Elevator extends HasFloorIndicator
{
	/**
	 * Performs the UP single movement
	 */
	void up();

	/**
	 * Performs the DOWN single movement
	 */
	void down();
}
