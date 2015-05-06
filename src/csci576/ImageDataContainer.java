package csci576;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ImageDataContainer {
	
	int width;
	int height;
	byte[] bytes;
	BufferedImage img;
	
	ImageDataContainer(String fileName, int width, int height){
		this.width = width;
		this.height = height;
		this.img = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		
		try{
			
		File imageFile = new File(fileName);
		InputStream imageFileInputStream = new FileInputStream(imageFile);
		
		long len = imageFile.length();
		bytes = new byte[(int)len];
		int offset = 0;
		int numRead = 0;
		while(offset < bytes.length && (numRead = imageFileInputStream.read(bytes, offset, bytes.length-offset))>=0){
			offset+=numRead;
		}		
		
		imageFileInputStream.close();
		
		} catch (FileNotFoundException e){
			 e.printStackTrace();
		} catch (IOException e){
			 e.printStackTrace();
		}
		
		Draw();
	}
	
	ImageDataContainer(byte [] bytes, int width, int height){
		this.width = width;
		this.height = height;
		this.img = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		this.bytes = new byte[(int)bytes.length];
		System.arraycopy(bytes, 0, this.bytes, 0, bytes.length);
	}
	
	public void Draw(){
		img.getRaster().setDataElements(0, 0, width, height, bytes);	
	}
	
	}