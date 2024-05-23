
public class FuelTruck { //Each fuel attendant has his own truck
	private int capacity;
	private int fuelAmount;
	
	public FuelTruck(int capacity) { //Constructor
		this.capacity = capacity;
		this.fuelAmount = capacity;
	}
	
	public int getFuelAmount() {
		return fuelAmount;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public void useFuel() { //Subtract 200 fuel from the fuel amount
		fuelAmount-=200;
	}

	public void fillFuel() { //Fill fuel up to the max capacity
		fuelAmount=capacity;
	}

}
