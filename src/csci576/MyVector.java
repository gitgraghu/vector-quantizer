package csci576;

public class MyVector{
	
	int x,y;
	
	MyVector(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int distanceBetween(MyVector v){
		int distance = (int) Math.abs(Math.pow((this.x - v.x),2) + Math.pow((this.y - v.y),2));
		return distance;	
	}
	
	public void add(MyVector v){
		this.x = this.x + v.x; 
		this.y = this.y + v.y;
	}
	
	public int meanDiff(MyVector v){
		int diffx = Math.abs(this.x - v.x); 
		int diffy = Math.abs(this.y - v.y);
		return (diffx + diffy);
	}
	
	public void average(int m){
		this.x = Math.round((this.x/m));
		this.y = Math.round((this.y/m));
	}
	
	public void reset(){
		this.x = 0;
		this.y = 0;
	}
	
	@Override
	public String toString() {
		return ("Vector-> (x: " + x + ", y: " + y + ")");
	}
	
}