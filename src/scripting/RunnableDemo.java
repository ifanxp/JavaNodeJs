package scripting;
import jdk.nashorn.api.scripting.JSObject;

class RunnableDemo implements Runnable {
	   private Thread t;
	   private String threadName;
	   private JSObject callback;
	   private Object[] args;
	   private int duration;
	   
	   RunnableDemo( String name) {
	      threadName = name;
	   }

	   RunnableDemo( String name, int _duration, JSObject _callback, Object[] _args) {
	      threadName = name;
	      duration = _duration;
	      callback = _callback;
	      args = _args;
	   }
	   
	   public void clear()
	   {
		   Thread.interrupted();
	   }

	   public void run() {
	      try {
	    	  Thread.sleep(this.duration);
	      }catch (InterruptedException e) {
	          System.out.println("Thread " +  threadName + " interrupted.");
	      }

	      if (this.callback != null && this.callback.isFunction())
	    	  this.callback.call(null, this.args);
	      else
	    	  System.out.println("Not a function");
	   }
	   
	   public void start () {
	      if (t == null) {
	         t = new Thread (this, threadName);
	         t.start ();
	      }
	   }
	}