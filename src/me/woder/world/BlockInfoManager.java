package me.woder.world;
import java.util.*;
import java.io.*;
import java.nio.file.Files;
/*
Clark Krusemark
24/02/2015 (dd/mm/yyyy)
nuvasuper
cdkrusemark@gmail.com

this is the BlockInfoManager class.
It reads in a plaintext file with all of the relevant block information(id, name, hardness, advised/required tool)

tool is either the tool's item id, or a number [-2,3]
-2 indicates we don't know what this block is
-1 indicates no tool is nessessary, or the block is unbreakable, check hardness to verify.
0 is unused
1 indicates a pick assists, but is unnessissary
2 indicates a shovel assists, but is unnessissary
3 indicates a axe assists, but is unnessissary

hardness is a value from 50-0, or -1 if unbreakable, -2 if we don't know what this block is.

The method you should be using is getInfo(id) to return a BlockInfo object.

*/
public class BlockInfoManager{
   private Map<Integer,String> BLOCK_NAME;
   private Map<Integer,Integer> HARDNESS;
   private Map<Integer,Integer> TOOL;
   //TODO: add metavalue support
   //private Map<Integer,Map<Integer,String>> metaMap;
   //private Map<Integer String> metavalueMap;
   private File f = new File("BlockInfo.txt");
   
   public BlockInfoManager() throws IOException {
   
      BLOCK_NAME = new HashMap<Integer,String>();
      HARDNESS = new HashMap<Integer,Integer>();
      TOOL = new HashMap<Integer,Integer>();
      if (!f.exists()) {
    	 System.out.println("Working Directory = " + System.getProperty("user.dir"));
    	 File copy = new File("BlockInfoCopy.txt");
         if(copy.exists()){
            Files.copy(copy.toPath(), f.toPath());
         }
      } 
      Scanner s = new Scanner(f);
      while(s.hasNext()) {
         int id = s.nextInt();
         int hardness =(int)(1000*s.nextDouble());
         String blockName = s.next();
         int tool = s.nextInt();
         BLOCK_NAME.put(id,blockName);
         HARDNESS.put(id,hardness);
         TOOL.put(id,tool);
      }      
      s.close();
   }
   
   public boolean isValidBlock(int id) {
      return BLOCK_NAME.containsKey(id);
   }
   
   //populates the Block given with the appropriate tool, name, and hardness.
   public void addInfo(Block b, boolean force) {
      //BlockInfo result = getInfo(b.getTypeId());
	   int id = b.getTypeId();
	   if(!isValidBlock(id)) {
	         throw new IllegalArgumentException("Given ID not found");
	      }
	   String blockName = BLOCK_NAME.get(id);
	   int hardness = HARDNESS.get(id);
	   int tool = TOOL.get(id);
	   b.addInfo(blockName, tool, hardness);
   }
}