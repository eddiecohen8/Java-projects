import java.util.Vector;
import java.io.*;

public class Manager extends Employee {

	private Vector <BusDetails> SaveBusDeatails; //Vector of all bus details reports
	private int totalPassengers;
	private int totalCargo;
	private int totalCost;
	private int totalFuelUsed;
	private int TotalSecurityIssue;
	private int busCounter;
	private BusStation busStation;
	private String name;
	private UnboundedQueue<BusDetails> ThisQueue; //bus details queue

	public Manager(String name,UnboundedQueue<BusDetails> ThisOueue, BusStation busStation ) {
		this.name=name;
		this.ThisQueue=ThisOueue;
		this.SaveBusDeatails=new Vector<BusDetails>();
		this.busCounter=0;
		this.busStation=busStation;
	}



	private void insertToExiting(LeavingBusDetails report) { //Create the exiting busses file and add to it
        String content = report.getBusCode() + " " + report.getPassengersNum() + " " + report.getDestination() + " " + report.gettimeInFasility()+"\n";
        // Try block to check if exception occurs
        try {
            FileWriter fWriter=new FileWriter("Exiting.txt",true); //If the file doesn't exist, create it and add the bus
            fWriter.write(content);
            fWriter.close();
        }
        catch (IOException e) {
        }
    }
	
	private void insertToEntering(EnteringBusDetails report) { //Create the entering busses file and add to it
        String content = report.getBusCode() + " " + report.getPassengersNum() +"	"+ report.getCargoAmount() + " " + report.getTotalCost()+"	"+report.getIsSecurityIssue()+"	"+report.gettimeInFasility()+ "\n";
        // Try block to check if exception occurs
        try {
            FileWriter fWriter=new FileWriter("Entering.txt",true); //If the file doesn't exist, create it and add the bus
            fWriter.write(content);
            fWriter.close();
        }
        catch (IOException e) {
        }
    }
	



	public synchronized void setTotalCost(int num) {
		totalCost+=num;
	}

	public synchronized void setTotalPassengers(int num) {
		totalPassengers+=num;
	}

	public synchronized void setTotalCargo(int num) {
		totalCargo+=num;
	}

	public synchronized void setTotalFuelUsed(boolean IsFuelUsed) {
		if(IsFuelUsed) 
			totalFuelUsed+=200;
	}

	public synchronized void setTotalSecurityIssue(boolean IsSecurityIssue) {
		if(IsSecurityIssue)
			TotalSecurityIssue+=1;
	}

	public synchronized void setBusCounter() {
		busCounter++;
	}

	public synchronized boolean didDayEnd() { //If the counter reaches the vector size the day ended
		int totalBusesNum = busStation.getBusVector().size();
		if(busCounter==totalBusesNum)
			return true;
		return false;
	}

	public synchronized void setTotal(BusDetails busDetails) { //Updates all the fields in busDetails
		setTotalPassengers(busDetails.getPassengersNum()); 
		setBusCounter();

		if(busDetails instanceof EnteringBusDetails) { //The fields that are only in enteringBuses
			setTotalCargo(((EnteringBusDetails)busDetails).getCargoAmount());
			setTotalCost(((EnteringBusDetails)busDetails).getTotalCost());
			setTotalFuelUsed(((EnteringBusDetails)busDetails).getIsFuelUsed());
			setTotalSecurityIssue(((EnteringBusDetails)busDetails).getIsSecurityIssue());
		}

	}

	public synchronized void printEndOfDay() { //Prints all relevant fields when the day is over
		System.out.println("Number of passengeres in total: " + totalPassengers);
		System.out.println("Number of cargo in total: " + totalCargo);
		System.out.println("The most popular destination: " + busStation.mostPopularDest());
		System.out.println("Number of total cost: " + totalCost);
		System.out.println("Amount of fuel used in total: " + totalFuelUsed);
		System.out.println("Number of security issues in total: " + TotalSecurityIssue);


	}
	
	
	public void printInfo(BusDetails busDetails) { //print details of each bus
		System.out.println("Bus Code: " +busDetails.getBusCode());
		System.out.println("Time in fasility: "+ busDetails.gettimeInFasility());
		if(busDetails instanceof EnteringBusDetails) { //Only entering busses can have total cost, others get 0 by default
			System.out.println("Total Cost: "+((EnteringBusDetails)busDetails).getTotalCost());//? casting oK?
		}
		else {
			System.out.println("Total cost: 0");
		}
	}
	public void closeStation() {
		printEndOfDay(); //Print everything that's needed
		busStation.setEndOfDay();
	}
	
	public  void WriteToForm(BusDetails busDetails) { //Write to the created file
		if(busDetails instanceof EnteringBusDetails) {
			insertToEntering((EnteringBusDetails)busDetails);
		}
		if(busDetails instanceof LeavingBusDetails) {
			insertToExiting((LeavingBusDetails) busDetails);
		}		
	}

	public void run() {
		while(!didDayEnd()) {
			BusDetails busDetails = ThisQueue.extract();
			setTotal(busDetails); //Update all the final totals
			System.out.println(busCounter);
			WriteToForm(busDetails); //Send to write to form method
			try {
				this.sleep(2000);
			} catch (InterruptedException e) {}

			this.printInfo(busDetails);
		}
		closeStation();//let every worker know that the day is over
	}







}