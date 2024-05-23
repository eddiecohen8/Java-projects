public class TicketsSales {
	private int Trip;
	private int SoldTo;
	private int NumberOfTickets;
	private int SoldBy;
	private String Site;
	
	//Contractor
	public TicketsSales(int Trip, int SoldTo, int NumberOfTickets, int SoldBy) {
		this.Trip = Trip;
		this.SoldTo = SoldTo;
		this.NumberOfTickets = NumberOfTickets;
		this.SoldBy = SoldBy;
		this.Site = "";
	}
	
	//Contractor for guide who register by a friend
	public TicketsSales(int Trip, int SoldTo, int NumberOfTickets, String Site) {
		this.Trip = Trip;
		this.SoldTo = SoldTo;
		this.NumberOfTickets = NumberOfTickets;
		this.SoldBy = 0;
		this.Site = Site;
	}
	
	public int getTrip() {//return the trip number
		return Trip;
	}
	
	public int getSoldTo() {//return the ID of the buyer
		return SoldTo;
	}
	
	public int getNumberOfTickets() {//return the number of tickets bought from the customer
		return NumberOfTickets;
	}
	
	public int getSoldBy() {//return the employee ID number that sold the ticket
		return SoldBy;
	}
	
	public String getSite() {//return the site that the customer bought the ticket from
		return Site;
	}
	
	



}
