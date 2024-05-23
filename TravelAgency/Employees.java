import java.util.Vector;

abstract public class Employees implements salaryable {
	protected int Age;
	protected String Name;
	protected int ID;
	protected double BonusRate;
	protected String Profession;
	protected double salary;
	
	public int getAge() {
		return Age;
	}
	
	public String getName() {
		return Name;
	}
	
	public int getID() {
		return ID;
	}
	
	public double getBonus() {
		return BonusRate;
	}
	
	public String getProfession() {
		return Profession;
	}
	
	abstract public void setFriendBringFriend(Employees e) ;// abstract method for son's 

	abstract public int getBasicSalary();// abstract method for son's who have a base salary
	
	abstract protected double getSalary();// abstract method for all son's

	public int whoEarnsMore(Object e) {//implements salaryable
		if(this.getSalary()>((Employees)e).getSalary())
			return 1;//this salary is bigger than the other employee
		else if(this.getSalary()<((Employees)e).getSalary())
			return -1;//the other employee salary is bigger than this salary
		else
			return 0;// both salaries are equals
		
	}

}
