/*
 Block class writted by woder, edited to include function of former BlockInfo class by nuvasuper
 24/02/2015 dd/mm/yy 
 This was to eliminate redundancies in functionality.
 
 tool type codes include [-1,3]
-1 indicates a hand
0 is unused
1 indicates a pick
2 indicates a shovel
3 indicates an axe

tool will either indicate the minimum tool required to harvest, or a tool type code.
in the latter case, the tool type indicated isn't required but will shorten the break time

TODO: add support for sword break times

 */

package me.woder.world;

public class Block {
    private int id;
    private int x;
    private int y;
    private int z;
    private int metadata;
    private World world;
    
    private String blockName;
    private int tool;
    private int hardness;
    
    public Block(World world, int x, int y, int z, int id){
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
    }
    
    public Block(World world, int x, int y, int z, int id, int metadata){
        this(world, x, y, z, id);
        this.metadata = metadata;
    }
    
    public int getMetaData(){
        return metadata;
    }
    
    public Integer getTypeId(){
        return id;
    }
    
    public World getWorld(){
        return world;
    }
    
    public Location getLocation(){
        return new Location(world,x,y,z);
    }
    
    public Integer getX(){
        return x;
    }
    
    public Integer getY(){
        return y;
    }
    
    public Integer getZ(){
        return z;
    }
    
    public Integer getHardness() {
    	return hardness;
    }
    public String getBlockName() {
    	return blockName;
    }
    
    //used by BlockInfoManager to assign the name, tool and hardness of a block
    public void addInfo(String blockName, int tool, int hardness) {
    	this.blockName = blockName;
    	this.tool = tool;
    	this.hardness = hardness;
    }
    
    //returns the id of the minimum tool required for harvesting, or -1 if nothing specific is needed.
    public int getTool() {
       if (tool<=3) {//if the tool is only advisable, return that a hand is okay
          return -1;
       } 
       else {//otherwise, let them know the minimum tool for harvesting
          return tool;
       }
    }
   
  //returns this block's tool type code (see header)
    public int getToolType() {
       if (tool<=3) {//tool is already formatted as toolID code, it is advised, but not required
          return tool;
       } else {
          return getToolType(tool);//if a specific tool is required, we use this method to get its type.
       }
    }
    
    //returns the tool type code for the provided item id (see header)
    private int getToolType(int toolID) {
       if (toolID==270||toolID==274||toolID==257||toolID==285||toolID==278) {//pick case
          return 1;
       } 
       else if(toolID==269||toolID==273||tool==256||toolID==277||toolID==284) {//shovel case
          return 2;
       } 
       else if (toolID==271||toolID==275||toolID==258||toolID==279||toolID==286) {//axe case
          return 3;
       } 
       else if (toolID==359){//shears case, return shears
          return 359;
       } 
       else {//not a recognized tool treat as hand
          return -1;
       }
    }
    
  //returns if a given tool can be used to break this block
    public boolean isValidTool(int toolID) {
       if (tool==toolID||tool<=3) {//if the tool they have is what we need, or we don't need something specific
          return true;
       } else if (getToolType(toolID)==1&&getToolType()==1) {//they've got a pick and we need a pick
          if (getToolLevel(toolID)==12) {//gold exception
             return getToolLevel()<=6; //only works if iron or less needed
          } else {
             return getToolLevel(toolID)>=getToolLevel();//if they've got something better or equal to what we need
          }
       } else if (getToolType(toolID)==2&&getToolType()==2) {//they've got a shovel and we need a shovel
          return true;
       }//note: there are no cases where an axe is nessesary
       return false;
    }
    
    //for determining the level of tool required to break this block
    //see getToolLevel(toolId) for listing of tool levels 
    private int getToolLevel() {
       if (getToolType()<=3) {
          return 1;//hand
       } else {
          return getToolLevel(tool);
       }
    }
    
    //returns the "level" of a given item id
    //the level of a tool is directly used in calculating break time
    public int getToolLevel(int toolID) {
       if (getToolType(toolID)==-1) {
          return 1;//hand
       } else if (toolID==269||toolID==270||toolID==271) {
          return 2;//wood
       } else if (toolID==273||toolID==274||toolID==275) {
          return 4;//stone
       } else if (toolID==256||toolID==257||toolID==258) {
          return 6;//iron
       } else if (toolID==277||toolID==278||toolID==279) {
          return 8;//diamond
       } else if (toolID==284||toolID==285||toolID==286) {
          return 12;//gold
       } else {//note: shouldn't reach this case, as unrecognized tools are treated as hand.
          throw new IllegalArgumentException("tool id not recognized");
       }
    }

       //returns the time required to break a block with a given tool in miliseconds
       //mishandles jackolantern, pumpkin, melon, cobweb, leaves, vines, cocoa if using sword
       public int getBreakTime(int toolID) {
          if (hardness==-1) {//unbreakable block
             return -1;
          }
          if(isValidTool(toolID)) {//add sword complexity
             if (tool==359) {//shears exception
                if (id==35) {
                   return (int)(1000*0.24);
                } else {
                   return (int) (hardness*.1);
                }
             } else {
                return (int)(hardness*(3.0/(2.0*getToolLevel(toolID))));
             }
          } else {
             if(getToolType()==getToolType(toolID)) {
                return (int) (hardness*(5.0/getToolLevel(toolID)));
             } else {
                return (int) (hardness*(5.0));
             }
          }
       }
    
    
    public Block getRelative(int bx, int by, int bz){
        Block b = world.getBlock(x+bx, y+by, z+bz);
        System.out.println("Getting block at: " + (x+bx) + "," + (y+by) + "," + (z+bz));
        if(b == null){
            System.out.println("B is null, ending the world....");
        }
        return b;
    }

}
