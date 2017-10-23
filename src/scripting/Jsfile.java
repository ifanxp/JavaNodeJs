package scripting;
import javax.script.*;
import java.util.Map;
import java.util.HashMap;
import java.io.FileReader;
import java.io.BufferedReader;

public class Jsfile {
	public ScriptEngineManager factory;
	public ScriptEngine engine;
	public Map<String, Object> files;

	public Jsfile()
	{
        this.factory = new ScriptEngineManager();
        this.engine = this.factory.getEngineByName("JavaScript");
        this.engine.put("$Context", this);
        this.files = new HashMap<String, Object>();
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
		content.append("  function require(filename){ return $Context.loadJs(filename); }\n");
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

	public static void main(String[] args) throws Exception 
	{
		Jsfile js = new Jsfile();
		js.loadJs("js/main.js");
	}
}
