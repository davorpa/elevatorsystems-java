package elevatorsystems.v3;

import elevatorsystems.v2.WithAutoDoorsSupport;

/**
 * A decorator to auto open/close doors of an elevator with doors on each elevator action (goToFloor).
 *
 * @author davorpa
 *
 * @param <E> The elevator type wrapped by this decorator
 */
public class WithAutoDoorsDecorator<E extends FlooredElevator>
		extends WithAutoDoorsSupport<FlooredElevator>
		implements 	FlooredElevator
{
	public WithAutoDoorsDecorator(E target) {
		super(target);
	}

	@Override
	public void goToFloor(int targetFloor) {
		applyDecoration(() -> this.target.goToFloor(targetFloor));
	}
}
