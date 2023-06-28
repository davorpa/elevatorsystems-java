package elevatorsystems.v2;

import elevatorsystems.v1.Elevator;

/**
 * A decorator to auto open/close doors of an elevator with doors on each elevator action (up/down).
 *
 * @author davorpa
 *
 * @param <E> The elevator type wrapped by this decorator
 */
public class WithAutoDoorsDecorator<E extends Elevator & IsElevatorWithDoors<E>>
		extends WithAutoDoorsSupport<E>
		implements 	Elevator
{
	public WithAutoDoorsDecorator(E target) {
		super(target);
	}


	@Override
	public void up() {
		applyDecoration(this.target::up);
	}


	@Override
	public void down() {
		applyDecoration(this.target::down);
	}
}
