package csci576;

public class Helpers {
	
	static int ByteToInt(byte b){
		return (int)b & 0x000000FF;
	}

	static boolean isPowerofTwo(int N){
		return N!=1 && (N & (N - 1)) == 0;
	}
}