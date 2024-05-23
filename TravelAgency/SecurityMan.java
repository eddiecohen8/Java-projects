import java.util.Vector;

public class SecurityMan extends Employees {
	private int basicSalary;
	private int RegisteredBy;
	private Vector<Employees> FriendBringFriend;

	//Contractor
	public SecurityMan(int ID, String Name, int Age, String Profession, double Bonus) {
		this.ID = ID;
		this.Name = Name;
		this.Age = Age;
		this.Profession = Profession;
		if(Bonus>0.2||Bonus<0.15) {//range of the bonus rate
			throw new EmployeeBonusRateException();
		}
		this.BonusRate = Bonus;
		this.FriendBringFriend = new Vector<Employees>();
		this.basicSalary=5500;
	}
	
	//Contractor for guide who register by a friend
	public SecurityMan(int ID, String Name, int Age, String Profession, double Bonus, int RegisteredBy) {
		this.ID = ID;
		this.Name = Name;
		this.Age = Age;
		this.Profession = Profession;
		if(Bonus>0.2||Bonus<0.15) {//range of the bonus rate
			throw new EmployeeBonusRateException();
		}
		this.BonusRate = Bonus;
		this.RegisteredBy=RegisteredBy;
		this.FriendBringFriend = new Vector<Employees>();
		this.basicSalary=5500;

	}
	public void setFriendBringFriend(Employees e) {
		this.FriendBringFriend.add(e);
	}
	
	public int getBasicSalary() {// return the basic Salary
		return basicSalary;
	}

	
	public double getSalary() {// return the total salary
	this.salary= basicSalary+this.bonus();
	return salary;
	}
	
	public double bonus() {//sum the total bonus
		double bonus=0;
		for(int i=0;i<FriendBringFriend.size();i++) {//bonus for every employee that this guide brought
			bonus= bonus+ FriendBringFriend.elementAt(i).getBasicSalary()*this.BonusRate;
		}
		return bonus;
	}
}

