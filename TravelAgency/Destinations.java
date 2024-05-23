import java.util.ArrayList;
import java.util.Vector;

public class Destinations implements Ticketable{
	
	private String destinationName;
	private int numOfTickets;
	
	public Destinations(String destinaionName) {//Contractor
		this.destinationName = destinaionName;
		numOfTickets = 0;
	}

	public String getDestinationName() {// return destination Name
		return destinationName;
	}
	
	public int getNumOfTickets() {// return number Of Tickets
		return numOfTickets;
	}
	
	public void setNumOfTickets(int numOfTickets) {// setter for the number of ticket
		this.numOfTickets = numOfTickets;
	}
	
	//sum the number of tickets bought for this destination 
	public int ticketsPerDest(Vector<Trips> Trips, Vector<TicketsSales> TicketsSales) {
		int numOfTickets=0;
		for(int i=0; i<Trips.size(); i++) {
			if(Trips.elementAt(i).getDestination().equals(this.destinationName)) {//the trip is at this destination
				for(int j=0; j<TicketsSales.size();j++) {
					if(Trips.elementAt(i).getNumber()==TicketsSales.elementAt(j).getTrip()) {//finds the TicketsSales pointer for the trip
						numOfTickets +=  TicketsSales.elementAt(j).getNumberOfTickets();//update the number of ticket bought to this destination
					}
				}
			}
		}		
		return numOfTickets;				
	}

	public int whoHasMoreTickets(Object d, Vector<TicketsSales> TicketsSales) {//implements Ticketable
		if(this.getNumOfTickets()>((Destinations)d).getNumOfTickets())
			return 1;
		else if(this.getNumOfTickets()<((Destinations)d).getNumOfTickets())
			return -1;
		else
			return 0;	
	}


}
