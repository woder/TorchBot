package me.woder.plugin;

import java.util.ArrayList;
import java.util.List;

public class Plugin {
  public String name;
  public String content;
  public List<String> commands = new ArrayList<String>();
  public List<String> description = new ArrayList<String>();
  public String[] events;
  
  public Plugin(String name, String content, List<String> commands, List<String> description, String[] events){
      this.name = name;
      this.content = content;
      this.commands = commands;
      this.description = description;
      this.events = events;
  }
  
  public String getName(){
      return name;
  }

}
