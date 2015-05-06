package csci576;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class VectorQuantizerImpl {
	ArrayList <MyVector> vectors;
	ArrayList <CodeWord> codewords;
	
	VectorQuantizerImpl(){
		vectors   = new ArrayList<MyVector>();
		codewords = new ArrayList<CodeWord>();	
	}
	
	public void GenerateVectors(byte[] bytes){
		
		int i,x,y;
		
		for(i = 0; i < bytes.length; i+=2){
			x = Helpers.ByteToInt(bytes[i]);
			y = Helpers.ByteToInt(bytes[i+1]);
			MyVector vector = new MyVector(x, y);
			vectors.add(vector);		
		}		
	}
	
	public void InitializeCodeWords(int N){
		
		int i,j;
		int div  =  (int) (Math.log(N) / Math.log(2));
		int stepsize = (int) Math.floor(256.0/div);
		int init = ((stepsize/2)-1);
		
		int k = 0;
		
		if(N==2){
		CodeWord cw1 = new CodeWord(127,127);
		CodeWord cw2 = new CodeWord(191,191);
		codewords.add(cw1);
		codewords.add(cw2);
		}
		else{
		
		for(i=0; k < N; i+=stepsize){
			for(j=0; j <= 255 && k < N; j+=stepsize){
				CodeWord cw = new CodeWord(i+init,j+init);
				codewords.add(cw);
				k++;
			}
		}
		
		}
	}
	
	private CodeWord findBMU(MyVector vector){
		
		int minDi = 1000000, Di;
		CodeWord BMU = null;
		
		for(CodeWord code: codewords){
			
			Di = vector.distanceBetween(code);
			
			if(minDi > Di){
				minDi = Di;
				BMU = code;
			}
	    }
	
		return BMU;
	}
	
	public void FindCodeWords(int N){
		
		boolean converged = false;
		int i =0;
		while(!converged){
			i++;
			for(MyVector vector: vectors){
				CodeWord BMU = findBMU(vector);
				BMU.ErrorVector.add(vector);
				BMU.vectorcount++;
			}
		
		Collections.sort(codewords);
		
		int k = 0, j = 0;
		converged = true;
		for(j=0; j<codewords.size(); j++){
			
			CodeWord code = codewords.get(j);
			int diff = code.performAdjustment();
			
			if(diff == -1){
				codewords.remove(code);
				CodeWord hcw = codewords.get(k);
				CodeWord cw = new CodeWord(hcw.x+4,hcw.y+4);
				codewords.add(cw);
				k++;
			}
			else if(diff != 0){
				converged = false;
			}
		 }
		}
	}
	
	public void QuantizeBytes(byte[] bytes){
		
		int i,x,y;
		CodeWord BMU = null;
		int errorx = 0, errory = 0;
		for(i=0; i<bytes.length; i+=2){
			x = Helpers.ByteToInt(bytes[i]);
			y = Helpers.ByteToInt(bytes[i+1]);
			MyVector vector = new MyVector(x, y);
			BMU = findBMU(vector);
			
			bytes[i] = (byte)BMU.x;
			bytes[i+1] = (byte)BMU.y;
			errorx += Math.pow(vector.x - BMU.x, 2);
			errory += Math.pow(vector.y - BMU.y, 2);

		}		
		
			float meanxerr = (float)errorx/vectors.size();
			float meanyerr = (float)errory/vectors.size();
			float meanerr = (float) ((meanxerr + meanyerr)/2.0);
			
			System.out.println("CodeWords: " + codewords.size());
			
			for(CodeWord code: codewords){
				System.out.println("x: " + code.x  + " y: " + code.y);
			}
			System.out.println("Mean Squared Error (MSE): " + meanerr);
		
	}


}