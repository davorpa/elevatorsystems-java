package elevatorsystems.v1_5;

/**
 * Interface mark to provide composition for a building.
 *
 * @author davorpa
 */
public interface HasBuilding
{
	Building getBuilding();

	// XXX Should have only package access
	void setBuilding(Building building);
}
