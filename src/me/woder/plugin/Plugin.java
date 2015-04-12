package me.woder.plugin;

import java.util.ArrayList;
import java.util.List;

import org.mozilla.javascript.ScriptableObject;

public class Plugin {
  public String name;
  public List<String> commands = new ArrayList<String>();
  public List<String> description = new ArrayList<String>();
  public String[] events;
  public ScriptableObject scope;
  
  public Plugin(String name, ScriptableObject scope, List<String> commands, List<String> description, String[] events){
      this.name = name;
      this.scope = scope;
      this.commands = commands;
      this.description = description;
      this.events = events;
  }
  
  public String getName(){
      return name;
  }

}
