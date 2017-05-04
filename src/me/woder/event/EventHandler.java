package me.woder.event;

import java.util.ArrayList;
import java.util.List;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.ScriptableObject;

import me.woder.bot.ChatColor;
import me.woder.bot.Client;
import me.woder.plugin.Plugin;

public class EventHandler {
    Client c;
    /* Event list so far
     * onSpawnPlayer String playername, String uuid, int x, int y, int z, byte yaw, byte pitch, short currentitem
     * onSignUpdate int x, int y, int z, String line1, String line2, String line3, String line4
     * onBlockChange int x, int y, int z, int bid, int meta
     * onChatMessage String username, String message (NOTE; this event will often return "Unknown" as user, in that case the message will contain the user AND string)
     * onEntityEquipement int entityid, int slotnum, int itemid, int itemcount, int itemdamage
     * onEntityMove int eid, double x, double y, double z, boolean onGround
     * onEntityMoveLook int eid, double x, double y, double z, byte yaw, byte pitch, boolean onGround
     * onEntityTeleport int eid, double x, double y, double z
     * onHealthUpdate float health, short food, float foodsat
     * onPlayerAbilities byte flags, float flyspeed, float walkspeed
     * onPlayerPosLook double x, double y, double z, byte yaw, byte pitch, boolean onground
     * onSlotUpdate byte window, int slo, int itemid, int count, int damage
     * onTimeUpdate long age, long time
     */
    
    public EventHandler(Client c){
        this.c = c;
    }
    
    @SuppressWarnings("deprecation")
    public void handleEvent(Event event){
        List<Plugin> pul = getPluginEvents(event);    
        Context context = Context.enter();
        try {
         for(int i = 0; i < pul.toArray().length; i++){
            try {
               ScriptableObject scope = pul.get(i).scope;
               Function fct = (Function)scope.get(event.type, scope);
               fct.call(context, scope, scope, event.param);   
            } catch (SecurityException e) {
                c.gui.addText(ChatColor.DARK_AQUA + "Warning: Security error encountered while trying to pass " + event.type + " to " + pul.get(i).getName() + "\n" + e.getMessage());
            } catch (IllegalArgumentException e) {
                c.gui.addText(ChatColor.DARK_AQUA + "Warning: Wrong amount of argument error encountered while trying to pass " + event.type + " to " + pul.get(i).getName() + "\n" + e.getMessage());
            }
         }
        }finally{
            Context.exit();
        }
    }
    
    @SuppressWarnings("deprecation")
    public void handleCommand(String name, String[] args, String username){
        List<Plugin> pul = getPluginCommands(name);
        Context context = Context.enter();
        try{
         for(int i = 0; i < pul.toArray().length; i++){
            try {
                ScriptableObject scope = pul.get(i).scope;
                Function fct = (Function)scope.get(name, scope);
                fct.call(context, scope, scope, new Object[]{args, username});   
             } catch (SecurityException e) {
                 c.gui.addText(ChatColor.DARK_AQUA + "Warning: Security error encountered while trying to pass " + name + " to " + pul.get(i).getName() + "\n" + e.getMessage());
             } catch (IllegalArgumentException e) {
                 c.gui.addText(ChatColor.DARK_AQUA + "Warning: Wrong amount of argument error encountered while trying to pass " + name + " to " + pul.get(i).getName() + "\n" + e.getMessage());
             }
         }
        }finally{
            Context.exit();
        }
    }
    
    
    public List<Plugin> getPluginCommands(String command){
        List<Plugin> tmp = new ArrayList<Plugin>();
        for(int i = 0; i < c.ploader.plugins.toArray().length; i++){
            Plugin p = c.ploader.plugins.get(i);
            for(int z = 0; z < p.commands.toArray().length; z++){
                if(p.commands.get(z).equalsIgnoreCase(command)){
                    tmp.add(p);
                    break;
                }
            }
        }
        return tmp;
    }
    //this gets the plugins that actually have this event in them, to save memory usage
    public List<Plugin> getPluginEvents(Event event){
        List<Plugin> tmp = new ArrayList<Plugin>();
        for(int i = 0; i < c.ploader.plugins.toArray().length; i++){
            Plugin p = c.ploader.plugins.get(i);
            for(int z = 0; z < p.events.length; z++){
                if(p.events[z].equalsIgnoreCase(event.type)){
                    tmp.add(p);
                    break;
                }
            }
        }
        return tmp;
    }

}
