package elevatorsystems.v2;

import java.util.Objects;

import elevatorsystems.Wrappeer;

/**
 * WithAutoDoors decorators allows you to auto open/close doors of an elevator with doors on each elevator action.
 * <p>
 * This support class is should be the base of all of them.
 *
 * @author davorpa
 *
 * @param <E> The elevator type wrapped by this decorator
 */
public abstract class WithAutoDoorsSupport<E extends IsElevatorWithDoors<E>>
		implements 	Wrappeer<E>,
					IsElevatorWithDoors<E>
{
	protected final E target;


	protected WithAutoDoorsSupport(E target) {
		super();
		this.target = Objects.requireNonNull(
				target, "'target' elevator instance must not be null");
	}


	@Override
	public E unwrap() {
		return this.target;
	}


	@Override
	public ElevatorDoors<E> getDoors() {
		return this.target.getDoors();
	}


	@Override
	public int getCurrentFloor() {
		return this.target.getCurrentFloor();
	}


	protected void applyDecoration(Runnable task) {
		Objects.requireNonNull(task, "'task' to decorate must not be null");
		ElevatorDoors<?> doors = getDoors();
		// Around decoration
		synchronized (doors) {
			if (doors.isOpen()) {
				doors.close();
			}
			task.run();
			if (doors.isClosed()) {
				doors.open();
			}
		}
	}
}
