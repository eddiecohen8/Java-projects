import java.util.Vector;

public class UnboundedQueue<T> extends Queue<T> {
	
	public UnboundedQueue(int queueNum) { //Constructor
		buffer = new Vector<T>();
		this.queueNum=queueNum;
	}
	
	protected synchronized void insert(T item) { //When inserting, wake everyone up
		buffer.add(item);
		this.notifyAll(); 
	}
}

