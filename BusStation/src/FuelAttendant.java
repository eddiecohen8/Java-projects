import java.util.Random;

public class FuelAttendant extends Employee {
	
	static Random rand = new Random();

	private String name;
	private FuelTruck truck;
	private BoundedQueue<EnteringBus> ThisQueue;
	private UnboundedQueue<EnteringBus> technitionsQueue;
	private UnboundedQueue<BusDetails> managerQueue;
	
	
	public FuelAttendant(String name,FuelTruck truck,BoundedQueue<EnteringBus> ThisQueue,UnboundedQueue<EnteringBus> technitionsQueue,UnboundedQueue<BusDetails> managerQueue) {
		this.name=name;
		this.truck=truck;
		this.ThisQueue=ThisQueue;
		this.technitionsQueue=technitionsQueue;
		this.managerQueue=managerQueue;
	}
	
	private synchronized boolean isThereFuel() { //Checks if there's enough fuel for this bus
		if (truck.getCapacity()>=200)
			return true;
		return false;
	}
	
	public synchronized boolean doesBusWork() { //Checks if a problem emerged (30%)
		int randomNum = rand.nextInt(1, 11);
		if(randomNum<=3)
			return false;
		return true;
	}

	
	private synchronized void busTreatment(EnteringBus bus) {
		truck.useFuel(); //Subtract 200 from bus fuel amount
		if(doesBusWork()) { //Was there a problem with the fuel fill? IF not-
			createReport(bus);
		}
		else { //There was a problem, send bus to tech queue and mention it in the bus relevant attribute
			bus.setSecurityIssue();
			technitionsQueue.insert(bus);
		}	
	}

	private synchronized void createReport(EnteringBus bus) { //Create a report for the manager
		EnteringBusDetails l = new EnteringBusDetails(bus);
		l.setFuelUsed();
		managerQueue.insert(l);
	}
	
	
	public void run() {
		while(!ThisQueue.getEndOfDay()) {
			if(isThereFuel()) { //Check if there is enough fuel
				EnteringBus bus = ThisQueue.extract(); //Take the bus from queue and 'fill it' for 3-4 seconds
				if(bus==null) { //When a null bus is inserted, everyone wakes up and notices that the condition is false
					break;
				}
				int workTime=rand.nextInt(3000, 5000);
				try {
					this.sleep(workTime);
				} catch (InterruptedException e) {}
				bus.setArrivalTime(workTime);
				busTreatment(bus); //Send bus to next station
			}
			else {
				truck.fillFuel(); //Truck wasn't good, fill it up before doing with it anything
				try {
					this.sleep(5000);
				} catch (InterruptedException e) {}
			}

		}

	}
}
