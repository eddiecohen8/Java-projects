
public class LeavingBusDetails extends BusDetails {
	private String destination;
	
	
	
	public LeavingBusDetails(LeavingBus bus) { //The report for each leaving bus
		this.busCode = bus.getCode();
		this.passengersNum=bus.getPassengersNum();
		this.destination=bus.getDestination();
		this.timeInFasility=bus.getArrivalTime();
	}
	
	public String getDestination() {
		return destination;
	}
	
}
