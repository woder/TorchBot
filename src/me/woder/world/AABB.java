package me.woder.world;

public class AABB {
	public Vector centre; //Our centre vector, just the centre of the entity
	public double[] f; //an array to hold our data
	public boolean solid;
	
	//The bounding box for mc player is [-0.3, 0, -0.3, 0.3, 1.62, 0.3]
	public AABB(double width, double height){
		centre = new Vector();
		f = new double[3];
		f[0] = width * 0.5D;
		f[1] = height * 0.5D;
		f[2] = width * 0.5D;
	}
	
	public void update(Vector position){
		this.centre.x = position.x;
		this.centre.y = position.y;
		this.centre.z = position.z;
	}
	
	public boolean isSolid(){
		return solid;
	}

}
