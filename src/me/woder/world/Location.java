package me.woder.world;

public class Location {
	private World world;
	private double x;
	private double y;
	private double z;
	
	
	public Location(World world2, int x, int y, int z) {
		this.world = world2;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Location(World world2, double x, double y, double z) {
		this.world = world2;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public World getWorld(){
		return this.world;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public double getZ(){
		return z;
	}
	
	public Integer getBlockX(){
		return (int) Math.floor(x);
	}
	
	public Integer getBlockY(){
		return (int) Math.floor(y);
	}
	
	public Integer getBlockZ(){
		return (int) Math.floor(z);
	}
	
	public Block getBlock(){
		return world.getBlock((int)x, (int)y, (int)z);
	}

}
