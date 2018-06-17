package scripting;
import javax.script.*;

public class Jsfile {
	public ScriptEngineManager factory;
	public ScriptEngine engine;
	public JavaScriptContext context;

	public Jsfile()
	{
        this.factory = new ScriptEngineManager();
        this.engine = this.factory.getEngineByName("JavaScript");
        this.context = new JavaScriptContext(this.engine);
        this.engine.put("global", this.context);
	}

	public static void main(String[] args) throws Exception 
	{
		Jsfile js = new Jsfile();
		js.context.loadJs("js/main.js");
	}
}
