package me.woder.bot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import me.woder.network.Packet;
import me.woder.world.Block;
import me.woder.world.Location;

import com.adamki11s.pathing.AStar;
import com.adamki11s.pathing.AStar.InvalidPathException;
import com.adamki11s.pathing.PathingResult;
import com.adamki11s.pathing.Tile;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;


public class MovementHandler {
    private Client c;
    Tile next;
    
    public MovementHandler(Client c){
        this.c = c;
    }

    public void runPathing(final Location start, final Location end, final int range){
        try {
            //create our pathfinder
            AStar path = new AStar(start, end, range);
            //get the list of nodes to walk to as a Tile object
            ArrayList<Tile> route = path.iterate();
            //get the result of the path trace
            PathingResult result = path.getPathingResult();
     
            switch(result){
            case SUCCESS : 
                //Path was successful. Do something here.
                moveAlong(start, route);
                c.chat.sendMessage("Path was found! :D");
                break;
            case NO_PATH :
                //No path found, throw error.
                System.out.println("No path found!");
                c.chat.sendMessage("No path was found :(");
                break;
             default:
                c.chat.sendMessage("Well... appearently we didn't find a path and we did... at the same time");
                break;
            }
        } catch (InvalidPathException e) {
            //InvalidPathException will be thrown if start or end block is air
            if(e.isEndNotSolid()){
                System.out.println("End block is not walkable");
                c.chat.sendMessage("Unable to walk on the block you are standing on.");
            }
            if(e.isStartNotSolid()){
                System.out.println("Start block is not walkable");
            }
        }
    }
     
    private void moveAlong(final Location start, ArrayList<Tile> tiles){
        Timer timer = new Timer();
        final Iterator<Tile> itr = tiles.iterator();
        TimerTask task = new TimerTask() {
            public void run() {           
             if(itr.hasNext()){
              if(next != null){
               Location loc = next.getLocation(start);
               next = itr.next();
               Location locs = next.getLocation(start);
               double newYaw = 0, newPitch = 0;
               double xDiff = locs.getX() - c.location.getX();
			   double yDiff = locs.getY() - (c.location.getY() - 1);
			   double zDiff = locs.getZ() - c.location.getZ();
			   double DistanceXZ = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
			   double DistanceY = Math.sqrt(DistanceXZ * DistanceXZ + yDiff * yDiff);
			   newYaw = Math.acos(xDiff / DistanceXZ) * 180 / Math.PI;
			   newPitch = Math.acos(yDiff / DistanceY) * 180 / Math.PI - 90;
			   if(zDiff < 0.0) {
				 newYaw = newYaw + Math.abs(180 - newYaw) * 2;
			   }	
			   c.yaw = (float) newYaw;
			   c.pitch = (float) newPitch;
			   if(!itr.hasNext()){
				   next = null;
			   }
               calcMovement(new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));       
              }else{
               Tile t = itr.next();
               next = itr.next();
               Location locs = next.getLocation(start);
               double newYaw = 0, newPitch = 0;
               double xDiff = locs.getX() - c.location.getX();
			   double yDiff = locs.getY() - (c.location.getY() - 1);
			   double zDiff = locs.getZ() - c.location.getZ();
			   double DistanceXZ = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
			   double DistanceY = Math.sqrt(DistanceXZ * DistanceXZ + yDiff * yDiff);
			   newYaw = Math.acos(xDiff / DistanceXZ) * 180 / Math.PI;
			   newPitch = Math.acos(yDiff / DistanceY) * 180 / Math.PI - 90;
			   if(zDiff < 0.0) {
				 newYaw = newYaw + Math.abs(180 - newYaw) * 2;
			   }	
               Location loc = t.getLocation(start);
               calcMovement(new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
              }
             }else{
              this.cancel();
             }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 150);
    }
    
    /**
     * Causes the bot to attempt to go to a player's current location
     * has a maximum range of 100 units
     *
     * @param  name the name of the player you want to go to
     * @see goToLocation
     */
    public void goToPlayer(String name){
        Player p = c.en.findPlayer(name);
        if(p != null){
          Location l = new Location(c.whandle.getWorld(),c.location.getX(), c.location.getY()-1, c.location.getZ());
          Location loc = new Location(c.whandle.getWorld(), p.getLocation().getX(), p.getLocation().getY()-1, p.getLocation().getZ());
          c.move.runPathing(l, loc, 100);
        }
    }
    
    /**
     * Causes the bot to attempt to go to the specified location
     * has a maximum range of 100 units
     *
     * @param x the x coord
     * @param y the y coord
     * @param z the z coord
     * @see goToPlayer
     */
    public void goToLocation(int x, int y, int z){
        Location l = new Location(c.whandle.getWorld(),c.location.getX(), c.location.getY()-1, c.location.getZ());
        Location loc = new Location(c.whandle.getWorld(), x, y-1, z);
        c.move.runPathing(l, loc, 100);
    }  
    
    public void applyGravity(){
        Block block = c.whandle.getWorld().getBlock(c.location).getRelative(0, -2, 0);
        int id = block.getTypeId();
        if(canBlockBeWalkedThrough(id)){
            //c.gui.addText("Data: " + c.location.getX() + ", " + Math.floor(c.location.getY()-2) + ", " + c.location.getZ());
            c.location.setY(Math.floor(c.location.getY()-2));  
            //c.gui.addText("Updated " + c.location.getY());
        }else{
            if(block.getY() != c.location.getY()){
               c.gui.addText("Location set to: " + c.location.getY() + " because " + c.location.getY() + " and " + block.getY());
               c.location.setY(Math.floor(c.location.getY()));
            }else{
               c.gui.addText("Thats a lie..."); 
            }
        }
    }
    
    public Location getCenter(double x, double y, double z){
        double rx;
        double rz;
        rx = Math.floor(x)+0.5;
        rz = Math.floor(z)+0.5;
        System.out.println("Center is: " + rx + " " + rz);
        return new Location(c.whandle.getWorld(),rx,y,rz);
    }
    
    /**Take the delta: deltax deltay deltaz
    Divide the delta by the amount of smaller steps you want to make
    Then apply the divided difference to the current position in increments and  most importantly when we are done we will move to the TARGET POSITION USING NO DELTAS


    Note that the divided increments can be changed to allow for speed modifications, or the entire loop can be slowed, 
    this is a question of preference although in order to standardize the walking speed it should probably be done via a 
    timed loop to prevent slower PCs running slower than faster ones (it will mess up the timing on the for loop)**/
    
    public boolean calcMovement(Location l){
        boolean canGo = false;
        //checks that the place we are going to is safe
        if(canBlockBeWalkedThrough(l.getBlock().getRelative(0, 1, 0).getTypeId())){//start by checking if we can go there
            //now check if the head is safe
            if(canBlockBeWalkedThrough(l.getBlock().getRelative(0, 2, 0).getTypeId())){
                //yay it seems clear, so now we can go there
            	int steps = 10; //the amount of steps to take
            	double deltax = (l.getX() - c.location.getX())/steps;
            	double deltay = (l.getY()+1 - c.location.getY())/(steps*0.5);
            	double deltaz = (l.getZ() - c.location.getZ())/steps;
            	while(--steps > 0){
            		c.location.setX(c.location.getX() + deltax);
            		c.location.setY(c.location.getY() + deltay);
            		c.location.setZ(c.location.getZ() + deltaz);
            		tick();
            	}
            	c.location.setX(l.getX());
            	c.chat.sendMessage("");
        		c.location.setY(l.getY()+1);// HAHAHAHAHAH WOW
        		c.location.setZ(l.getZ());
        		tick();
                //c.chat.sendMessage("Location: " + l.getBlockX() + ", " + (l.getBlockY()+1) + "," + l.getBlockZ() + " " + c.location.getX() + ", " + c.location.getY() + ", " + c.location.getZ());
                canGo = true;
            }
        }//if we can't go there then we will return false
        return canGo;
    }
    
    private boolean canBlockBeWalkedThrough(int id) {
        return (id == 0 || id == 6 || id == 50 || id == 63 || id == 30 || id == 31 || id == 32 || id == 37 || id == 38 || id == 39 || id == 40 || id == 55 || id == 66 || id == 75
                || id == 76 || id == 78);
    }
    
   public void tick(){
      move(c.location.getX(), c.location.getY(), c.location.getZ());
   }

    public void move(double x, double y, double z) {
        ByteArrayDataOutput buf = ByteStreams.newDataOutput();
        try{
         Packet.writeVarInt(buf, 6);
         buf.writeDouble(x);
         buf.writeDouble(y);
         buf.writeDouble(z);
         buf.writeFloat(c.yaw);
         buf.writeFloat(c.pitch);
         buf.writeBoolean(c.onground);
         c.net.sendPacket(buf, c.out);
        }catch(IOException e){
          e.printStackTrace();
        }
    }
    
    public void sendOnGround(){
        ByteArrayDataOutput buf = ByteStreams.newDataOutput();
        try{
         Packet.writeVarInt(buf, 3);
         buf.writeBoolean(c.onground);
         c.net.sendPacket(buf, c.out);
        }catch(IOException e){
          e.printStackTrace();
        }
    }
    
    public double getDeci(double num){
        double iPart;
        double fPart;
        iPart = (long) num;
        fPart = num - iPart;
        return fPart;
    }
}
