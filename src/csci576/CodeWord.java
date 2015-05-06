package csci576;

public class CodeWord extends MyVector implements Comparable<CodeWord>{
	
	MyVector ErrorVector;
	int vectorcount;
	
	CodeWord(int x, int y) {
		super(x, y);
		ErrorVector = new MyVector(0, 0);
		vectorcount = 0;
	}
	
	public int performAdjustment(){
		
		if(vectorcount == 0){
			return -1;
		}
		
		ErrorVector.average(vectorcount);
		
		int diff = this.meanDiff(ErrorVector);
		
		this.x = ErrorVector.x;
		this.y = ErrorVector.y;
			
		vectorcount = 0;
		ErrorVector.reset();
		
		return diff;
	}

	@Override
	public int compareTo(CodeWord o) {
		return this.vectorcount - o.vectorcount;
	}
		
}
