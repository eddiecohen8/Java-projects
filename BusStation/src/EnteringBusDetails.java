
public class EnteringBusDetails extends BusDetails {
	private int cargoAmount;
	private int totalCost;
	private boolean IsSecurityIssue;	
	private boolean IsFuelUsed;
	
	
	public EnteringBusDetails(EnteringBus bus) { //Constructor by the specific bus that is required
		this.busCode = bus.getCode();
		this.passengersNum=bus.getPassengersNum();
		this.cargoAmount=bus.getCargoAmount();
		this.totalCost=bus.getTotalCost();
		this.IsSecurityIssue=bus.getIsSecurityIssue();
		this.timeInFasility=bus.getArrivalTime();
		this.IsFuelUsed=false;
	}
	
	
	public int getCargoAmount() {
		return cargoAmount;
	}
	
	public int getTotalCost() {
		return totalCost;
	}
	
	public boolean getIsSecurityIssue() {
		return IsSecurityIssue;
	}
	
	public boolean getIsFuelUsed() {
		return IsFuelUsed;
	}
	
	public boolean setFuelUsed() { //This bus used fuel
		return IsFuelUsed=true;
	}
}
