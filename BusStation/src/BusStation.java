import java.util.Vector;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class BusStation {

	private Vector<Bus> busVector = new Vector<Bus>(); //Bus station has all there fields, that are relevant for different methods and workers
	private Vector<Thread> threadVector = new Vector<Thread>();
	private Vector<Employee> employeeVector = new Vector<Employee>();
	private UnboundedQueue<Bus> gate = new UnboundedQueue<Bus>(1); //These are all the queues that are in our bus station
	private UnboundedQueue<EnteringBus> logistic = new UnboundedQueue<EnteringBus>(2);
	private UnboundedQueue<EnteringBus> cleaner = new UnboundedQueue<EnteringBus>(3);
	private BoundedQueue<EnteringBus> fuel = new BoundedQueue<EnteringBus>(4,8);
	private UnboundedQueue<EnteringBus> tech = new UnboundedQueue<EnteringBus>(5);
	private UnboundedQueue<BusDetails> manager = new UnboundedQueue<BusDetails>(0);
	private int numTechnicalAttendant;
	private double cleaningTime;

	public BusStation(String BusStation,int NumTechnicalAttendant,double cleaningTime) {
		setBusVector(BusStation); //Read from file and create the bus vector
		this.numTechnicalAttendant=NumTechnicalAttendant; //Get these from gui
		this.cleaningTime=cleaningTime;
		
		starthreads();
	}


	private void setBusVector(String BusStation){//read from file Employees
		BufferedReader inFile = null;
		String line;
		try {
			FileReader fr = new FileReader(BusStation + ".txt");
			inFile = new BufferedReader(fr);
			inFile.readLine();
			String tempLine;
			String[] arrLine = new String[5];
			while ((line = inFile.readLine()) != null) {
				tempLine = line;
				arrLine = tempLine.split("	");
				String code = arrLine[0];
				int passengers = Integer.parseInt(arrLine[1]);
				int arrivalTime = Integer.parseInt(arrLine[2]);
				if (!arrLine[3].equals("")) {
					String destination = arrLine[3];
					LeavingBus bus = new LeavingBus(code,passengers,arrivalTime,destination);
					bus.setGateKeeperQueue(gate);
					Thread t = new Thread(bus);
					this.busVector.add(bus);
					this.threadVector.add(t);
				}
				else {
					int cargo = Integer.parseInt(arrLine[4]);
					EnteringBus bus = new EnteringBus(code,passengers,arrivalTime,cargo);
					bus.setGateKeeperQueue(gate);
					Thread t = new Thread(bus);
					this.busVector.add(bus);
					this.threadVector.add(t);
				}

			}

		} catch (FileNotFoundException exception) {
			System.out.println("The file " + BusStation + " was not found.");
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

	public synchronized void setEndOfDay() { //Create a fake bus and make all the queues working condition false
		EnteringBus bus = new EnteringBus(" ",0,0,0);
		logistic.setEndOfDay();
		cleaner.setEndOfDay();
		fuel.setEndOfDay();
		tech.setEndOfDay();
		logistic.insert(bus);
		cleaner.insert(bus);
		fuel.insert(bus);
		tech.insert(bus);
	}

	
 	private void createEmployees() { //Create all the relevant employees as requested
		GateKeeper g = new GateKeeper(this.gate,this.logistic,this.manager,busVector.size());
		threadVector.add(g);
		employeeVector.add(g);
		GateKeeper g2 = new GateKeeper(this.gate,this.logistic,this.manager,busVector.size());
		threadVector.add(g2);
		employeeVector.add(g2);
		g.setSecondKeeper(g2);
		g2.setSecondKeeper(g);
		LogisticsAttendant l = new LogisticsAttendant("l",30,this.logistic,this.cleaner,this.tech);
		threadVector.add(l);
		employeeVector.add(l);
		LogisticsAttendant l2 = new LogisticsAttendant("l2",50,this.logistic,this.cleaner,this.tech);
		threadVector.add(l2);
		employeeVector.add(l2);
		LogisticsAttendant l3 = new LogisticsAttendant("l3",70,this.logistic,this.cleaner,this.tech);
		threadVector.add(l3);
		employeeVector.add(l3);
		Cleaner c = new Cleaner(5,this.cleaner,this.fuel,cleaningTime,tech);
		threadVector.add(c);
		employeeVector.add(c);
		Cleaner c2 = new Cleaner(3,this.cleaner,this.fuel,cleaningTime,tech);
		threadVector.add(c2);
		employeeVector.add(c2);
		FuelTruck ft = new FuelTruck(1000);
		FuelTruck ft2 = new FuelTruck(2000);
		FuelAttendant f = new FuelAttendant("f",ft,this.fuel,this.tech,this.manager);
		threadVector.add(f);
		employeeVector.add(f);
		FuelAttendant f2 = new FuelAttendant("f2",ft2,this.fuel,this.tech,this.manager);
		threadVector.add(f2);
		employeeVector.add(f2);
		for(int i=0; i<numTechnicalAttendant; i++) { //Create technical attendants according to gui
			TechnicalAttendant t = new TechnicalAttendant("t",this.tech,this.logistic,this.cleaner,this.fuel,this.manager);
			threadVector.add(t);
			employeeVector.add(t);
		}
		Manager m = new Manager("m",this.manager,this);
		threadVector.add(m);
		employeeVector.add(m);
	}


	public Vector<Bus> getBusVector() {
		return busVector;
	}

	private void starthreads() { //Start all threads
		createEmployees();
		for(Thread t: this.threadVector) 
			t.start();
	}

	private Vector<LeavingBus> createLeavingVector() { //Create a vector of all the leaving buses
		Vector<LeavingBus> l = new Vector<LeavingBus>();
		for(Bus bus: this.getBusVector()) {
			if(bus instanceof LeavingBus) {
				l.add((LeavingBus)bus);
			}
		}
		return l;
	}

	public String mostPopularDest() { //Find the most popular destination within the leaving buses
		int currentDestCounter = 0;
		int bestDestCounter = 0;
		String mostPopularDest = "";
		Vector<LeavingBus> lVector = createLeavingVector();
		for(LeavingBus bus: lVector) { //For each bus check all the others and check how many times dest repeated
			for(LeavingBus bus2: lVector) {
				if(bus.getDestination().equals(bus2.getDestination())) {
					currentDestCounter++;
				}
			}
			if(currentDestCounter>bestDestCounter) { //If dest repeated more than the best one before it, change it
				mostPopularDest=bus.getDestination();
				bestDestCounter=currentDestCounter;
				System.out.println(bus.getDestination());
				System.out.println(currentDestCounter);
			}
			currentDestCounter=0;
		}
		return mostPopularDest;
	}
}