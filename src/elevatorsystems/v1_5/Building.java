package elevatorsystems.v1_5;

/**
 * Buildings are in charge of provide a range of valid floors where an elevator can move itself in.
 *
 * @author davorpa
 */
public class Building
{
	private final int minFloor;

	private final int maxFloor;


	public Building(int minFloor, int maxFloor) {
		super();
		// swap values if needed
		this.minFloor = Math.min(minFloor, maxFloor);
		this.maxFloor = Math.max(minFloor, maxFloor);
	}


	public int getMinFloor() {
		return this.minFloor;
	}


	public int getMaxFloor() {
		return this.maxFloor;
	}


	// XXX: Should be protected / package access
	public boolean checkFloorRange(int floor) {
		return floor >= this.minFloor && floor <= this.maxFloor;
	}
}
