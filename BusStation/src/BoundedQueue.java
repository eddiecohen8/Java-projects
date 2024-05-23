import java.util.Vector;
public class BoundedQueue<T> extends Queue<T>{
	
	private int maxSize;//maximum size of the queue
	
	public BoundedQueue(int queueNum,int maxSize){//constructor
	    buffer = new Vector<T>();
	    this.queueNum=queueNum;
		this.maxSize=maxSize;

	}

	
	protected synchronized void insert(T item) {//if the queue if full-wait
		while(buffer.size()>=maxSize)
			try{
				this.wait();
			} catch(InterruptedException e){}

		buffer.add(item);
		this.notifyAll(); 
	}
}