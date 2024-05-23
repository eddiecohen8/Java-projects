abstract public class Bus extends Thread {
	protected String code;
	protected UnboundedQueue<Bus> gateKeeperQueue;
	protected int relevantQueueNum;
	protected int passengersNum;
	protected int arrivalTime;
	
	
	protected synchronized void setArrivalTime(long time) {//Calculates total arrival times
		arrivalTime += time;
	}
	
	protected synchronized String getCode() {
		return code;
	}
	
	protected synchronized int getPassengersNum() {
		return passengersNum;
	}
	
	protected synchronized int getArrivalTime() {
		return arrivalTime;
	}
	
	protected synchronized Queue<Bus> getGateKeeperQueue() {
		return gateKeeperQueue;
	}
	
	//Set the first queue- gate keeper
	protected synchronized void setGateKeeperQueue(UnboundedQueue<Bus> gateKeeper) {
		this.gateKeeperQueue=gateKeeper;
	}
	
	protected synchronized int getRelevantQueueNum() {//return the current queue
		return relevantQueueNum;
	}
	
	protected synchronized void setRelevantQueueNum(int queueNum) {//set the new queue
		relevantQueueNum=queueNum;
	}
	
	public void run() {
		try {
			this.sleep(arrivalTime);//sleep the relevant time
		} catch (InterruptedException e) {}
		gateKeeperQueue.insert(this);//insert to the gate keeper queue
	}
}