package me.woder.plugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.woder.bot.Client;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.ScriptableObject;

import com.google.common.base.Charsets;
import com.google.common.io.Files;


public class PluginLoader {
    public List<Plugin> plugins = new ArrayList<Plugin>();
    Client c;
    
    class JsFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return (name.endsWith(".js"));
        }
    }
    
    public PluginLoader(Client c){
        this.c = c;
    }
    
    public void loadPlugins(){
        List<String> commands = new ArrayList<String>();
        List<String> description = new ArrayList<String>();
        List<String> pcommands = new ArrayList<String>();
        List<String> pdescription = new ArrayList<String>();
        String script = null;
        File folder = new File("plugins");
       if(folder.exists()){
        File[] filelist = folder.listFiles(new JsFilter());
        for (File f : filelist) {
         pcommands = new ArrayList<String>();
         pdescription = new ArrayList<String>();
         try {
            script = Files.toString(new File("plugins/" + f.getName()), Charsets.UTF_8);
         } catch (FileNotFoundException e1) {
            e1.printStackTrace();
         } catch (IOException e) {
            e.printStackTrace();
         }
         Context context = Context.enter();
         try {
            ScriptableObject scope = context.initStandardObjects();
            Object wrappedBot = Context.javaToJS(c, scope);
            ScriptableObject.putProperty(scope, "c", wrappedBot);
            context.evaluateString(scope, script, "script", 1, null);
            Function fct = (Function)scope.get("getName", scope);
            Object result = fct.call(context, scope, scope, null);      
            c.gui.addText("§3Loading '" + result + "'...");
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
               pcommands.add(tmp[0]);
               pdescription.add(tmp[1]);
              }else{
                 c.gui.addText("§4Warning: missing command description in plugin " + result + "!" );
              }
            }
            Function getevent = (Function)scope.get("getListener", scope);
            Object eventlist = getevent.call(context, scope, scope, null);
            System.out.println("event is: " + eventlist.toString());
            c.gui.addText("§3Plugin '" + result + "' registered events: " + eventlist);
            String[] events = eventlist.toString().split(",");
            plugins.add(new Plugin(result.toString(), script, pcommands, pdescription, events));
            if(!started.equals(true)){
              c.gui.addText("§4Failed to load: '" + result + "'");
            }
         }catch (Exception e){
            //We failed to load some plugin, we don't want to crash due to this so lets do this
             c.gui.addText("§4**WARNING***");
             c.gui.addText("§4Failed to load some plugin! (" + e.getMessage() + " caused by: " + e.getCause() + ")");
             e.printStackTrace();
         }finally{
            Context.exit();
         }
        }
       }else{
            c.gui.addText("§4Warning: Could not find directory \"plugins\", does it exist?"); 
       }
         
         c.cmds = commands.toArray(new String[commands.size()]);
         c.descriptions = description.toArray(new String[description.size()]); 
    }
    
    public void reloadPlugins(){
       plugins = new ArrayList<Plugin>();
       loadPlugins();
    }
}
