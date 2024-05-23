import java.util.Vector;

public abstract class Queue <T> {
	
    protected Vector<T> buffer; 
    protected int queueNum;
    protected boolean endOfDay=false; // When day is over this turns true
    
	protected synchronized Vector<T> getBuffer() {
		return buffer;
	}
	
	public boolean getEndOfDay() {
		return endOfDay;
	}
	
	public void setEndOfDay() {
		endOfDay=true;
	}
	
	protected synchronized void insert(T item) {} //Each queue uses this method differently

	protected synchronized int size() {
		return  buffer.size();
	}
    
	protected synchronized T extract() { 
		while (buffer.isEmpty()&&!endOfDay){ //Only if the queue is empty and day isn't over
			try{
				this.wait();
			} catch(InterruptedException e){}
		}
		if(endOfDay) // if the day is over, you "Extract" null
			return null;
		T t = buffer.elementAt(0);
		buffer.remove(t);
		return t;

	}
   
	protected int getQueueNum() {
	   return queueNum;
   }
   
	protected synchronized T extractSpecific(T item) { //Method that extracts a specific bus from queue
       while (buffer.isEmpty()){
       	try{
				this.wait();
			} catch(InterruptedException e){}
       }
    buffer.remove(item);
    return item;

}
   
   
	protected synchronized boolean isEmpty() { //Checks if our queue is empty
  	 if(buffer.isEmpty()) 
  		 return true;
  	 return false;
   }
}
