import java.util.Random;
public class Cleaner extends Employee{

	static Random rand = new Random();

	private int seniority;
	private UnboundedQueue<EnteringBus> ThisQueue;//the current queue
	private BoundedQueue<EnteringBus> NextQueue;//the next queue
	private double cleaningTime;//information from GUI
	private UnboundedQueue<EnteringBus> technitionsQueue;//technician queue

	//Constructor 
	public Cleaner(int seniority, UnboundedQueue<EnteringBus> ThisQueue, BoundedQueue<EnteringBus> NextQueue, double cleaningTime, UnboundedQueue<EnteringBus> technitionsQueue) {
		this.seniority=seniority;
		this.ThisQueue=ThisQueue;
		this.NextQueue=NextQueue;
		this.cleaningTime=cleaningTime;
		this.technitionsQueue=technitionsQueue;
	}

	public void run() {
		while(!ThisQueue.getEndOfDay()) {//while the day is not over
			EnteringBus bus = ThisQueue.extract();//Take the bus out of the queue
			if(bus==null) { //When a null bus is inserted, everyone wakes up and notices that the condition is false
				break;
			}
			isSusObject(bus);//Checks if there is a suspicious object
			try {
				this.sleep((long)cleaningTime);
			} catch (InterruptedException e) {}

			bus.setArrivalTime((long)cleaningTime);//update the arrival time
			setBusNextOueue(bus);//insert the bus to the next relevant queue

		}
	}

	//insert the bus to the next relevant queue
	public synchronized void setBusNextOueue(EnteringBus bus) {
		if(doesBusWork()) {//checks if the bus work
			NextQueue.insert(bus);
			int NextQueueNum = NextQueue.getQueueNum();
			bus.setRelevantQueueNum(NextQueueNum);//update the number of the next queue
		}
		else {
			technitionsQueue.insert(bus);//insert to the technitions queue
		}
	}


	//Checks if there is a suspicious object
	public synchronized void isSusObject(EnteringBus bus) {
		int randomNum = rand.nextInt(1, 21);//The chance is 5% for suspicious object
		if(randomNum==1) {//a suspicious object was found
			try {
				this.sleep(2000);
			} catch (InterruptedException e) {}
			bus.setArrivalTime(2000);//update arrival time
			bus.setSecurityIssue();//update security issue
		}
	}

	//checks if the bus work
	public synchronized boolean doesBusWork() {
		int randomNum = rand.nextInt(1, 5);//The chance is 20% for bus breakdown
		if(randomNum==1)
			return false;
		return true;
	}

}