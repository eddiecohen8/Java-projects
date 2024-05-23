
public class LeavingBus extends Bus{
	private String destination; //Has destination
	
	public String getDestination() {
		return destination;
	}
	
	public LeavingBus(String code, int passengersNum, int arrivalTime, String destination) { //Constructor
		this.code=code;
		this.passengersNum=passengersNum;
		this.arrivalTime=arrivalTime;
		this.destination=destination;
		this.gateKeeperQueue=null;
	}
	
}
