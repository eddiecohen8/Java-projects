import java.util.Random;

public class LogisticsAttendant extends Employee {
	
	static Random rand = new Random();

	private String name;
	private int workRate;
	private UnboundedQueue<EnteringBus> ThisQueue;
	private UnboundedQueue<EnteringBus> NextQueue;
	private UnboundedQueue<EnteringBus> technitionsQueue;

	
	public LogisticsAttendant(String name,int workRate,UnboundedQueue<EnteringBus> ThisQueue, UnboundedQueue<EnteringBus> NextQueue, UnboundedQueue<EnteringBus> technitionsQueue) { //Constructor
		this.name=name;
		this.workRate=workRate;
		this.ThisQueue=ThisQueue;
		this.NextQueue=NextQueue;
		this.technitionsQueue=technitionsQueue;
	}
	
	
	private synchronized boolean doesBusWork() { //1 in 10 times the bus has a technical problem
		int randomNum = rand.nextInt(1, 11);
		if(randomNum==1)
			return false;
		return true;
	}
	
	private synchronized void busTreatment(EnteringBus bus) {
		if(doesBusWork()) { //If the bus has no problem, move it to next queue and update queue num
			NextQueue.insert(bus);
			int NextQueueNum = NextQueue.getQueueNum();
			bus.setRelevantQueueNum(NextQueueNum);
		}
		else { //If there's a problem send to technitions queue
			technitionsQueue.insert(bus);
		}
	}

	public void run() {
		while(!ThisQueue.getEndOfDay()) { //Work until day is over
			EnteringBus bus = ThisQueue.extract();
			if(bus==null) { //When a null bus is inserted, everyone wakes up and notices that the condition is false
				break;
			}
			int sleepTime=workRate*bus.getCargoAmount();
			bus.setArrivalTime(sleepTime);
			try {
				this.sleep(sleepTime);
			} catch (InterruptedException e) {}
			busTreatment(bus); //Send to treatment method
		}
	}
}
