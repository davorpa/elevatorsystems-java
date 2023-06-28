package elevatorsystems.v3;

import elevatorsystems.v1.Elevator;
import elevatorsystems.v1.HasFloorIndicator;
import elevatorsystems.v2.ElevatorDoors;

/**
 * Helper to gain access to common Elevator functionality in this v3 package.
 */
public class ElevatorHelper extends elevatorsystems.v1.ElevatorHelper
{
	protected ElevatorHelper(HasFloorIndicator elevator) {
		super(elevator);
	}


	@Override
	protected HasFloorIndicator unwrapElevatorIfNeeded() {
		// Overridden to provide package classes access
		return super.unwrapElevatorIfNeeded();
	}


	@Override
	protected ElevatorDoors<?> unwrapElevatorDoors() {
		// Overridden to provide package classes access
		return super.unwrapElevatorDoors();
	}


	@Override
	protected boolean canMove(int targetFloor) {
		// Overridden to provide package classes access
		return super.canMove(targetFloor);
	}


	protected void goToFloor(int targetFloor) {
		// XXX: Unwrap proxies (decorators & adapters) until find root Elevator (maybe a SimpleElevator), before instanceof detections
//		HasFloorIndicator elevator = unwrapElevatorIfNeeded();

		if (elevator instanceof Elevator) { // v1-v2
			Movement movement;
			while (	// at targetFloor... so stop check loop
					(movement = resolveMovement(targetFloor)) != Movement.STOP
					&&
					canMove(targetFloor))
			{
				switch (movement) {
					// continue checking/moving...
					case UP:
						((Elevator) elevator).up();
						break;
					case DOWN:
						((Elevator) elevator).down();
						break;
					// it should not happen
					default:
						throw new UnsupportedOperationException("unrecognized elevator's movement command: " + movement);
				}
			}
			return;
		}

		throw new UnsupportedOperationException("non-implemented goToFloor elevator behavior");
	}
}
