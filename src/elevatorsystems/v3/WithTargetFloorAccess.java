package elevatorsystems.v3;

/**
 * Interface mark that allows any elevator go to any desired floor directly.
 *
 * @author davorpa
 */
public interface WithTargetFloorAccess
{
	/**
	 * Performs the GOTO desired floor movement
	 *
	 * @param targetFloor - the desired floor which you want move to
	 */
	void goToFloor(int targetFloor);
}
