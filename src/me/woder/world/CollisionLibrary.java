package me.woder.world;

public class CollisionLibrary {
	/*Static method library for checking collisions
	 * Accessed staticly with CollisionLibrary.method();
	 */
	
	//Simple overlap test, this should return true if these two boxes overlap
	public static boolean doesOverlap(final AABB box1, final AABB box2) {
		   if(Math.abs(box1.centre.x - box2.centre.x) > (box1.f[0] + box2.f[0])) return false;
		   if(Math.abs(box1.centre.y - box2.centre.y) > (box1.f[1] + box2.f[1])) return false;
		   if(Math.abs(box1.centre.z - box2.centre.z) > (box1.f[2] + box2.f[2])) return false;
		   return true;
    }
	
	/**
	 *The first box is the box that we are and the box two is the box that we are colliding with
	 *
	 **/
	public static Vector willOverlapVec(final AABB box1, final AABB box2, double x, double y, double z){
		   Vector result = new Vector();//Creates new vector, remember that vector's default is 0,0,0
		   AABB temp = new AABB(box1.f[0]*2,box1.f[1]*2); //A temp object copy of the box that we can use to test the future
		   temp.update(new Vector(x,y,z)); //our future coords
		   double vx = temp.centre.x - box2.centre.x, vy = temp.centre.y - box2.centre.y, vz = temp.centre.z - box2.centre.z;
		   if(Math.abs(vx) > (temp.f[0] + box2.f[0])){ //if the distance from centre to centre is bigger than centre plus width then we obviously are coliding
			   //Calculate the distance 
			   result.x = vx;
		   }
		   if(Math.abs(vy) > (temp.f[1] + box2.f[1])){
			   result.y = vy;
		   }
		   if(Math.abs(vz) > (temp.f[2] + box2.f[2])){
			   result.z = vz;
		   }
		   return result;		
	}
	
	public static boolean willOverlap(final AABB box1, final AABB box2, double x, double y, double z){
		   AABB temp = new AABB(box2.f[0]*2,box2.f[1]*2); //A temp object copy of the box that we can use to test the future
		   temp.update(new Vector(x,y,z)); //our future coords
		   double vx = temp.centre.x - box2.centre.x, vy = temp.centre.y - box2.centre.y, vz = temp.centre.z - box2.centre.z;
		   if(Math.abs(vx) > (temp.f[0] + box2.f[0])){ //if the distance from centre to centre is bigger than centre plus width then we obviously are coliding
			   //Calculate the distance 
			   return false;
		   }
		   if(Math.abs(vy) > (temp.f[1] + box2.f[1])){
			   return false;
		   }
		   if(Math.abs(vz) > (temp.f[2] + box2.f[2])){
			   return false;
		   }
		   return true;		
	}

}
