import java.util.Random;

public class GateKeeper extends Employee {
	
	static Random rand = new Random();
	
	private int numOfBuses;
	private int busCounter;
	private UnboundedQueue<Bus> ThisQueue; //Current queue
	private UnboundedQueue<EnteringBus> NextQueue; //Logistics queue
	private UnboundedQueue<BusDetails> managerQueue; //Send the reports created to this queue
	private GateKeeper secondKeeper; //The gatekeepers get there friend gatekeeper with the set method
	
	public GateKeeper(UnboundedQueue<Bus> ThisQueue, UnboundedQueue<EnteringBus> NextQueue, UnboundedQueue<BusDetails> managerQueue, int numOfBuses) { //Constructor
		busCounter = 0;
		this.ThisQueue=ThisQueue;
		this.NextQueue=NextQueue;
		this.managerQueue=managerQueue;
		this.numOfBuses=numOfBuses;
	}
	
	private synchronized Bus extractBus() { //Take bus out of queue, first take leaving buses
		for(int i=0; i<ThisQueue.getBuffer().size(); i++) {
			if(ThisQueue.getBuffer().elementAt(i) instanceof LeavingBus) {
				busCounter++;
				return ThisQueue.extractSpecific(ThisQueue.getBuffer().elementAt(i)); //Use the specific extract method to take out the first leaving bus
			}
		}
		busCounter++;
		return ThisQueue.extract(); //No leaving bus in queue- take the first bus
	}
	
	private synchronized void createReport(LeavingBus bus) { //If the bus is a leaving bus create a report
		LeavingBusDetails l = new LeavingBusDetails(bus);
		managerQueue.insert(l);
	}
	public int getBusCounter() {
		return busCounter;
	}
	
	public void setSecondKeeper(GateKeeper g) { //Adds the second gatekeeper, is used to count both of their busses counters
		this.secondKeeper=g;
	}
	
	
	public void run() {
		while(this.busCounter+this.secondKeeper.getBusCounter()<numOfBuses) { //When both gate keepers counted together the full bus amount, stop
			Bus bus = extractBus(); //Take the relevant bus out of the queue (Takes 5-10 seconds)
			int workTime=rand.nextInt(5000, 11000);
			bus.setArrivalTime(workTime);
			try {
				this.sleep(workTime);
			} catch (InterruptedException e) {}
			if(bus instanceof LeavingBus) { //If leaving bus- create report
				createReport((LeavingBus)bus);
			}
			else { //If entering bus- take it to next queue and update queue num in bus attribute
				NextQueue.insert((EnteringBus)bus);
				int NextQueueNum = NextQueue.getQueueNum();
				bus.setRelevantQueueNum(NextQueueNum);
			}
		}
	}
}
	

