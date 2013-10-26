package me.woder.event;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.ScriptableObject;

import me.woder.bot.Client;

public class EventHandler {
    Client c;
    
    public EventHandler(Client c){
        this.c = c;
    }
    public void eventHandler(Event event, Client c){
        for(int i = 0; i < c.plugins.length; i++){
               File plugin = c.plugins[i];
               BufferedReader script = null;
               Object result = null;
               ScriptableObject scope = null;
               Context context = null;
               try{
               script = new BufferedReader(new FileReader("plugins/" + plugin.getName()));
               context = Context.enter();
               scope = context.initStandardObjects();
               Object wrappedBot = Context.javaToJS(c, scope);
               ScriptableObject.putProperty(scope, "botmain", wrappedBot);
               context.evaluateReader(scope, script, "script", 1, null);
               Function fct = (Function)scope.get("getListners", scope);
               result = fct.call(context, scope, scope, null); 
               }catch(Exception e){
                   c.gui.addText("§3**WARNING**");
                   c.gui.addText("§3Failed to load " + plugin.getName());
                   return;
               }
               String[] temp = result.toString().split(",");
                for(int z = 0; z < temp.length; z++){
                  if(event.type.equalsIgnoreCase(temp[z])){
                   try {
                       Function called = (Function)scope.get(event.type, scope);
                      if(event.type.equalsIgnoreCase("onMessage")){
                       result = called.call(context, scope, scope, new Object[] {event.param[0], event.param[1], event.param[2], event.param[3], event.param[4]}); 
                      }else if(event.type.equalsIgnoreCase("onWhois")){
                       result = called.call(context, scope, scope, new Object[] {event.param[0], event.param[1]});  
                      }else if(event.type.equalsIgnoreCase("onPrivateMessage")){
                       result = called.call(context, scope, scope, new Object[] {event.param[0], event.param[1], event.param[2], event.param[3]});   
                      }else if(event.type.equalsIgnoreCase("onNotice")){
                       result = called.call(context, scope, scope, new Object[] {event.param[0], event.param[1], event.param[2], event.param[3], event.param[4]});   
                      }else if(event.type.equalsIgnoreCase("onJoin")){
                       result = called.call(context, scope, scope, new Object[] {event.param[0], event.param[1], event.param[2], event.param[3]});   
                      }else if(event.type.equalsIgnoreCase("onPart")){
                       result = called.call(context, scope, scope, new Object[] {event.param[0], event.param[1], event.param[2], event.param[3], event.param[4]});   
                      }else if(event.type.equalsIgnoreCase("onConnect")){
                       result = called.call(context, scope, scope, new Object[] {event.param[0]});   
                      }else if(event.type.equalsIgnoreCase("onUnknown")){
                       result = called.call(context, scope, scope, new Object[] {event.param[0]});   
                      }
                   } catch (SecurityException e) {
                       c.gui.addText("§3Warning: Security error encountered while trying to pass " + event.type + " to " + plugin.getName() + "\n" + e.getMessage());
                   } catch (IllegalArgumentException e) {
                       c.gui.addText("§3Warning: Wrong amount of argument error encountered while trying to pass " + event.type + " to " + plugin.getName() + "\n" + e.getMessage());
                   }
                  }
                }
        }
    }

}
