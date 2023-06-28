package elevatorsystems.v2;

import java.util.Objects;

/**
 * Doors are an important component part of the elevators.
 *
 * @author davorpa
 *
 * @param <E> The class type of the elevator that the doors is composite part of
 */
public class ElevatorDoors<E extends IsElevatorWithDoors<E>>
{
	protected final E elevator;

	private volatile boolean closed;


	public ElevatorDoors(E elevator) {
		this(elevator, true);
	}

	public ElevatorDoors(E elevator, boolean closed) {
		super();
		this.elevator = Objects.requireNonNull(elevator, "'elevator' must not be null");
		this.closed = closed;
	}


	public E getElevator() {
		return this.elevator;
	}


	public boolean isClosed() {
		return this.closed;
	}


	public boolean isOpen() {
		return !this.closed;
	}


	public void open() {
		synchronized (this) {
			boolean status = isClosed();
			if (status) {
				System.out.println("\udb86\udc1e El ascensor está abriendo sus puertas...");
			}
			this.closed = false;
			if (status) {
				System.out.println("\udb80\udc9e Puertas del ascensor abiertas.");
			}
		}
	}


	public void close() {
		synchronized (this) {
			boolean status = isOpen();
			if (status) {
				System.out.println("\udb86\udc1e El ascensor está cerrando sus puertas...");
			}
			this.closed = true;
			if (status) {
				System.out.println("\udb80\udc9e Puertas del ascensor cerradas.");
			}
		}
	}
}
