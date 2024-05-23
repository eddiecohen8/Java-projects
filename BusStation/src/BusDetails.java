abstract class BusDetails {
	
	protected Bus bus;
	protected String busCode;
	protected int passengersNum;
	protected int timeInFasility;
	
	
	public String getBusCode() {//return the bus code
		return busCode;
	}
	
	public int getPassengersNum() {//return the number of passengers at the bus
		return passengersNum;
	}
	
	public int gettimeInFasility() {//return the time in fasility
		return timeInFasility;
	}
}