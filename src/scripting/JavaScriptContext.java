package scripting;
import jdk.nashorn.api.scripting.JSObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;

public class JavaScriptContext {
	public List<Thread> Timers = new ArrayList<Thread>();
	public Map<String, Object> files = new HashMap<String, Object>();
	public ScriptEngine engine;
	
	public JavaScriptContext(ScriptEngine _engine)
	{
		this.engine = _engine;
	}

	//load js file
	public Object loadJs(String filename) throws Exception
	{
		if (this.files.containsKey(filename)) {
			return this.files.get(filename);
		}

		Object jsObject = this.engine.eval(_wrapJsContent(filename));
		this.files.put(filename, jsObject);
		return jsObject;
	}
	
	private String _wrapJsContent(String filename) throws Exception
	{
		StringBuffer content = new StringBuffer();
		content.append("(function() {\n");
		content.append("  function require(filename){ return global.loadJs(filename); }\n");
		content.append("  var module = {file: '" + filename + "', exports: {}};");
		content.append("  (function(exports, module, require) {\n");
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line;
		while ((line = br.readLine()) != null) {
			content.append(line);
			content.append('\n');
		}
		br.close();
		content.append("  })(module.exports, module, require);\n");
		content.append("  return module.exports;\n");
		content.append("})();\n");
		return content.toString();
	}

	public void printType(Object object)
	{
		System.out.println(object.getClass().getSimpleName());
	}
	
	public int setTimeOut(int duration, JSObject callback, Object... args)
	{
		Thread t1 = new Thread(new RunnableDemo( "Thread-" + Timers.size(), duration, callback, args));
		Timers.add(t1);
		t1.start();
		return Timers.size();
	}
	
	public void clearTimeOut(int id)
	{
		Timers.get(id - 1).interrupt();
	}

	public int setInterval(int duration, JSObject callback, Object... args)
	{
		Thread t1 = new Thread(new RunnableDemo( "Thread-" + Timers.size(), duration, callback, args, true));
		t1.start();
		Timers.add(t1);
		return Timers.size();
	}
	
	public void clearInterval(int id)
	{
		Timers.get(id - 1).interrupt();
	}
	
}
