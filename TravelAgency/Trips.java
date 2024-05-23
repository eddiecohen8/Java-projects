import java.util.Vector;

public class Trips implements Ticketable{
	private int Number;
	private int Type;
	private String Destination;
	private double PricePerTicket;
	private Vector<Customers> CustomersInTrip;

	//Contractor
	public Trips(int Number, int Type, String Destination, double PricePerTicket) throws ImpossibleTripLevelException {
		this.Number = Number;
		if(Type<1 || Type>5)
			throw new ImpossibleTripLevelException();
		this.Type = Type;
		this.Destination = Destination;
		this.PricePerTicket = PricePerTicket;
		this.CustomersInTrip = new Vector<Customers>();
	}
	
	
	////////////////////// getters ////////////////////////////////
	
	public int getNumber() {//return trip number
		return Number;
	}
	
	public int getType() {//return the difficulty level of the trip
		return Type;
	}
	
	public String getDestination() {//return the destination name
		return Destination;
	}
	
	public double getPricePerTicket() {//return the price per ticket
		return PricePerTicket;
	}
	
	public Vector<Customers> getCustomersInTrip() {//return the vector of customers registered to trip
		return CustomersInTrip;
	}
	
	public void addCustomerToTrip(Customers c) {//add a new customer to the vector
		CustomersInTrip.add(c);
	}
	

	//////////////////////// Methods ///////////////////////////////
	
	
	public int NumberOfTicketsBought(Vector<TicketsSales> TicketsSales) {//count the total ticket that sold to the trip 
		int numberOfTicketsBought = 0;
		for(int i=0; i<TicketsSales.size(); i++) {
			if(this.getNumber()==TicketsSales.elementAt(i).getTrip()) {
				numberOfTicketsBought+= TicketsSales.elementAt(i).getNumberOfTickets();
			}
		}
		return numberOfTicketsBought;
	}
	
	
	public int whoHasMoreTickets(Object t, Vector<TicketsSales> TicketsSales) {//implements Ticketable
		if(this.NumberOfTicketsBought(TicketsSales)>((Trips)t).NumberOfTicketsBought(TicketsSales))
			return 1;//this salary is bigger than the other employee
		else if(this.NumberOfTicketsBought(TicketsSales)<((Trips)t).NumberOfTicketsBought(TicketsSales))
			return -1;//the other employee salary is bigger than this salary
		else
			return 0;// both salaries are equals
		
	}
	
	
	
}


