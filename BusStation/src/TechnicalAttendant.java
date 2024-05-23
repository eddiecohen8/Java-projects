import java.util.Random;
import java.util.Vector;

public class TechnicalAttendant extends Employee{
	
	static Random rand = new Random();

	private UnboundedQueue<EnteringBus> ThisQueue; //Current queue + all possible queues that the bus came from
	private UnboundedQueue<EnteringBus> LogisticsQueue; 
	private UnboundedQueue<EnteringBus> CleanersQueue;
	private BoundedQueue<EnteringBus> FuelQueue;
	private UnboundedQueue<BusDetails> managerQueue;
	private String name;
	
	public TechnicalAttendant(String name, UnboundedQueue<EnteringBus> ThisQueue, UnboundedQueue<EnteringBus> LogisticsQueue, UnboundedQueue<EnteringBus> CleanersQueue, BoundedQueue<EnteringBus> FuelQueue, UnboundedQueue<BusDetails> managerQueue) {
		
		this.name=name;
		this.ThisQueue=ThisQueue;
		this.LogisticsQueue=LogisticsQueue;
		this.CleanersQueue=CleanersQueue;
		this.FuelQueue=FuelQueue;
		this.managerQueue=managerQueue;
	}

	private synchronized void createReport(EnteringBus bus) { //If bus is fixed after fuel problem, report is generated
		EnteringBusDetails l = new EnteringBusDetails(bus); // A new report
		managerQueue.insert(l); //Add report to the managers queue
	}
	
	private synchronized void busTreatment(EnteringBus bus) { //Adds to the bus total spent amount
		int cost=rand.nextInt(500, 1001);  //Random price
		bus.setTotalCost(cost); 
	}
	
	private synchronized void busNextStation(EnteringBus bus) {
		int lastQueueNum = bus.getRelevantQueueNum();
		if (lastQueueNum==2) { //Bus came from Logistics queue
			bus.setRelevantQueueNum(lastQueueNum+1);
			CleanersQueue.insert(bus); 
		}
		else if(lastQueueNum==3) { //Bus came from Cleaners queue
			bus.setRelevantQueueNum(lastQueueNum+1);
			FuelQueue.insert(bus);
		}
		else if (lastQueueNum==4) { //Bus came from Fuel queue
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {}
			bus.setRelevantQueueNum(lastQueueNum+1);
			createReport(bus);
		}
	}
	
	public void run() {
		while(!ThisQueue.getEndOfDay()) {
			EnteringBus bus = ThisQueue.extract(); //Take the bus out of the queue and 'fix it' for 3-5 seconds
			if(bus==null) {
				break;
			}
			int workTime=rand.nextInt(3000, 6000);
			bus.setArrivalTime(workTime);
			try {
				this.sleep(workTime);
			} catch (InterruptedException e) {}
			busTreatment(bus); //Add the treatment price to the bus total spent
			busNextStation(bus); //Send the bus to the next station according to his last station
		}
	}

}