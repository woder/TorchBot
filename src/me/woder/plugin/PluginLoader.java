package me.woder.plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngineManager;

import me.woder.bot.Client;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.ScriptableObject;


public class PluginLoader {
    Client c;
    public File[] plugins = null;
    public String[] cmds = null;
    public String[] descriptions = null;
    
    class JsFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return (name.endsWith(".js"));
        }
    }
    
    public PluginLoader(Client c){
        this.c = c;
    }
    
    @SuppressWarnings("unused")
    public void loadplugins(){
        List<File> list = new ArrayList<File>();
        List<String> commands = new ArrayList<String>();
        List<String> description = new ArrayList<String>();
        ScriptEngineManager mgr = new ScriptEngineManager();
        BufferedReader script = null;
        File folder = new File("plugins");
        if(folder.exists()){
        File[] files = folder.listFiles(new JsFilter());
        for (File f : files) {
         try {
            script = new BufferedReader(new FileReader("plugins/" + f.getName()));
         } catch (FileNotFoundException e1) {
            e1.printStackTrace();
         }
         Context context = Context.enter();
         try {
            ScriptableObject scope = context.initStandardObjects();
            Object wrappedBot = Context.javaToJS(c, scope);
            ScriptableObject.putProperty(scope, "c", wrappedBot);
            context.evaluateReader(scope, script, "script", 1, null);
            Function fct = (Function)scope.get("getName", scope);
            Object result = fct.call(context, scope, scope, null);      
            c.gui.addText("§3Loading " + result + "...");
            Function run = (Function)scope.get("run", scope);
            Object started = run.call(context, scope, scope, null);
            Function commandall = (Function)scope.get("getCommand", scope);
            Object commandlist = commandall.call(context, scope, scope, null);
            String[] lists = commandlist.toString().split(",");
            for(int x = 0; x < lists.length; x++){
              String[] tmp = lists[x].split(";");
              if(tmp.length > 1){
               commands.add(tmp[0]);  
               description.add(tmp[1]);
              }else{
                 c.gui.addText("§4Warning: missing command description in plugin " + result + "!" );
              }
            }
            list.add(f);
            if(!started.equals(true)){
              c.gui.addText("§4Failed to load: '" + result + "'");
            }
         } catch (Exception e) {
            //We failed to load some plugin, we don't want to crash due to this so lets do this
             c.gui.addText("§4**WARNING***");
             c.gui.addText("§4Failed to load some plugin! (" + e.getMessage() + " caused by: " + e.getCause() + ")");
            //e.printStackTrace();
         } 
         finally 
         {
            Context.exit();
         }
        }
        }else{
            c.gui.addText("§4Warning: Could not find directory \"plugins\", does it exist?"); 
        }
         
         cmds = commands.toArray(new String[commands.size()]);
         plugins = list.toArray(new File[list.size()]);
         descriptions = description.toArray(new String[description.size()]);
         /*plugin.run();
         JavaPlugin[] plugins = {plugin};
        */
        //return plugins;//result.toArray(new JavaPlugin[result.size()]);   
    }
}
