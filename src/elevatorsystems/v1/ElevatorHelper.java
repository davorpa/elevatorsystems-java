package elevatorsystems.v1;

import java.util.Objects;

import elevatorsystems.Wrappeer;
import elevatorsystems.v1_5.HasBuilding;
import elevatorsystems.v2.ElevatorDoors;
import elevatorsystems.v2.HasDoors;

/**
 * Helper to board with multiple inheritance across Elevator versions tree but
 * without each pollute classes or repeat code.
 *
 * @author davorpa
 */
public class ElevatorHelper
{
	public enum Movement {
		UP("\udb83\udda3 subir", "\udb83\udda3 subiendo", "\udb83\udda3"),
		DOWN("\udb83\udda1 bajar", "\udb83\udda1 bajando", "\udb83\udda1"),
		STOP("mantener piso", "parado", "\uf28d");

		public final String verbLabel;
		public final String abverbLabel;
		public final String icon;

		Movement(String verbLabel, String adverbLabel, String icon) {
			this.verbLabel = verbLabel;
			this.abverbLabel = adverbLabel;
			this.icon = icon;
		}
	}

	protected final HasFloorIndicator elevator;


	protected ElevatorHelper(HasFloorIndicator elevator) {
		this.elevator = Objects.requireNonNull(
				elevator, "'elevator' to help must not be null");
	}


	protected HasFloorIndicator unwrapElevatorIfNeeded() {
		HasFloorIndicator instance = this.elevator;
		while (instance instanceof Wrappeer<?>) { // proxy walk
			instance = (HasFloorIndicator) ((Wrappeer<?>) instance).unwrap();
		}
		return instance;
	}


	protected ElevatorDoors<?> unwrapElevatorDoors() {
		HasFloorIndicator instance = this.elevator;
		if (instance instanceof HasDoors<?>) { // doors found at proxy
			ElevatorDoors<?> doors = ((HasDoors<?>) instance).getDoors();
			if (doors != null) { // Adapters lazy support
				return doors;
			}
		}
		while (instance instanceof Wrappeer<?>) { // proxy walk
			instance = (HasFloorIndicator) ((Wrappeer<?>) instance).unwrap();
			if (instance instanceof HasDoors<?>) {
				return ((HasDoors<?>) instance).getDoors(); // doors found
			}
		}
		if (instance instanceof HasDoors) {
			return ((HasDoors<?>) instance).getDoors(); // doors found at root
		}
		return null; // no doors found
	}


	protected boolean canMove(int targetFloor) {
		// Unwrap proxies (decorators & adapters) until find root Elevator (maybe a SimpleElevator),
		// before instanceof detections
		HasFloorIndicator elevator = unwrapElevatorIfNeeded();

		if (elevator instanceof HasBuilding &&
				!((HasBuilding) elevator).getBuilding().checkFloorRange(targetFloor)) {
			final int delta = resolveMovementDelta(targetFloor);
			System.out.format("\udb80\udc29 ¡El ascensor no puede %s %d %s más!%n",
					resolveMovement(targetFloor).verbLabel,
					delta, delta == 1 ? "piso" : "pisos"
				);
			return false;
		}

		ElevatorDoors<?> doors = unwrapElevatorDoors();
		if (doors != null && doors.isOpen()) {
			System.out.format(
					"\udb80\udc29 Para %s cierre primero las puertas del ascensor...%n",
					resolveMovement(targetFloor).verbLabel
				);
			return false;
		}

		// Is a simple elevator (v1 / v3)
		return true;
	}


	public Movement resolveMovement(int targetFloor) {
		int floor = this.elevator.getCurrentFloor();
		int deltha = targetFloor - floor;
		if (deltha > 0) {
			return Movement.UP;
		}
		if (deltha < 0) {
			return Movement.DOWN;
		}
		return Movement.STOP;
	}


	public int resolveMovementDelta(int targetFloor) {
		int floor = this.elevator.getCurrentFloor();
		return Math.abs(targetFloor - floor);
	}
}
