
public class EnteringBus extends Bus{
	private int cargoAmount;
	private int totalCost;
	private boolean isSecurityIssue;
	
	public EnteringBus(String code, int passengersNum, int arrivalTime, int cargoAmount) { //Constructor
		this.code=code;
		this.passengersNum=passengersNum;
		this.arrivalTime=arrivalTime;
		this.cargoAmount=cargoAmount;
		this.totalCost=0;
		this.isSecurityIssue=false;
		this.gateKeeperQueue=null;
	}
	
	public synchronized int getCargoAmount() {
		return cargoAmount;
	}
	
	public synchronized int getTotalCost() {
		return totalCost;
	}
	
	public synchronized boolean getIsSecurityIssue() {
		return isSecurityIssue;
	}
	
	public synchronized void setTotalCost(int cost) { //Add the cost of each treatment to total cost
		totalCost += cost;
	}
	
	public synchronized void setSecurityIssue() { //If a security issue was found, turned true
			isSecurityIssue=true;
	}
}
