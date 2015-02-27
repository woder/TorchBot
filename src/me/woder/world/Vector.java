package me.woder.world;

public class Vector {
	public double x;
	public double y;
	public double z;
	
	public Vector(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector() {
		x = 0D;
		y = 0D;
		z = 0D;
	}

	public double getDistSQ(Vector v){
		double deltax = this.x - v.x;
		double deltay = this.y - v.y;
		double deltaz = this.z - v.z;
		return deltax * deltax + deltay * deltay + deltaz * deltaz;
	}

}
