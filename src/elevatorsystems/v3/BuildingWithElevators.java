package elevatorsystems.v3;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.Objects;

import elevatorsystems.v1_5.Building;
import elevatorsystems.v1_5.BuildingElevator;
import elevatorsystems.v1_5.IsBuildingElevator;
import elevatorsystems.v2.HasDoors;

/**
 * This kind of building have installed a collection of building elevators.
 * <p>
 * Allows to the user know in any moment the state of its installed elevators;
 * also operate with them remotely.
 *
 * @author davorpa
 */
public class BuildingWithElevators<E extends IsBuildingElevator> extends Building
{
	private E[] elevators;


	/**
	 * Bean default constructor to be used with setElevators setter lazily
	 */
	public BuildingWithElevators(
			int minFloor, int maxFloor
		) {
		super(minFloor, maxFloor);
	}

	public BuildingWithElevators(
			int minFloor, int maxFloor,
			E[] elevators
		) {
		super(minFloor, maxFloor);
		setElevators(elevators);
	}

	public BuildingWithElevators(
			int minFloor, int maxFloor,
			int elevatorsSize
		) {
		super(minFloor, maxFloor);
		setElevators(initializeElevators(elevatorsSize));
	}


	@SuppressWarnings("unchecked")
	protected Class<? extends BuildingElevator> resolveElevatorsClazz() {
		try {
			Type type = getClass().getTypeParameters()[0].getBounds()[0];
			return (Class<? extends BuildingElevator>) Class.forName(type.getTypeName());
		} catch (IllegalStateException e) {
			throw e;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	protected E[] initializeElevators(int size) {
		@SuppressWarnings("unchecked")
		E[] array = (E[]) Array.newInstance(resolveElevatorsClazz(), size);
		for (int i = 0; i < size; i++) {
			array[i] = createElevator(i);
		}
		return array;
	}

	@SuppressWarnings("unchecked")
	protected E createElevator(int index) {
		return (E) new BuildingFlooredElevator(this);
	}


	protected final E[] getElevators() {
		// emulate a final field behavior but with lazily support
		synchronized (this) {
			if (this.elevators == null) {
				throw new IllegalStateException("'building.elevators' need to be initialized");
			}
			return this.elevators;
		}
	}


	public void setElevators(E[] elevators) {
		// emulate a final field behavior but with lazily support
		Objects.requireNonNull(elevators, "'building.elevators' must not be null");
		synchronized (this) {
			if (this.elevators != null && this.elevators != elevators) {
				throw new IllegalStateException("'building.elevators' can not be reasigned since it is a final field");
			}
			// business restriction: at least one elevator
			if (elevators.length < 1) {
				throw new IllegalArgumentException("'building.elevators' must have elements");
			}
			for (E elevator : elevators) { //for-each
				Objects
					// ensure non-null elements
					.requireNonNull(elevator, "'building.elevators' must contain non-null elements")
					// validate and ensure 1:N bidirectional entity navigation
					.setBuilding(this);
			}
			// set bean attribute
			this.elevators = elevators;
		}
	}


	public E elevator(int index) {
		E[] array = getElevators();
		return array[Objects.checkIndex(index, array.length)];
	}


	public int sizeOfElevators() {
		E[] array = getElevators();
		return array.length;
	}


	public void reportElevatorsState(PrintWriter out) {
		for (int i = 0; i < sizeOfElevators(); i++) {
			out.println(composeElevatorState(i, elevator(i)));
		}
	}

	public void reportElevatorsState(PrintStream out) {
		for (int i = 0; i < sizeOfElevators(); i++) {
			out.println(composeElevatorState(i, elevator(i)));
		}
	}

	protected Appendable composeElevatorState(int index, E reportable) {
		StringBuilder line = new StringBuilder();
		line.append("\uf0a9 El ascensor ").append(String.format("%03d", index))
			.append(" está en el piso ").append(String.format("%3d", reportable.getCurrentFloor()))
			.append(" del edificio");
		if (reportable instanceof HasDoors<?>) {
			line.append(" con las puertas ")
				.append(((HasDoors<?>) reportable).getDoors().isClosed() ? "cerradas" : "abiertas");
		}
		line.append(".");
		return line;
	}
}
