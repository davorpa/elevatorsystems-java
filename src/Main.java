import java.util.concurrent.ThreadLocalRandom;

import elevatorsystems.v1.HasFloorIndicator;
import elevatorsystems.v1.SimpleElevator;
import elevatorsystems.v1_5.Building;
import elevatorsystems.v1_5.BuildingElevator;
import elevatorsystems.v1_5.IsBuildingElevator;
import elevatorsystems.v2.BuildingElevatorWithDoors;
import elevatorsystems.v2.IsElevatorWithDoors;
import elevatorsystems.v2.SimpleElevatorWithDoors;
import elevatorsystems.v2.WithAutoDoorsDecorator;
import elevatorsystems.v3.BuildingAdaptedElevator;
import elevatorsystems.v3.BuildingFlooredElevator;
import elevatorsystems.v3.BuildingWithElevators;
import elevatorsystems.v3.DowngradeElevatorAdapter;
import elevatorsystems.v3.SimpleFlooredElevator;
import elevatorsystems.v3.UpgradeElevatorAdapter;

public class Main
{
	public static void main(String[] args) {
		testElevatorV1();
		testElevatorV1_5();

		testElevatorV2();
		testElevatorV2_1();
		testWithAutoDoorsElevatorV2();
		testWithAutoDoorsElevatorV2_1();

		testElevatorV1AdaptedToV3();
		testElevatorV1_5AdaptedToV3();

		testElevatorV2AdaptedToV3();
		testElevatorV2_1AdaptedToV3();

		testElevatorV3();
		testElevatorV3_1();

		testElevatorV3_xAdaptedToV1();

// TODO: Enable when AutoDoors proxy chain will be fixed
//		testWithAutoDoorsElevatorV2AdaptedToV3();
//		testWithAutoDoorsElevatorV2_1AdaptedToV3();
		testWithAutoDoorsElevatorV3();

		testBuildingWithElevatorsReport();
	}


	static void testElevatorV1() {
		System.out.println(" ==== TESTING ELEVATOR V1.0 (SimpleElevator) ==== ");

		SimpleElevator elevator = new SimpleElevator(1);
		assertElevatorFloor(elevator, 1);

		elevator.down();
		assertElevatorFloor(elevator, 0);

		elevator.down();
		assertElevatorFloor(elevator, -1);

		elevator.up();
		assertElevatorFloor(elevator, 0);

		elevator.up();
		assertElevatorFloor(elevator, 1);

		printEnd();
	}


	static void testElevatorV1AdaptedToV3() {
		System.out.println(" ==== TESTING ELEVATOR V1.0->3.0 (SimpleElevator Directly Adapted) ==== ");

		UpgradeElevatorAdapter elevator = new UpgradeElevatorAdapter(
				new SimpleElevator(1));
		assertElevatorFloor(elevator, 1);
		assertClosedElevatorDoors(elevator);

		elevator.goToFloor(5); // 4 movements UP
		assertElevatorFloor(elevator, 5);

		elevator.doors.open();
		assertOpenElevatorDoors(elevator);

		elevator.goToFloor(0); // opened doors => no movement
		assertElevatorFloor(elevator, 5);

		elevator.doors.close();
		assertClosedElevatorDoors(elevator);

		elevator.goToFloor(5); // movementDelta is 0 => No movement
		assertElevatorFloor(elevator, 5);

		elevator.goToFloor(1); // 4 movements DOWN
		assertElevatorFloor(elevator, 1);

		printEnd();
	}


	static void testElevatorV1_5() {
		System.out.println(" ==== TESTING ELEVATOR V1.5 (BuildingElevator) ==== ");

		Building building = new Building(0, 1);
		BuildingElevator elevator = new BuildingElevator(building, 0);
		assertElevatorFloor(elevator, 0);

		elevator.down(); // Out of range => No movement
		assertElevatorFloor(elevator, 0);

		elevator.down(); // Out of range => No movement
		assertElevatorFloor(elevator, 0);

		elevator.up();
		assertElevatorFloor(elevator, 1);

		elevator.up(); // Out of range => No movement
		assertElevatorFloor(elevator, 1);

		printEnd();
	}


	static void testElevatorV1_5AdaptedToV3() {
		System.out.println(" ==== TESTING ELEVATOR V1.5->3.0 (BuildingElevator Directly Adapted) ==== ");

		Building building = new Building(1, 5);
		UpgradeElevatorAdapter elevator = new UpgradeElevatorAdapter(
				new BuildingElevator(building, 2));
		assertElevatorFloor(elevator, 2);
		assertClosedElevatorDoors(elevator);

		elevator.goToFloor(5); // 3 movements UP
		assertElevatorFloor(elevator, 5);

		elevator.goToFloor(5); // movementDelta is 0 => No movement
		assertElevatorFloor(elevator, 5);

		elevator.doors.open();
		assertOpenElevatorDoors(elevator);

		elevator.goToFloor(1); // opened doors => no movement
		assertElevatorFloor(elevator, 5);

		elevator.doors.close();
		assertClosedElevatorDoors(elevator);

		elevator.goToFloor(1); // 4 movements DOWN
		assertElevatorFloor(elevator, 1);

		elevator.doors.open();
		assertOpenElevatorDoors(elevator);

		elevator.goToFloor(10); // opened doors => no movement
		assertElevatorFloor(elevator, 1);

		elevator.doors.close();
		assertClosedElevatorDoors(elevator);

		elevator.goToFloor(10); // Out of range => No movement
		assertElevatorFloor(elevator, 1);

		printEnd();
	}


	static void testElevatorV2() {
		System.out.println(" ==== TESTING ELEVATOR V2.0 (With Doors) ==== ");

		SimpleElevatorWithDoors elevator = new SimpleElevatorWithDoors(0);
		assertClosedElevatorDoors(elevator);

		elevator.doors.open();
		assertOpenElevatorDoors(elevator);

		assertElevatorFloor(elevator, 0);

		elevator.down(); // opened doors => no movement
		elevator.down(); // opened doors => no movement
		assertElevatorFloor(elevator, 0);

		elevator.up(); // opened doors => no movement
		assertElevatorFloor(elevator, 0);

		elevator.doors.close();
		assertClosedElevatorDoors(elevator);

		elevator.up(); // 2 movements UP
		elevator.up();
		assertElevatorFloor(elevator, 2);

		elevator.doors.open();
		assertOpenElevatorDoors(elevator);

		elevator.down(); // opened doors => no movement
		assertElevatorFloor(elevator, 2);

		elevator.doors.close();
		assertClosedElevatorDoors(elevator);

		elevator.down();
		assertElevatorFloor(elevator, 1);

		printEnd();
	}


	static void testElevatorV2_1() {
		System.out.println(" ==== TESTING ELEVATOR V2.1 (With Doors At Building) ==== ");

		Building building = new Building(0, 1);
		BuildingElevatorWithDoors elevator = new BuildingElevatorWithDoors(building, 0);
		assertElevatorFloor(elevator, 0);
		assertClosedElevatorDoors(elevator);

		elevator.doors.open();
		assertOpenElevatorDoors(elevator);

		elevator.down(); // opened doors => no movement
		elevator.down(); // opened doors => no movement
		assertElevatorFloor(elevator, 0);

		elevator.up(); // opened doors => no movement
		assertElevatorFloor(elevator, 0);

		elevator.doors.close();
		assertClosedElevatorDoors(elevator);

		elevator.up(); // 1 movement UP
		elevator.up(); // out of range ==> no movement
		assertElevatorFloor(elevator, 1);

		elevator.doors.open();
		assertOpenElevatorDoors(elevator);

		elevator.down(); // opened doors => no movement
		assertElevatorFloor(elevator, 1);

		elevator.doors.close();
		assertClosedElevatorDoors(elevator);

		elevator.down();
		assertElevatorFloor(elevator, 0);

		printEnd();
	}


	static void testWithAutoDoorsElevatorV2() {
		System.out.println(" ==== TESTING ELEVATOR V2.1 (With AutoDoors) ==== ");

		WithAutoDoorsDecorator<SimpleElevatorWithDoors> elevator =
				new WithAutoDoorsDecorator<>(
						new SimpleElevatorWithDoors(0));
		assertElevatorFloor(elevator, 0);
		assertClosedElevatorDoors(elevator);

		elevator.down();
		assertOpenElevatorDoors(elevator);
		elevator.down();
		assertElevatorFloor(elevator, -2);
		assertOpenElevatorDoors(elevator);

		elevator.up();
		assertElevatorFloor(elevator, -1);
		assertOpenElevatorDoors(elevator);

		elevator.up();
		assertOpenElevatorDoors(elevator);
		elevator.up();
		assertElevatorFloor(elevator, 1);
		assertOpenElevatorDoors(elevator);

		elevator.down();
		assertElevatorFloor(elevator, 0);
		assertOpenElevatorDoors(elevator);

		elevator.down();
		assertElevatorFloor(elevator, -1);
		assertOpenElevatorDoors(elevator);

		printEnd();
	}


	static void testWithAutoDoorsElevatorV2_1() {
		System.out.println(" ==== TESTING ELEVATOR V2.1 (With AutoDoors At Building) ==== ");

		Building building = new Building(-1, 2);
		WithAutoDoorsDecorator<BuildingElevatorWithDoors> elevator =
				new WithAutoDoorsDecorator<>(
						new BuildingElevatorWithDoors(building, 0));
		assertElevatorFloor(elevator, 0);
		assertClosedElevatorDoors(elevator);

		elevator.down();
		assertElevatorFloor(elevator, -1);
		assertOpenElevatorDoors(elevator);

		elevator.up();
		assertElevatorFloor(elevator, 0);
		assertOpenElevatorDoors(elevator);

		elevator.up();
		assertOpenElevatorDoors(elevator);
		elevator.up();
		assertElevatorFloor(elevator, 2);
		assertOpenElevatorDoors(elevator);

		elevator.down();
		assertElevatorFloor(elevator, 1);
		assertOpenElevatorDoors(elevator);

		elevator.down();
		assertElevatorFloor(elevator, 0);
		assertOpenElevatorDoors(elevator);

		printEnd();
	}


	static void testElevatorV2AdaptedToV3() {
		System.out.println(" ==== TESTING ELEVATOR V2.0->3.0 (Directly Adapted / With Doors) ==== ");

		UpgradeElevatorAdapter elevator = new UpgradeElevatorAdapter(
				new SimpleElevatorWithDoors(0));
		assertClosedElevatorDoors(elevator);
		assertElevatorFloor(elevator, 0);

		elevator.doors.open();
		assertOpenElevatorDoors(elevator);

		elevator.goToFloor(-2); // open doors => no movement DOWN
		assertElevatorFloor(elevator, 0);

		elevator.goToFloor(1); // open doors => no movement UP
		assertElevatorFloor(elevator, 0);

		assertOpenElevatorDoors(elevator);
		elevator.doors.close();
		assertClosedElevatorDoors(elevator);

		elevator.goToFloor(2); // 2 movements UP
		assertElevatorFloor(elevator, 2);

		assertClosedElevatorDoors(elevator);
		elevator.doors.open();
		assertOpenElevatorDoors(elevator);

		elevator.goToFloor(1); // open doors => no movement DOWN
		assertElevatorFloor(elevator, 2);

		assertOpenElevatorDoors(elevator);
		elevator.doors.close();
		assertClosedElevatorDoors(elevator);

		elevator.goToFloor(0); // 2 movements DOWN
		assertElevatorFloor(elevator, 0);

		printEnd();
	}


	static void testElevatorV2_1AdaptedToV3() {
		System.out.println(" ==== TESTING ELEVATOR V2.1->3.0 (Directly Adapted / With Doors At Building) ==== ");

		Building building = new Building(0, 5);
		UpgradeElevatorAdapter elevator = new UpgradeElevatorAdapter(
				new BuildingElevatorWithDoors(building, 2));
		assertElevatorFloor(elevator, 2);
		assertClosedElevatorDoors(elevator);

		elevator.goToFloor(5); // 3 movements UP
		assertElevatorFloor(elevator, 5);

		elevator.goToFloor(5); // movementDelta is 0 => No movement
		assertElevatorFloor(elevator, 5); // 5

		elevator.goToFloor(1); // 4 movements DOWN
		assertElevatorFloor(elevator, 1);

		elevator.goToFloor(10); // Out of range => No movement
		assertElevatorFloor(elevator, 1);

		assertClosedElevatorDoors(elevator);
		elevator.doors.open();
		assertOpenElevatorDoors(elevator);

		elevator.down(); // open doors => no movement
		assertOpenElevatorDoors(elevator);
		elevator.down(); // open doors => no movement
		assertElevatorFloor(elevator, 1);
		assertOpenElevatorDoors(elevator);

		elevator.up(); // open doors => no movement
		assertElevatorFloor(elevator, 1);
		assertOpenElevatorDoors(elevator);

		elevator.goToFloor(2); // open doors => no movement
		assertElevatorFloor(elevator, 1);
		assertOpenElevatorDoors(elevator);

		elevator.doors.close();
		assertClosedElevatorDoors(elevator);

		elevator.up(); // 2 movements UP
		assertClosedElevatorDoors(elevator);
		elevator.up();
		assertClosedElevatorDoors(elevator);
		assertElevatorFloor(elevator, 3);

		elevator.doors.open();
		assertOpenElevatorDoors(elevator);

		elevator.down(); // open doors => no movement
		assertOpenElevatorDoors(elevator);
		assertElevatorFloor(elevator, 3);

		elevator.doors.close();
		assertClosedElevatorDoors(elevator);

		elevator.down();
		assertClosedElevatorDoors(elevator);
		assertElevatorFloor(elevator, 2);

		printEnd();
	}


	static void testElevatorV3() {
		System.out.println(" ==== TESTING ELEVATOR V3.0 (SimpleFlooredElevator) ==== ");

		SimpleFlooredElevator elevator = new SimpleFlooredElevator(1);
		assertElevatorFloor(elevator, 1);
		assertClosedElevatorDoors(elevator);

		elevator.goToFloor(3); // 2 movements UP
		assertElevatorFloor(elevator, 3);
		assertClosedElevatorDoors(elevator);

		elevator.doors.open();
		assertOpenElevatorDoors(elevator);

		elevator.goToFloor(4); // opened doors => no movement
		assertElevatorFloor(elevator, 3);
		assertOpenElevatorDoors(elevator);

		elevator.doors.close();
		assertClosedElevatorDoors(elevator);

		elevator.goToFloor(3); // no movements
		assertElevatorFloor(elevator, 3);
		assertClosedElevatorDoors(elevator);

		elevator.goToFloor(0); // 3 movements DOWN
		assertElevatorFloor(elevator, 0);
		assertClosedElevatorDoors(elevator);

		printEnd();
	}


	static void testWithAutoDoorsElevatorV2AdaptedToV3() {
		System.out.println(" ==== TESTING ELEVATOR V2.1->3.0 (Directly Adapted / With AutoDoors) ==== ");

		UpgradeElevatorAdapter elevator = new UpgradeElevatorAdapter(
				new WithAutoDoorsDecorator<>(
						new SimpleElevatorWithDoors(1)));
		assertElevatorFloor(elevator, 1);
		assertClosedElevatorDoors(elevator);

		elevator.goToFloor(2); // 1 movements UP
		assertElevatorFloor(elevator, 2);
		assertOpenElevatorDoors(elevator);

		elevator.goToFloor(4); // 2 movements UP
		assertElevatorFloor(elevator, 4);
		assertOpenElevatorDoors(elevator);

		elevator.goToFloor(4); // movementDelta is 0 => No movement
		assertElevatorFloor(elevator, 4);
		assertOpenElevatorDoors(elevator);

		elevator.goToFloor(3); // 1 movements DOWN
		assertElevatorFloor(elevator, 3);
		assertOpenElevatorDoors(elevator);

		elevator.doors.open();
		assertOpenElevatorDoors(elevator);

		elevator.goToFloor(0); // opened doors => no movement
		assertElevatorFloor(elevator, 3);
		assertOpenElevatorDoors(elevator);

		elevator.doors.close();
		assertClosedElevatorDoors(elevator);

		elevator.goToFloor(0); // 3 movements DOWN
		assertElevatorFloor(elevator, 0);
		assertOpenElevatorDoors(elevator);

		printEnd();
	}


	static void testWithAutoDoorsElevatorV2_1AdaptedToV3() {
		System.out.println(" ==== TESTING ELEVATOR V2.1->3.0 (Directly Adapted / With AutoDoors At Building) ==== ");

		Building building = new Building(0, 3);
		UpgradeElevatorAdapter elevator = new UpgradeElevatorAdapter(
				new WithAutoDoorsDecorator<>(
						new BuildingElevatorWithDoors(building)));
		assertElevatorFloor(elevator, 0);
		assertClosedElevatorDoors(elevator);

		elevator.goToFloor(0); // movementDelta is 0 => No movement
		assertElevatorFloor(elevator, 0);
		assertOpenElevatorDoors(elevator);

		elevator.goToFloor(1); // 1 movements UP
		assertElevatorFloor(elevator, 1);
		assertOpenElevatorDoors(elevator);

		elevator.goToFloor(3); // 2 movements UP
		assertElevatorFloor(elevator, 3);
		assertOpenElevatorDoors(elevator);

		elevator.goToFloor(4); // Out of range => No movement
		assertElevatorFloor(elevator, 3);
		assertOpenElevatorDoors(elevator);

		elevator.goToFloor(6); // Out of range => No movement
		assertElevatorFloor(elevator, 3);
		assertOpenElevatorDoors(elevator);

		elevator.goToFloor(3); // movementDelta is 0 => No movement
		assertElevatorFloor(elevator, 3);
		assertOpenElevatorDoors(elevator);

		elevator.goToFloor(2); // 1 movements DOWN
		assertElevatorFloor(elevator, 2);
		assertOpenElevatorDoors(elevator);

		elevator.doors.open();
		assertOpenElevatorDoors(elevator);

		elevator.goToFloor(0); // opened doors => no movement
		assertElevatorFloor(elevator, 0);
		assertOpenElevatorDoors(elevator);

		elevator.doors.close();
		assertClosedElevatorDoors(elevator);

		elevator.goToFloor(0); // 2 movements DOWN
		assertElevatorFloor(elevator, 0);
		assertOpenElevatorDoors(elevator);

		printEnd();
	}


	static void testElevatorV3_1() {
		System.out.println(" ==== TESTING ELEVATOR V3.0 (BuildingFlooredElevator) ==== ");

		Building building = new Building(1, 6);
		BuildingFlooredElevator elevator = new BuildingFlooredElevator(building);
		assertElevatorFloor(elevator, 1);

		elevator.goToFloor(3); // 2 movements UP
		assertElevatorFloor(elevator, 3);

		elevator.goToFloor(3); // no movements
		assertElevatorFloor(elevator, 3);

		elevator.goToFloor(7); // out of range => no movements
		assertElevatorFloor(elevator, 3);

		elevator.goToFloor(4); // 1 movement UP
		assertElevatorFloor(elevator, 4);

		elevator.doors.open();
		assertOpenElevatorDoors(elevator);

		elevator.goToFloor(6); // opened doors => no movement
		assertElevatorFloor(elevator, 4);

		elevator.doors.close();
		assertClosedElevatorDoors(elevator);

		elevator.goToFloor(1); // 4 movement DOWN
		assertElevatorFloor(elevator, 1);

		printEnd();
	}


	static void testElevatorV3_xAdaptedToV1() {
		System.out.println(" ==== TESTING ELEVATOR V3.x->1.0 (SimpleElevator Directly Adapted) ==== ");

		DowngradeElevatorAdapter elevator = new DowngradeElevatorAdapter(
				new SimpleFlooredElevator(1));
		assertElevatorFloor(elevator, 1);
		assertClosedElevatorDoors(elevator);

		elevator.down();
		assertElevatorFloor(elevator, 0);

		elevator.up();
		assertElevatorFloor(elevator, 1);

		elevator.goToFloor(25);
		assertElevatorFloor(elevator, 25);

		printEnd();
	}


	static void testWithAutoDoorsElevatorV3() {
		System.out.println(" ==== TESTING ELEVATOR V3.0 (With AutoDoors) ==== ");

		elevatorsystems.v3.WithAutoDoorsDecorator<SimpleFlooredElevator> elevator =
				new elevatorsystems.v3.WithAutoDoorsDecorator<>(
						new SimpleFlooredElevator(0));
		assertElevatorFloor(elevator, 0);
		assertClosedElevatorDoors(elevator);

		elevator.getDoors().close();
		assertClosedElevatorDoors(elevator);

		elevator.goToFloor(-2); // 2 movements DOWN with closed doors
		assertElevatorFloor(elevator, -2);
		assertOpenElevatorDoors(elevator);

		elevator.getDoors().open();
		assertOpenElevatorDoors(elevator);

		elevator.goToFloor(5); // 7 movements UP with open doors
		assertElevatorFloor(elevator, 5);
		assertOpenElevatorDoors(elevator);

		printEnd();
	}


	static void testBuildingWithElevatorsReport() {
		System.out.println(" ==== TESTING BUILDING ELEVATORS REPORT V3.0 ==== ");

		BuildingWithElevators<IsBuildingElevator> building =
				new BuildingWithElevators<>(1, 5);
		// create a random array of building elevators with in range random floor state
		final ThreadLocalRandom randomizer = ThreadLocalRandom.current();
		final IsBuildingElevator[] elevators = new IsBuildingElevator[randomizer.nextInt(1, 21)];
		for (int i = 0; i < elevators.length; i++) {
			int random = randomizer.nextInt(400);
			int floor = randomizer.nextInt(building.getMinFloor(), building.getMaxFloor() + 1);
			if (random < 100) {
				elevators[i] = new BuildingElevator(building, floor);
			} else if (random < 200) {
				elevators[i] = new BuildingAdaptedElevator(building, floor);
			} else if (random < 300) {
				elevators[i] = new BuildingElevatorWithDoors(building, floor);
			} else {
				elevators[i] = new BuildingFlooredElevator(building, floor);
			}
		}
		building.setElevators(elevators);

		building.reportElevatorsState(System.out);

		printEnd();
	}


	static void assertElevatorFloor(HasFloorIndicator elevator, int expectedFloor) throws AssertionError {
		int currentFloor = elevator.getCurrentFloor();
		if (currentFloor != expectedFloor) {
			throw new AssertionError(String.format("Floor: %d != %d", currentFloor, expectedFloor));
		}
		System.err.println("El ascensor está en el piso: " + elevator.getCurrentFloor());
	}

	static void assertClosedElevatorDoors(IsElevatorWithDoors<?> elevator) throws AssertionError {
		boolean currentState = elevator.getDoors().isClosed();
		if (!currentState) {
			throw new AssertionError(String.format("Puertas cerradas: %s", currentState));
		}
		System.err.println("El ascensor tiene las puertas cerradas");
	}

	static void assertOpenElevatorDoors(IsElevatorWithDoors<?> elevator) throws AssertionError {
		boolean currentState = elevator.getDoors().isOpen();
		if (!currentState) {
			throw new AssertionError(String.format("Puertas abiertas: %s", currentState));
		}
		System.err.println("El ascensor tiene las puertas abiertas");
	}



	static void printEnd() {
		System.out.println(" ==== FIN ==== ");
		System.out.println();
		System.out.println();
	}
}
