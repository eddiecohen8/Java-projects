import java.util.ArrayList;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class TravelAgency  {

	Vector<Customers> Customers = new Vector<Customers>();
	Vector<Employees> Employees = new Vector<Employees>();
	Vector<Trips> Trips = new Vector<Trips>();
	Vector<TicketsSales> TicketsSales = new Vector<TicketsSales>();


	//method 1
	public TravelAgency(String Trips, String Employees, String Customers, String TicketsSales) {
		setTripsVector(Trips);
		setEmployeesVector(Employees);
		setCustomersVector(Customers);
		setTicketsSalesVector(TicketsSales);

		setEmployeesSalaryEvents();
		setCustomersToTrip();
		setTripsToCustomer();
	}


	private void setEmployeesVector(String Employees){//read from file Employees
		BufferedReader inFile = null;
		String line;
		try {
			FileReader fr = new FileReader(Employees + ".txt");
			inFile = new BufferedReader(fr);
			inFile.readLine();
			String tempLine;
			String[] arrLine = new String[5];
			while (!(line = inFile.readLine()).isBlank()) {
				tempLine = line;
				arrLine = tempLine.split("	");
				if (arrLine.length == 1)
					continue;
				int id = Integer.parseInt(arrLine[0]);
				String name = arrLine[1];
				int age = Integer.parseInt(arrLine[2]);
				String profession = arrLine[3];
				if (!arrLine[4].equals("")) {
					double bonusRate = Double.parseDouble(arrLine[4]);
					if (profession.equals("Maintenance Worker")) {
						MaintenanceTeam M = new MaintenanceTeam(id, name, age, profession, bonusRate);
						this.Employees.add(M);
					} else if (profession.equals("Security Guard")) {
						SecurityMan G = new SecurityMan(id, name, age, profession, bonusRate);
						this.Employees.add(G);
					} else if (profession.equals("Driver")) {
						Driver D = new Driver(id, name, age, profession, bonusRate);
						this.Employees.add(D);
					} else {
						Guide G = new Guide(id, name, age, profession, bonusRate);
						this.Employees.add(G);
					}
				} else {
					String phone = arrLine[5];
					MarketingTeam MK = new MarketingTeam(id, name, age, profession, phone);
					this.Employees.add(MK);
				}

			}

		} catch (FileNotFoundException exception) {
			System.out.println("The file " + Customers + " was not found.");
		} catch (IOException exception) {
			System.out.println(exception);
		} finally {
			try {
				inFile.close();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
	}

	private void setTripsVector(String Trips) {//read from file Trips
		BufferedReader inFile = null;
		String line;
		try {
			FileReader fr = new FileReader(Trips + ".txt");
			inFile = new BufferedReader(fr);
			inFile.readLine();
			String tempLine;
			String[] arrLine = new String[5];
			while ((line = inFile.readLine()) != null) {
				tempLine = line;
				arrLine = tempLine.split("	");
				int number = Integer.parseInt(arrLine[0]);
				int type = Integer.parseInt(arrLine[1]);
				String destination = arrLine[2];
				double pricePerTicket = Double.parseDouble(arrLine[3]);
				Trips t = new Trips(number, type, destination, pricePerTicket);
				this.Trips.add(t);
			}
		} catch (FileNotFoundException exception) {
			System.out.println("The file " + Customers + " was not found.");
		} catch (IOException exception) {
			System.out.println(exception);
		} finally {
			try {
				inFile.close();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
	}

	private void setCustomersVector(String Customers) {//read from file Customers
		BufferedReader inFile = null;
		String line;
		try {
			FileReader fr = new FileReader(Customers + ".txt");
			inFile = new BufferedReader(fr);
			inFile.readLine();
			String tempLine;
			String[] arrLine = new String[5];
			while ((line = inFile.readLine()) != null) {
				tempLine = line;
				arrLine = tempLine.split("	");
				int ID = Integer.parseInt(arrLine[0]);
				String name = arrLine[1];
				int age = Integer.parseInt(arrLine[2]);
				String gender = arrLine[3];
				int registeredBy = Integer.parseInt(arrLine[4]);
				//				Vector<Trips> Trips = new Vector<Trips>();
				Customers c = new Customers(ID, name, age, gender, registeredBy);
				this.Customers.add(c);
			}
		} catch (FileNotFoundException exception) {
			System.out.println("The file " + Customers + " was not found.");
		} catch (IOException exception) {
			System.out.println(exception);
		} finally {
			try {
				inFile.close();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
	}

	private void setTicketsSalesVector(String TicketsSales) {//read from file TicketsSales
		BufferedReader inFile = null;
		String line;
		try {
			FileReader fr = new FileReader(TicketsSales + ".txt");
			inFile = new BufferedReader(fr);
			inFile.readLine();
			String tempLine;
			String[] arrLine = new String[5];
			while ((line = inFile.readLine()) != null) {
				tempLine = line;
				arrLine = tempLine.split("	");
				int trip = Integer.parseInt(arrLine[0]);
				int soldtocustomer = Integer.parseInt(arrLine[1]);
				int numberOfTickets = Integer.parseInt(arrLine[2]);

				if (!(arrLine[3].equals(""))) {
					int soldBy = Integer.parseInt(arrLine[3]);
					TicketsSales t = new TicketsSales(trip, soldtocustomer, numberOfTickets, soldBy);
					this.TicketsSales.add(t);
				} else {
					String site = arrLine[4];
					TicketsSales t = new TicketsSales(trip, soldtocustomer, numberOfTickets, site);
					this.TicketsSales.add(t);
				}

			}
		} catch (FileNotFoundException exception) {
			System.out.println("The file " + Customers + " was not found.");
		} catch (IOException exception) {
			System.out.println(exception);
		} finally {
			try {
				inFile.close();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
	}


	//////////////////////////// methods ////////////////////////////////////


	// All set methods that can be created after the files read

	//set the vector of customers who bought a ticket for this trip
	public void setCustomersToTrip() {
		for(int i=0; i<Trips.size();i++) {
			for(int j=0; j<TicketsSales.size();j++) {
				if(TicketsSales.elementAt(j).getTrip()==Trips.elementAt(i).getNumber()) {//find the pointer at TicketsSales vector
					Customers customer = relevantCustomer(TicketsSales.elementAt(j).getSoldTo());//save the relevant customer pointer
					Trips.elementAt(i).addCustomerToTrip(customer);//add the customer to this trip vector
				}
			}
		}
	}

	//set the vector of the trips that the customer bought to the relevant customer 
	public void setTripsToCustomer() {
		for(int i=0; i<Customers.size();i++) {
			for(int j=0; j<TicketsSales.size();j++) {
				if(TicketsSales.elementAt(j).getSoldTo()==Customers.elementAt(i).getID()) {
					Trips trip = relevantTrip(TicketsSales.elementAt(j).getTrip());//find the pointer to the trip
					Customers.elementAt(i).addToTripsBought(trip);//add the trip to the vector
				}
			}
		}
	}

	//The method creates a new employee who came from a friendBringsFriend operation and updates accordingly
	private void bringFriend(int ID, String Name, int Age, String Profession, double Bonus, int RegisteredBy){	
		Employees newE =newEmployee(ID, Name, Age, Profession, Bonus, RegisteredBy);
		Employees.add(newE);//add the new employee to the employees vector
		for(int i=0; i<Employees.size();i++) {
			if(Employees.elementAt(i).getID()==RegisteredBy) {
				Employees.elementAt(i).setFriendBringFriend(newE);;//add the new employee to vector of the employee who brought him
			}
		}
	}

	//creates new employee phase that came from a friendBringsFriend
	private Employees newEmployee(int ID, String Name, int Age, String Profession, double Bonus, int RegisteredBy){
		if(Profession=="MaintenanceTeam") {
			Employees MaintenanceTeam= new MaintenanceTeam(ID,Name,Age,Profession,Bonus,RegisteredBy);
			return MaintenanceTeam;
		}
		if(Profession=="Guide") {
			Employees Guide= new Guide(ID,Name,Age,Profession,Bonus,RegisteredBy);
			return Guide;

		}
		if(Profession=="Driver") {
			Employees Driver= new Driver(ID,Name,Age,Profession,Bonus,RegisteredBy);
			return Driver;

		}
		else {
			Employees SecurityMan= new SecurityMan(ID,Name,Age,Profession,Bonus,RegisteredBy);
			return SecurityMan;
		}
	}

	//create new marketingTeam
	private Employees newMarketinTeam (int ID, String Name, int Age, String Profession, String phoneNumber) {
		if(Profession=="MarketingTeam") {
			Employees MarketingTeam= new MarketingTeam(ID,Name,Age,Profession,phoneNumber);
			return MarketingTeam;
		}
		return null;
	}


	// Methods to turn an ID to an object, either customer or trip

	//find the pointer to the relevant employee-marketing team
	public MarketingTeam relevantMarketingTeam(int employeeID) {
		MarketingTeam employee=null;
		for(int i=0; i<Employees.size();i++) 
			if(Employees.elementAt(i).getID()==employeeID) {
				if(Employees.elementAt(i) instanceof MarketingTeam) {
					employee = (MarketingTeam)Employees.elementAt(i);
					return employee;
				}
			}
		return null;
	}

	//find the pointer to the relevant customer
	public Customers relevantCustomer(int customerID) {
		Customers customer=null;
		for(int i=0; i<Customers.size();i++) 
			if(Customers.elementAt(i).getID()==customerID) {
				customer = Customers.elementAt(i);
				break;
			}
		return customer;
	}

	//find the pointer to the relevant trip
	public Trips relevantTrip(int tripNumber ) {
		Trips trip=null;
		for(int i=0; i<Trips.size();i++) 
			if(Trips.elementAt(i).getNumber()==tripNumber) {
				trip = Trips.elementAt(i);
				break;
			}
		return trip;
	}



	//method 2

	//sets the salary events of all employees
	public void setEmployeesSalaryEvents() {
		for(int i=0;i<Employees.size(); i++) {
			setSalaryEventsToEmployee(Employees.elementAt(i).getID());
		}
	}

	
	
	//method 3

	//Updates an employee's salary events
	public void setSalaryEventsToEmployee(int employeeID) {
		MarketingTeam e=relevantMarketingTeam(employeeID);
		if(e!=null) {// The employee must be a marketingTeam for the setting 
			setMarketingTeamEvents(e);
		}
	}

	//set the events for the MarketingTeam
	public void setMarketingTeamEvents(MarketingTeam m) {
		for(int j=0; j<TicketsSales.size();j++) {
			if(TicketsSales.elementAt(j).getSoldBy()==m.getID()) {//marketing man sold the ticket
				Customers customer = relevantCustomer(TicketsSales.elementAt(j).getSoldTo());//save the customer pointer
				m.getRegisteredCustomres().add(customer);//add the event-register a customer- to the vector
				m.getTicketsSold().add(TicketsSales.elementAt(j));//add the event-Tickets Sold- to the vector
			}

		}
	}


	
	//method 4

	//Prints a chosen customer's trips and sum spent
	public void printCustomerPastActivity(int customerID) {
		Customers customer= relevantCustomer(customerID); //Take the id and turn in into a customer
		System.out.println("Customer Name: " + customer.getName());
		System.out.println("Trips list:\n");
		showHistory(customer); // Calls the method from this class
		System.out.println("\nSum spent: " + customer.SumSpentPerCustomer(TicketsSales)); // Calls the method from Customers
	}

	//Prints the relevant informaion for the customer
	public void showHistory(Customers customer) {
		for(int i=0; i<customer.getTripsBought().size();i++) { //For all customers
			System.out.print(customer.getTripsBought().elementAt(i).getNumber()+ "-"); //Print trip number and ticket price
			System.out.println(customer.getTripsBought().elementAt(i).getPricePerTicket());
		}
	}


	
	//method 5

	//Prints a ranking of the customers, according to who spent more
	public void printPotentialCustomersDetails(int[] CustomersIDs) {
		Customers [] CustomersumSpentArray = CustomersumSpentArray(CustomersIDs); //Create an array of customers instead of the IDs array
		ArrayList<Customers> organizedCustomersArray = compareSumSpent(CustomersumSpentArray); //Call the method
		System.out.println("Customer’s Potential: ");
		printRankings(organizedCustomersArray); //Call the method
	}

	//Turns the int array into a Customers array
	public Customers [] CustomersumSpentArray(int[] CustomersIDs) {
		Customers[] CustomerssumSpent= new Customers[CustomersIDs.length]; 
		for(int i=0; i<CustomersIDs.length;i++) {
			for(int j=0; j<Customers.size();j++) { //Go through all the customers in the agency, for each ID get the customer
				if(CustomersIDs[i]==Customers.elementAt(j).getID()) {
					CustomerssumSpent[i]=Customers.elementAt(j);
				}
			}
		}
		return CustomerssumSpent; //Return an array of customers
	}

	//Sorts the Customers array
	public ArrayList<Customers> compareSumSpent(Customers [] customersSumSpent) {
		ArrayList<Customers> customersArray = new ArrayList<Customers>(); //The array that will be filled and returned
		customersArray.add(customersSumSpent[0]); //Add the first customer, then start the comparison 
		for(int i=1; i< customersSumSpent.length; i++) {
			for(int j=0; j<i;j++) {
				if(customersSumSpent[i].whoSpentMore(customersArray.get(j),TicketsSales)>0) { //Use ticketable to add each customer in it's place in the new array
					customersArray.add(j, customersSumSpent[i]); //Add the customer and go on to the next one
					break;
				}
			}
			if(customersArray.size()==i) //If a customer wasn't added, it should be added in the end of the array because it has the lowest rank yet
				customersArray.add(customersSumSpent[i]);
		}
		return customersArray;
	}

	//Prints out the rankings 
	public void printRankings(ArrayList<Customers> CustomersSumSpentArray) {
		for(int i=0;i<CustomersSumSpentArray.size();i++) { //Go through the sorted array and print
			System.out.println("Rank-"+(i+1)+": " + CustomersSumSpentArray.get(i).getName());
		}
	}


	
	//method 6

	//Prints a specific trips age proportion
	public void printAgeReport(String a) {
		Trips relevantTrip = relevantTrip(a); //Turn the string into a trip
		System.out.println("Destination: " + a); //Call each function that gets the percentage of it's age group
		System.out.println("0-15: "+kidsInTrip(relevantTrip)+"%");
		System.out.println("16-21: "+teensInTrip(relevantTrip)+"%");
		System.out.println("22-30: "+youngAdultsInTrip(relevantTrip)+"%");
		System.out.println("21-45: "+adultsInTrip(relevantTrip)+"%");
		System.out.println("46-85: "+olderInTrip(relevantTrip)+"%");
	}

	//Make the string into a trip, according to the name
	public Trips relevantTrip(String tripName) {
		Trips relevantTrip=null;
		for(int i=0; i<Trips.size();i++) {  //Go through all the trips and find the one with the right name
			if(tripName.equals(Trips.elementAt(i).getDestination())) {
				relevantTrip = Trips.elementAt(i);
				break;
			}
		}
		return relevantTrip;
	}

	//Get all the proportions of age groups with the coming methods
	public int kidsInTrip(Trips trip) {
		int kidsInTrip = 0;
		for(int i=0; i<trip.getCustomersInTrip().size();i++) { //Go through the trips customers and find the ones in this age group
			if(trip.getCustomersInTrip().elementAt(i).getAge()<=15)
				kidsInTrip++;
		}
		return kidsInTrip*100/trip.getCustomersInTrip().size(); //Return the percentage of age group out of the whole trip
	}

	public int teensInTrip(Trips trip) {
		int teensInTrip = 0;
		for(int i=0; i<trip.getCustomersInTrip().size();i++) { //Go through the trips customers and find the ones in this age group
			if(trip.getCustomersInTrip().elementAt(i).getAge()>15 && trip.getCustomersInTrip().elementAt(i).getAge()<=21)
				teensInTrip++;
		}
		return teensInTrip*100/trip.getCustomersInTrip().size(); //Return the percentage of age group out of the whole trip
	}

	public int youngAdultsInTrip(Trips trip) {
		int youngAdultsInTrip = 0;
		for(int i=0; i<trip.getCustomersInTrip().size();i++) { //Go through the trips customers and find the ones in this age group
			if(trip.getCustomersInTrip().elementAt(i).getAge()>21 && trip.getCustomersInTrip().elementAt(i).getAge()<=30)
				youngAdultsInTrip++;
		}
		return youngAdultsInTrip*100/trip.getCustomersInTrip().size(); //Return the percentage of age group out of the whole trip
	}

	public int adultsInTrip(Trips trip) {
		int adultsInTrip = 0;
		for(int i=0; i<trip.getCustomersInTrip().size();i++) { //Go through the trips customers and find the ones in this age group
			if(trip.getCustomersInTrip().elementAt(i).getAge()>30 && trip.getCustomersInTrip().elementAt(i).getAge()<=45)
				adultsInTrip++;
		}
		return adultsInTrip*100/trip.getCustomersInTrip().size(); //Return the percentage of age group out of the whole trip
	}

	public int olderInTrip(Trips trip) {
		int olderInTrip = 0;
		for(int i=0; i<trip.getCustomersInTrip().size();i++) { //Go through the trips customers and find the ones in this age group
			if(trip.getCustomersInTrip().elementAt(i).getAge()>45)
				olderInTrip++;
		}
		return olderInTrip*100/trip.getCustomersInTrip().size(); //Return the percentage of age group out of the whole trip
	}


	//method 7
	
	//Print the proportion of online sales out of all sales
	public double getOnlineProportion() { //Return the percentage of online sales
		int numOfOnlineTickets = 0;
		for (int i=0; i<TicketsSales.size();i++) { 
			if(!TicketsSales.elementAt(i).getSite().isEmpty()) //If there is a site, add to online tickets
				numOfOnlineTickets++;
		}
		return (double)numOfOnlineTickets/TicketsSales.size(); //Print the percentage of site sales out of all sales
	}


	
	//method 8
	
	//Print the amount of money the agency earned/lost in total
	public double getBalance() {
		double totalIncome = 0;
		double totalPaid = 0;
		for(int i=0; i<Trips.size();i++) { //For all trips, find ticket price and multiply by tickets bought
			for(int j=0; j<TicketsSales.size();j++) {
				if(Trips.elementAt(i).getNumber()==TicketsSales.elementAt(j).getTrip()) { //Match the trip and ticket with trip num
					double ticketPrice = Trips.elementAt(i).getPricePerTicket();
					int ticketsAmount = TicketsSales.elementAt(j).getNumberOfTickets();
					totalIncome += ticketPrice*ticketsAmount;
				}
			}
		}
		System.out.println(totalIncome);
		for(int j=0; j<Employees.size();j++) { //Sum up all the employees salaries 
			totalPaid += Employees.elementAt(j).getSalary();
		}
		System.out.println(totalPaid);
		return totalIncome-totalPaid;
	}


	
	//method 9
	
	//Print all factors (Employees, trips and customers) In a specific order
	public void travelAgencyReport() {
		printEmployees(); //use a method from this class that returns an organized employees vector
		System.out.println("\n");
		printTrips(); //use a method from this class that returns an organized trips vector
		System.out.println("\n");
		printCustomers(); //use a method from this class that returns an organized customers vector
	}

	//Organize the employees according to what is required
	public ArrayList<Employees> employeesOrganized() { 
		ArrayList<Employees> employeesOrganized = new ArrayList<Employees>();
		employeesOrganized.add(Employees.elementAt(0)); //Like in method 5, create a new array and put default first employee first
		for(int i=1; i<Employees.size();i++) {
			for(int j=0; j<i;j++) {
				if(Employees.elementAt(i).whoEarnsMore(employeesOrganized.get(j))>0) { //Add each employee according to salaryable, before the first employee that you find that works
					employeesOrganized.add(j, Employees.elementAt(i));
					break;
				}
			}
			if(employeesOrganized.size()==i) //If employee wasn't added until now, add it in the end of the vector
				employeesOrganized.add(Employees.elementAt(i));
		}
		return employeesOrganized;
	}

	//Print the employees according to the required demands 
	public void printEmployees() {
		ArrayList<Employees> employeesOrganized = employeesOrganized(); //Use the organizer method
		System.out.println("Travel Agency Report");
		System.out.println("Workers list: ");
		for(int i=0; i<Employees.size();i++) { //For each employee print what's needed
			//This needs to be sorted, to be continued
			System.out.print("Name: " + employeesOrganized.get(i).getName()+"; ");
			System.out.println("Age: "+ employeesOrganized.get(i).getAge());
		}
	}

	//Print the trips according to the required demands 
	public void printTrips() {
		ArrayList<Trips> tripsOrganized = tripsOrganized(); //Use the organizer of trips
		System.out.println("Trips list:");
		for(int i=0; i<Trips.size();i++) {
			System.out.println(tripsOrganized.get(i).getNumber()); //Print what is requested
		}
	}
	
	//Organize the trips according to what is required
	public ArrayList<Trips> tripsOrganized() {
		ArrayList<Trips> tripsOrganized = new ArrayList<Trips>();
		tripsOrganized.add(Trips.elementAt(0));
		for(int i=1; i<Trips.size();i++) { //Similar to the organizing of the employees, default first trip
			for(int j=0; j<i;j++) {
				if(Trips.elementAt(i).whoHasMoreTickets(tripsOrganized.get(j), TicketsSales)>0) { //Use ticketable to add the trip when bigger than the trips in the new vector
					tripsOrganized.add(j, Trips.elementAt(i));
					break;
				}
			}
			if(tripsOrganized.size()==i) //If the trip wasn't added it's the smallest, add it last
				tripsOrganized.add(Trips.elementAt(i));
		}
		return tripsOrganized;
	}

	//Print the customers according to the required demands 
	public void printCustomers() {
		ArrayList<Customers> customersOrganized = customersOrganized(); //Use the relevant organizer
		System.out.println("Customers list:");
		for(int i=0; i<Customers.size();i++) { //For each customer print what is needed
			System.out.print("Name: " + customersOrganized.get(i).getName() + "; ");
			System.out.print("Age: " + customersOrganized.get(i).getAge() + "; ");
			System.out.println("Gender: " + customersOrganized.get(i).getGender() + "; ");
		}
	}

	//Organize the customers according to what is required
	public ArrayList<Customers> customersOrganized() { 
		ArrayList<Customers> customersOrganized = new ArrayList<Customers>();
		customersOrganized.add(Customers.elementAt(0));
		for(int i=1; i<Customers.size();i++) { //Exactly like the trips organizing method
			for(int j=0; j<i;j++) {
				if(Customers.elementAt(i).whoHasMoreTickets(customersOrganized.get(j),TicketsSales)>0) { //Exactly like the trips organizing method
					customersOrganized.add(j, Customers.elementAt(i));
					break;
				}
			}
			if(customersOrganized.size()==i) //Exactly like the trips organizing method
				customersOrganized.add(Customers.elementAt(i));
		}
		return customersOrganized;
	}



	//method 10
	
	//set a new recommendation and print the 10 most recommended destinations
	public boolean recommend(int employeeID, int customerID) {
		if(checkEmployee(employeeID)&&checkCustomer(customerID)) {//check if employee and customer are exist
			int i=0;
			while(i<10) {//choose the 10 most recommended destinations
				System.out.println(destinationsOrganized().get(i).getDestinationName()+ " ");
				i++;
			}
			MarketingTeam m=relevantMarketingTeam(employeeID);
			Customers c= relevantCustomer(customerID);
			m.setRecommendation(c);//set a new recommendation at the employee vector
			return true;
		}
		return false;

	}

	// check that the employee-MarketingTeam exist
	public boolean checkEmployee(int employeeID) {
		for(int i=0; i<Employees.size();i++) {
			if(Employees.elementAt(i).getID()== employeeID)
				if(Employees.elementAt(i) instanceof MarketingTeam)
					return true;
		}
		return false;
	}

	// check that the customer exist
	public boolean checkCustomer(int customerID) {
		for(int i=0; i<Customers.size();i++) {
			if(Customers.elementAt(i).getID()== customerID)
				return true;
		}
		return false;
	}

	//create a new Destinations vector- not sort
	public Vector<Destinations> createDestinations() {
		Vector<Destinations> d = new Vector<Destinations>();
		for(int i=0; i<Trips.size(); i++) {
			Destinations newDest = new Destinations(Trips.elementAt(i).getDestination());//Destinations name
			newDest.setNumOfTickets(newDest.ticketsPerDest(Trips, TicketsSales));//set Number Of Tickets
			d.add(newDest);
		}
		d = removeDoubleDestinations(d);// Each destination appears only once
		return d;
	}

	//Removes the Destinations created twice and more
	public Vector<Destinations> removeDoubleDestinations(Vector<Destinations> destinations) {
		for(int i=0; i<destinations.size();i++) {
			String dest1 = destinations.elementAt(i).getDestinationName();
			for(int j=i+1; j<destinations.size();j++) {
				String dest2 = destinations.elementAt(j).getDestinationName();
				if(dest1.equals(dest2)) {
					destinations.remove(j);
					j--;
				}
			}
		}
		return destinations;
	}

	//Sort by the amount of tickets bought for the destination
	public ArrayList<Destinations> destinationsOrganized() {
		Vector<Destinations> d = createDestinations();
		ArrayList<Destinations> destinationsOrganized = new ArrayList<Destinations>();
		destinationsOrganized.add(d.get(0));
		for(int i=1; i<d.size();i++) {
			for(int j=0; j<i;j++) {
				if(d.elementAt(i).whoHasMoreTickets(destinationsOrganized.get(j), TicketsSales)>0) {
					//if d has more ticket than destinationsOrganized at j
					destinationsOrganized.add(j, d.elementAt(i));
					break;
				}
			}
			if(destinationsOrganized.size()==i)//if the number of ticket are equal
				destinationsOrganized.add(d.elementAt(i));
		}
		return destinationsOrganized;
	}



	/////////////////////////// main //////////////////////////////////////////
	public static void main(String[] args) {

		TravelAgency EddieTours = new TravelAgency("C:\\Users\\eddie\\eclipse-workspace\\TravelAgency\\TravelAgency\\src\\Trips","C:\\Users\\eddie\\eclipse-workspace\\TravelAgency\\TravelAgency\\src\\Employees","C:\\Users\\eddie\\eclipse-workspace\\TravelAgency\\TravelAgency\\src\\Customers","C:\\Users\\eddie\\eclipse-workspace\\TravelAgency\\TravelAgency\\src\\TicketsSales");


		//		EddieTours.printAgeReport("Porto Alegre");
		//        System.out.println();
		//        EddieTours.printCustomerPastActivity(10000);
		//        System.out.println();
		//        int []id = new int[]{10005,10001,10002};
		//        EddieTours.printPotentialCustomersDetails(id);
		//        System.out.println();
		//        System.out.println(EddieTours.getOnlineProportion());
		//        System.out.println();
		System.out.println(EddieTours.getBalance()); //Check
		//        boolean flag =EddieTours.recommend(1176,10017);
		//        EddieTours.travelAgencyReport();





















		//		System.out.println("method 4");
		//		EddieTours.printCustomerPastActivity(10000);
		//		System.out.println("\n \n \n");
		//
		//		System.out.println("method 6");
		//		EddieTours.printAgeReport("Brussels");
		//		System.out.println("\n \n \n");
		//
		//		System.out.println("method 7");
		//		System.out.println(EddieTours.getOnlineProportion());
		//		System.out.println("\n \n \n");
		//
		//		System.out.println("method 8");
		//		System.out.println(EddieTours.getBalance());
		//		System.out.println("\n \n \n");
		//
		//		System.out.println("method 9");
		//		EddieTours.travelAgencyReport();
		//		System.out.println("\n \n \n");
		//
		//		System.out.println("method 10");
		//		System.out.println(EddieTours.recommend(1175, 10000));

		//		EddieTours.travelAgencyReport();
		//		System.out.println(EddieTours.getOnlineProportion());
		//		EddieTours.getBalance();



		//		System.out.println(EddieTours.Employees.elementAt(175).getName());
		//		System.out.println(EddieTours.Employees.elementAt(175).salary());

		//		EddieTours.printCustomerPastActivity(10372);
	}

}





