import java.util.Vector;

public class Driver extends Employees{
	private int basicSalary;
	private int RegisteredBy;
	private Vector<Employees> FriendBringFriend;

	
	public Driver(int ID, String Name, int Age, String Profession, double Bonus) {//Contractor
		this.ID = ID;
		this.Name = Name;
		this.Age = Age;
		if(Bonus>0.15||Bonus<0.1) {//range of the bonus rate
			throw new EmployeeBonusRateException();
		}
		this.BonusRate = Bonus;
		this.Profession = Profession;
		this.FriendBringFriend = new Vector<Employees>();
		this.basicSalary=3500;
	}
	
	public Driver(int ID, String Name, int Age, String Profession, double Bonus, int RegisteredBy) {//Contractor to add a new employee-driver that registered by a friend
		this.ID = ID;
		this.Name = Name;
		this.Age = Age;
		if(Bonus>0.15||Bonus<0.1) {//range of the bonus rate
			throw new EmployeeBonusRateException();
		}
		this.BonusRate = Bonus;
		this.Profession = Profession;
		this.RegisteredBy=RegisteredBy;
		this.FriendBringFriend = new Vector<Employees>();
		this.basicSalary=3500;
	}
	

	public int getBasicSalary() {//return the basic salary
		return basicSalary;
	}
	
	public void setFriendBringFriend(Employees e) {//add employee to the vector friend bring friend
		this.FriendBringFriend.add(e);
	}
	
	public double getSalary() {// return the total salary
	this.salary= basicSalary+this.bonus();
	return salary;
	}
	
	public double bonus() {//sum all the bonus 
		double bonus=0;
		for(int i=0;i<FriendBringFriend.size();i++) {
			bonus= bonus+ FriendBringFriend.elementAt(i).getBasicSalary()*this.BonusRate;
		}
		return bonus;
	}
}
