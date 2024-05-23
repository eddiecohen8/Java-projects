import java.util.Vector;


public class MarketingTeam extends Employees{

	private String PhoneNum;
	private Vector<Customers> registeredCustomers;
	private Vector<TicketsSales> ticketsSold;
	private Vector<Customers> recommendation;
	private int profitRegisteredCustomer;
	private int profitTicketsSold;
	private int profitRecommendation;

	
	
	//Contractor
	public MarketingTeam(int ID, String Name, int Age, String Profession, String PhoneNum) throws phoneNumNotValidException{
		this.ID = ID;
		this.Name = Name;
		this.Age = Age;
		this.Profession = Profession;
		this.BonusRate = 0;
		if (checkPhoneNum(PhoneNum)) 
			this.PhoneNum = PhoneNum;
		else {
			throw new phoneNumNotValidException("Illegal phone number");
		}
		this.registeredCustomers = new Vector<Customers>();
		this.ticketsSold = new Vector<TicketsSales>();
		this.recommendation = new Vector<Customers>();
		this.profitRecommendation=2;
		this.profitRegisteredCustomer=20;
		this.profitTicketsSold=15;
	}


	public void setFriendBringFriend(Employees e) {//does not participate at friend bring friend
	}


	public int getBasicSalary() {//does not have a basic salary
		return 0;
	}

	
	public boolean checkPhoneNum(String phoneNum) {//check if the phone number is valid
		if( phoneNum.length() < 9 || phoneNum.charAt(0) == 0 ) {
			return false;
		}
		return true;
	}

	
	public void setRecommendation(Customers c) {//add a new customer(new recommendation) to recommendation vector
		recommendation.add(c);
	}


	public String getPhoneNum() {//return phone number
		return PhoneNum;
	}

	
	public Vector<Customers> getRegisteredCustomres() {//return registeredCustomers Vector
		return registeredCustomers;
	}

	
	public Vector<TicketsSales> getTicketsSold() {//return ticketsSold Vector
		return ticketsSold;
	}

	
	public double getSalary() {//Calculate the salary 
		int totalProfitRegister =getProfitRegisteredCustomer()*registeredCustomers.size();//total profit for register customers
		int totalProfitRec=getProfitRecommendation()*recommendation.size();//total profit for recommendations for customers
		
		int totalTicketsSold=0;
		for(int i=0;i<ticketsSold.size();i++) {//sum the total ticket sales
			totalTicketsSold+=ticketsSold.elementAt(i).getNumberOfTickets();
		}
		int totalProfitTicketsSold= getProfitTicketsSold()*totalTicketsSold;//sum the profit for ticket sales

		this.salary = totalProfitRegister+totalProfitRec+totalProfitTicketsSold;//sum the total profit
		return salary;

	}
	
	
	public int getProfitRegisteredCustomer() {// return the profit for registered one customer
		return profitRegisteredCustomer;
	}

	
	public int getProfitTicketsSold() {// return the profit for sale one ticket
		return profitTicketsSold;
	}

	
	public int getProfitRecommendation() {// return the profit for one Recommendation
		return profitRecommendation;
	}



}





