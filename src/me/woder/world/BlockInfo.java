/*
Clark Krusemark
22/11/2014 (dd/mm/yyyy)
nuvasuper
cdkrusemark@gmail.com

toolID codes include [-1,3]
-1 indicates a hand
0 is unused
1 indicates a pick
2 indicates a shovel
3 indicates an axe

tool will either indicate the minimum tool required to harvest, or a toolID code.
in the latter case, the tool type indicated isn't required but will shorten the break time

this class does not correctly handle sword breaking properly

note: all stone slabs are considered the same, all wooden slabs are considered the same.  need metadata to differentiate.
this also applies to varieties of stone, prismarine, red sandstone, 
sandstone, monster egg, sponge, dirt, daylight detector, flower, 

In some cases, this can alter the hardness value, and the breaking time.
This will assume the highest hardness value possible for a given blockID.
the cases in which the hardness value may be incorrect include: 
block ID: 168 Prismarine variations

*/

package me.woder.world;

public class BlockInfo {
   private String blockName;
   private int tool;//TODO: create tool type enum?
   private int hardness;//if -1, block is unbreakable
   private int id;
   
   //TODO: add meta values
   
   //if you just want one to check toolTypes or something.
   // -2 used for hardness and tool to differentiate between
   // unbreakable blocks (hardness==-1) and 
   // blocks with (toolID== -1)
   public BlockInfo() {
      this(-1,"unknown",-2,-2);
   }
   
   //constructs a BlockInfo with the indicated id, name, hardness, and tool
   //tool will be a toolID if not nessissary, or the id of the lowest required tool for harvesting
   public BlockInfo(int id, String blockName,int hardness,int tool) {
      this.blockName = blockName;
      this.tool=tool;
      this.hardness=hardness;
      this.id=id;
   }
   
   //returns the name of the block as minecraft knows it
   // will return wrong names for some blocks with metadata variations, especially flowers/plants
   public String getBlockName() {
      return blockName;
   }
   
   //returns the id of the tool required for harvesting, or -1 if a specific tool isn't needed
   public int getTool() {
      if (tool<=3) {//if the tool is only advisable, return that a hand is okay
         return -1;
      } 
      else {//otherwise, let them know the minimum tool for harvesting
         return tool;
      }
   }

   //pre: if you broke the tool id, throws IllegalStateException
   //returns the id of the most effective tool in terms of break time.
   public int getBestTool() {
      if (getToolType()==1) {//pick case, return diamond pick id
         return 278;
      } 
      else if(getToolType()==2) {//shovel case, return diamond shovel
         return 277;
      } 
      else if (getToolType()==3) {//axe case, return diamond axe
         return 279;
      } 
      else if (tool==359){//shears case, return shears
         return 359;
      } 
      else {//somehow the tool ID was changed to something wrong, or the block's tool is unknown
         throw new IllegalStateException("tool doesn't match known tool types");
      }
   }
   
   //returns this block's toolID code (see header)
   public int getToolType() {
      if (tool<=3) {//tool is already formatted as toolID code
         return tool;
      } else {
         return getToolType(tool);
      }
   }
   
   //returns the toolID code for the provided item id (see header)
   public int getToolType(int toolID) {
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
   
   //returns the hardness of this block
   public int getHardness() {
      return hardness;
   }
   
   //returns the id of this block
   public int getID() {
      return id;
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
      }//note: there are no cases where an axe is nessissary
      return false;
   }
   
   //for determining the level of tool required to break this block
   public int getToolLevel() {
      if (getToolType()<=3) {
         return 1;//hand
      } else {
         return getToolLevel(tool);
      }
   }
   
   //returns the "level" of a given item id
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
      } else {
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
               return (int) (1000*hardness*.1);
            }
         } else {
            return (int)(1000*3/(2*getToolLevel(toolID)));
         }
      } else {
         return (int) (1000*5/getToolLevel(toolID));
      }
   }
   
}