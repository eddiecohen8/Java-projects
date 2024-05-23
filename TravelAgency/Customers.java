import java.util.Vector;

public class Customers implements Ticketable, sumSpentable {
	private int ID;
	private String Name;
	private int Age;
	private String Gender;
	private int RegisteredBy;
	private Vector<Trips> TripsBought;

	
	public Customers (int ID, String Name, int Age, String Gender, int RegisteredBy) {//constructor
		this.ID = ID;
		this.Name = Name;
		if(Age>85||Age<0)//the required range
			throw new CustomerAgeException();
		this.Age = Age;
		this.Gender = Gender;
		this.RegisteredBy = RegisteredBy;
		this.TripsBought = new Vector<Trips>();
	}
	
	//////////////////// Getters ////////////////////////////
	
	public int getID() {
		return ID;
	}
	
	public String getName() {
		return Name;
	}
	
	public int getAge() {
		return Age;
	}
	
	public String getGender() {
		return Gender;
	}
	
	public int getRegisteredBy() {// return the ID number of the one who register the customer
		return RegisteredBy;
	}
	
	public Vector<Trips> getTripsBought() {//vector of all the trips the customers bought
		return TripsBought;
	}
	
	public void addToTripsBought(Trips t) {//add a new trip to the vector
		TripsBought.add(t);
	}
	
	public double SumSpentPerCustomer(Vector<TicketsSales> TicketsSales) {//calculates the total tickets price 
		double sumSpent =0;
		for(int i=0; i<this.getTripsBought().size();i++) {
			double pricePerTrip = this.getTripsBought().elementAt(i).getPricePerTicket();//save the temp ticket price 
			int tripDestFromTripsBought = this.getTripsBought().elementAt(i).getNumber();//save the temp trip number
			for(int j=0; j<TicketsSales.size();j++) {
				int customerIDTickets = TicketsSales.elementAt(j).getSoldTo();
				int tripDestFromTickets = TicketsSales.elementAt(j).getTrip();
				if((customerIDTickets==this.getID())&&(tripDestFromTickets==tripDestFromTripsBought)) {
					int numOfTickets = TicketsSales.elementAt(j).getNumberOfTickets();//find the number of ticket the customer bought to the trip
					sumSpent += numOfTickets*pricePerTrip;
					break;
				}
			}
		}
		return sumSpent;
	}
	
	public int NumberOfTicketsBought(Vector<TicketsSales> TicketsSales) {//Count all the tickets the customer bought  
		int numberOfTicketsBought = 0;
		for(int i=0; i<TicketsSales.size(); i++) {
			if(this.getID()==TicketsSales.elementAt(i).getSoldTo()) {//find the relevant pointer at the vector-ticket sale
				numberOfTicketsBought+= TicketsSales.elementAt(i).getNumberOfTickets();//
			}
		}
		return numberOfTicketsBought;
	}

	public int whoHasMoreTickets(Object c, Vector<TicketsSales> TicketsSales) {//the Implements of Ticketable
		if(this.NumberOfTicketsBought(TicketsSales)>((Customers)c).NumberOfTicketsBought(TicketsSales))
			return 1;//my number of tickets is bigger than the other customer
		else if(this.NumberOfTicketsBought(TicketsSales)<((Customers)c).NumberOfTicketsBought(TicketsSales))
			return -1;//the other customer have more tickets than mine
		else
			return 0;//the number of tickets is equal
	}

	public int whoSpentMore(Object c, Vector<TicketsSales> TicketsSales) {//the Implements of sumSpentable
		if(this.SumSpentPerCustomer(TicketsSales)>((Customers)c).SumSpentPerCustomer(TicketsSales))
			return 1;
		else if(this.SumSpentPerCustomer(TicketsSales)<((Customers)c).SumSpentPerCustomer(TicketsSales))
			return -1;
		else
			return 0;
		
	}
}
	

	

