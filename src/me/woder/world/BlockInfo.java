/*
Clark Krusemark
22/11/2014 (dd/mm/yyyy)
nuvasuper
cdkrusemark@gmail.com

This is the BlockInfo class.  It is used to return important information about a certain kind of block.
It can return the name, id, tool advised/required (see toolID section), hardness of a block, and break time when using a specified tool.
It can also be used without referencing a type of block to determine the type of a specified tool, or the "level" of a specified tool

toolID codes include [-1,3]
-1 indicates a hand
0 is unused
1 indicates a pick
2 indicates a shovel
3 indicates an axe

tool will either indicate the minimum tool required to harvest, or a toolID code.
in the latter case, the tool type indicated isn't required but will shorten the break time

this class does not correctly handle sword breaking properly

note: use setMetadata(metadata, true) to update the blockName to a more specific string, but abandon minecraft's naming convention.

Methods you should be using:
getBlockName()
getHardness()
getID()
getBreakTime(int toolId) returns the time in miliseconds that the given tool will take to break this block
getTool() returns the *required* tool
getBestTool() returns the *quickest* tool, note: currently returns relavant diamond tool, should gold be returned?
isValidTool(int toolId)  returns if the given tool item id can be used to harvest this block

getToolType() returns the kind of tool helps break this block
getToolType(int toolId) returns the kind of tool that this tool item id is

*/

package me.woder.world;

public class BlockInfo {
   private String blockName;
   private int tool;//TODO: create tool type enum?
   private int hardness;//if -1, block is unbreakable
   private int id;
   private int metadata;
   
   //TODO: add meta values
   
   //if you just want one to check toolTypes or something.
   // -2 used for hardness and tool to differentiate between
   // unbreakable blocks (hardness==-1) and 
   // blocks with (toolID== -1) ie: hand advised
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
   // will return general names for some blocks with metadata variations, especially flowers/plants, logs, slabs, planks, etc
   public String getBlockName() {
      return blockName;
   }
   
   public void setMetadata(int metadata, boolean force) {
      this.metadata= metadata;
      if (id==168&&metadata==1) {//important prismarine brick case 
         hardness=1200;
      }
      if (force) {
         if(id==1) {//following commented cases do not use minecraft's naming convention, but provide more specific information
            if(metadata==0) {
               blockName="stone";
            }else if (metadata==1) {
               blockName="granite";
            }else if (metadata==2) {
               blockName="smooth_granite";
            }else if (metadata==3) {
               blockName="diorite";
            }else if (metadata==4) {
               blockName="smooth_diorite";
            }else if (metadata==5) {
               blockName="andesite";
            }else if (metadata==6) {
               blockName="smooth_andesite";
            }
         } else if(id==18) {
            if(metadata%4==0) {
               blockName="oak_leaves";
            } else if (metadata%4==1) {
               blockName="spruce_leaves";
            } else if (metadata%4==2) {
               blockName="birch_leaves";
            } else if (metadata%4==3) {
               blockName="jungle_leaves";
            }
            if (metadata/4==1) {
               blockName=blockName+"_no_decay";
            } else if (metadata/4==2) {
               blockName=blockName+"_check_decay";
            } else if (metadata/4==3) {
               blockName=blockName+"_no_decay_and_check_decay";
            }
         } else if (id==161) {
            if (metadata%2==0) {
               blockName="acacia_leaves";
            } else if (metadata%2==1) {
               blockName="dark_oak_leaves";
            }
            if (metadata/2==1) {
               blockName=blockName+"_no_decay";
            } else if (metadata/2==2) {
               blockName=blockName+"_check_decay";
            } else if (metadata/2==4) {
               blockName=blockName+"_no_decay_check_decay";
            }
         } else if (id==171||id==35) {
            if (metadata==0) {
               blockName="white_carpet";
            } else if (metadata==1) {
               blockName="orange_carpet";
            } else if (metadata==2) {
               blockName="magenta_carpet";
            } else if (metadata==3) {
               blockName="light_blue_carpet";
            } else if (metadata==4) {
               blockName="yellow_carpet";
            } else if (metadata==5) {
               blockName="lime_carpet";
            } else if (metadata==6) {
               blockName="pink_carpet";
            } else if (metadata==7) {
               blockName="gray_carpet";
            } else if (metadata==8) {
               blockName="light_gray_carpet";
            } else if (metadata==9) {
               blockName="cyan_carpet";
            } else if (metadata==10) {
               blockName="purple_carpet";
            } else if (metadata==11) {
               blockName="blue_carpet";
            } else if (metadata==12) {
               blockName="brown_carpet";
            } else if (metadata==13) {
               blockName="green_carpet";
            } else if (metadata==14) {
               blockName="red_carpet";
            } else if (metadata==15) {
               blockName="black_carpet";
            }
            if(id==35) {
               blockName=blockName+"_wool";
            } else {
               blockName=blockName+"_carpet";
            }
         } else if (id==43) {
            if (metadata==0) {
               blockName="double_stone_slab";
            } else if (metadata==1) {
               blockName="double_sandstone_slab";
            } else if (metadata==2) {
               blockName="double_stone_wooden_slab";
            } else if (metadata==3) {
               blockName="double_cobblestone_slab";
            } else if (metadata==4) {
               blockName="double_bricks_slab";
            } else if (metadata==5) {
               blockName="double_stone_brick_slab";
            } else if (metadata==6) {
               blockName="double_nether_brick_slab";
            } else if (metadata==7) {
               blockName="double_quartz_slab";
            } else if (metadata==8) {
               blockName="full_stone_slab";
            } else if (metadata==9) {
               blockName="full_sandstone_slab";
            } else if (metadata==10) {
               blockName="tile_quartz_slab";
            }
         } else if (id==181) {
            if (metadata==0) {
               blockName="double_red_sandstone_slab";
            } else {
               blockName="full_red_sandstone_slab";
            }
         } else if (id==44) {
            if (metadata==0) {
               blockName="stone_slab";
            } else if (metadata==1) {
               blockName="sandstone_slab";
            } else if (metadata==2) {
               blockName="stone_wooden_slab";
            } else if (metadata==3) {
               blockName="cobblestone_slab";
            } else if (metadata==4) {
               blockName="bricks_slab";
            } else if (metadata==5) {
               blockName="stone_brick_slab";
            } else if (metadata==6) {
               blockName="nether_brick_slab";
            } else if (metadata==7) {
               blockName="quartz_slab";
            } else if (metadata>=8) {
               blockName="upper_"+blockName;
            }
         } else if (id==182) {
            if (metadata==0) {
               blockName="red_sandstone_slab";
            } else {
               blockName="upper_red_sandstone_slab";
            }
         } else if (id==125) {
            if (metadata==0) {
               blockName="double_oak_wood_slab";
            } else if (metadata==1) {
               blockName="double_spruce_wood_slab";
            } else if (metadata==2) {
               blockName="double_birch_wood_slab";
            } else if (metadata==3) {
               blockName="double_jungle_wood_slab";
            } else if (metadata==4) {
               blockName="double_acacia_wood_slab";
            } else if (metadata==5) {
               blockName="double_dark_oak_wood_slab";
            }
         } else if (id==126) {
            if (metadata==0) {
               blockName="oak_wood_slab";
            } else if (metadata==1) {
               blockName="spruce_wood_slab";
            } else if (metadata==2) {
               blockName="birch_wood_slab";
            } else if (metadata==3) {
               blockName="jungle_wood_slab";
            } else if (metadata==4) {
               blockName="acacia_wood_slab";
            } else if (metadata==5) {
               blockName="dark_oak_wood_slab";
            } else if (metadata>=6) {
               blockName="upper_"+blockName;
            }
         } else if (id==98) {
            if (metadata==0) {
               blockName="stone_brick";
            } else if (metadata==1) {
               blockName="mossy_stone_brick";
            } else if (metadata==2) {
               blockName="cracked_stone_brick";
            } else if (metadata==3) {
               blockName="chiseled_stone_brick";
            }
         } else if (id==168) {
            if (metadata==0) {
               blockName="prismarine";
            } else if (metadata==1) {
               blockName="prismarine_bricks";
               hardness=1200;
            } else if (metadata==2) {
               blockName="dark_prismarine";
            }
         } else if (id==19) {
            if (metadata==1) {
               blockName="wet_sponge";
            }
         } else if (id==139) {
            if (metadata==1) {
               blockName="mossy_cobblestone_wall";
            }
         } else if(id==5) {
            if (metadata==0) {
               blockName="oak_wood_planks";
            } else if (metadata==1) {
               blockName="spruce_wood_planks";
            } else if (metadata==2) {
               blockName="birch_wood_planks";
            } else if (metadata==3) {
               blockName="jungle_wood_planks";
            } else if (metadata==4) {
               blockName="acacia_wood_planks";
            } else if (metadata==5) {
               blockName="dark_oak_planks";
            }
         } else if (id==3) {
            if (metadata==1) {
               blockName="coarse_dirt";
            } else if (metadata==1) {
               blockName="podzol";
            }
         }//remaining cases: logs, sandstone, red sandstone, non-solid grass, flower, monster egg, quartz, anvil
         
      }
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
   //TODO: shouldn't this be returning gold tools?  with the obsidian exception?
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
      if (tool<=3) {//tool is already formatted as toolID code, it is advised, but not required
         return tool;
      } else {
         return getToolType(tool);//if a specific tool is required, we use this method to get its type.
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
   
}