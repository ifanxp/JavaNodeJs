package scripting;
import jdk.nashorn.api.scripting.JSObject;

class Timer implements Runnable {
	   private Thread t;
	   private String threadName;
	   private JSObject callback;
	   private Object[] args;
	   private int duration;
	   private boolean forever;
	   
	   Timer( String name) {
	      threadName = name;
	   }

	   Timer( String name, int _duration, JSObject _callback, Object[] _args) {
	      threadName = name;
	      duration = _duration;
	      callback = _callback;
	      args = _args;
	   }

	   Timer( String name, int _duration, JSObject _callback, Object[] _args, boolean _forever) {
	      threadName = name;
	      duration = _duration;
	      callback = _callback;
	      args = _args;
	      forever = _forever;
	   }
	   
	   public void run() {
		   while (true)
		   {
		      try {
		    	  Thread.sleep(this.duration);
		      }catch (InterruptedException e) {
		          System.out.println("Thread " +  threadName + " interrupted.");
		          return;
		      }
	
		      if (this.callback != null && this.callback.isFunction())
		    	  this.callback.call(null, this.args);
		      else
		    	  System.out.println("Not a function");
		      
		      if (!forever) break;
		   }
	   }
	   
	   public void start () {
	      if (t == null) {
	         t = new Thread (this, threadName);
	         t.start ();
	      }
	   }
	}