package csci576;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VectorQuantizer {

	public static void main(String args[]){
	
	if(args.length != 2){
		System.out.println("Provide Arguments in Format -> VectorQuantization image1.raw N");
		System.exit(0);
	}
	
	String fileName = args[0];
	int N = Integer.parseInt(args[1]);
	
	if(!Helpers.isPowerofTwo(N)){
		System.out.println("N = " + N + " is not a power of 2.");
		System.exit(0);
	}

	int width  = 352;
	int height = 288;
	
	ImageDataContainer actualImage 		= new ImageDataContainer(fileName,width,height);
	ImageDataContainer processedImage 	= new ImageDataContainer(fileName,width,height);
	
	VectorQuantizerImpl quantizer = new VectorQuantizerImpl();
	quantizer.GenerateVectors(processedImage.bytes);
	quantizer.InitializeCodeWords(N);
	quantizer.FindCodeWords(N);
	quantizer.QuantizeBytes(processedImage.bytes);
	
	processedImage.Draw();	
	JPanel panel = new JPanel();
	panel.add (new JLabel (new ImageIcon(actualImage.img)));
	panel.add( new JLabel (new ImageIcon(processedImage.img)));
	JFrame frame = new JFrame("Display Images");
	frame.getContentPane().add(panel);
	frame.pack();
	frame.setVisible(true);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	}
	
}