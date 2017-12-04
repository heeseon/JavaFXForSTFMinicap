package application;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.apache.http.util.ByteArrayBuffer;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

class ClientReader extends Thread {
    Socket clientSocket = null;
    
    int readBannerBytes = 0;
    int bannerLength = 2;
    int readFrameBytes = 0;
    int frameBodyLength = 0;
    int frameReadLength = 0;
    ByteArrayBuffer frameBody = null;
    byte testLength[];
    Banner banner = null;
    Stage primaryStage = null;
    
    boolean firstRead = true;
    //int testLength = 0;
    
    int picNum = 0;
    
    GraphicsContext graphicsContext = null;
    
    ClientReader(Socket clientSocket, Stage primaryStage, GraphicsContext graphicsContext) {
    	readBannerBytes = 0;
    	bannerLength = 2;
    	readFrameBytes = 0;
    	frameBodyLength = 0;
    	//initialize frameBody 
    	frameBody = new ByteArrayBuffer(0);
    	testLength = new byte[4];
    	//banner = new Banner();
        this.clientSocket = clientSocket;
        this.primaryStage = primaryStage;
        this.graphicsContext = graphicsContext;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = clientSocket.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            
            banner = getBanner(bis);
            
            while(true){
            	byte[] byteArray = getScreenShot(bis);
                
//                toByteArray("testpicture" + (picNum++) + ".jpg", byteArray);            
//              if (frameBody.byteAt(0) != 0xFF || frameBody.byteAt(1) != 0xD8) {
//            	  
//              }
//              else 
            	  displayImage(byteArray);
            }
            
            
               
            
        } catch (Exception e) {                
            e.printStackTrace();
        }
    }
    
    public void displayImage(byte img[]){
        StackPane sp = new StackPane();

        
        Platform.runLater(() -> {
            Image im = new Image( new ByteArrayInputStream(img) );
            
            //Image img = Toolkit.getDefaultToolkit().createImage(bufferedImage.getSource());
            graphicsContext.drawImage(im, 0, 0, 720, 1280);
            
            primaryStage.show();
        });

        
    }
    
    
    public Banner getBanner(BufferedInputStream bis){
    	byte byteArray[] = new byte[24];
    	Banner banner = new Banner();
    
    	int chunk = 0;
		try {
			chunk = bis.read(byteArray);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	for (int cursor = 0; chunk > cursor ; ) {
              if (readBannerBytes < bannerLength) {
                switch (readBannerBytes) {
                case 0:
                  // version
                  System.out.println( "version, cur " + cursor);
                  banner.setVersion(byteArray[cursor] & 0xFF);
                  break;
                case 1:
                  // length
                  bannerLength = byteArray[cursor]& 0xFF;
                  banner.setLength(bannerLength);
                  System.out.println( "bannerLength " + bannerLength);
                  break;
                case 2:
                case 3:
                case 4:
                case 5:
                    System.out.println( "before pid, chunk cur " + byteArray[cursor]);
                    System.out.println( "pid, cur " + cursor);
                    System.out.println( "pid, chunk cur " + byteArray[cursor]);
                    System.out.println( "pid, chunk cur1 (readBannerBytes - 2) = " + (readBannerBytes - 2));
                    System.out.println( "pid, chunk cur2 " + ((byteArray[cursor] << ((readBannerBytes - 2) * 8))));
                    System.out.println( "pid, chunk cur3 " + ((byteArray[cursor] << ((readBannerBytes - 2) * 8)) >>> 0));
                  // pid
                	banner.setPid(banner.getPid() + (((byteArray[cursor]& 0xFF) << ((readBannerBytes - 2) * 8)) >>> 0));                            
                  break;
                case 6:
                case 7:
                case 8:
                case 9:
                	System.out.println( "before real width, chunk cur " + byteArray[cursor]);
                    System.out.println( "real width, cur " + cursor);
                    System.out.println( "real width, chunk cur " + byteArray[cursor]);
                    System.out.println( "real width, chunk cur1 (readBannerBytes - 6) = " + (readBannerBytes - 6));
                    System.out.println( "real width, chunk cur2 " + ((byteArray[cursor] << ((readBannerBytes - 6) * 8))));
                    System.out.println( "real width, chunk cur3 " + ((byteArray[cursor] << ((readBannerBytes - 6) * 8)) >>> 0));

                  // real width
                	banner.setRealWidth(banner.getRealWidth() + (((byteArray[cursor]& 0xFF) << ((readBannerBytes - 6) * 8)) >>> 0));
                  break;
                case 10:
                case 11:
                case 12:
                case 13:
                	System.out.println( "before real height, chunk cur " + byteArray[cursor]);
                    System.out.println( "real height, cur " + cursor);
                    System.out.println( "real height, chunk cur " + byteArray[cursor]);
                    System.out.println( "real height, chunk cur1 (readBannerBytes - 10) = " + (readBannerBytes - 10));
                    System.out.println( "real height, chunk cur2 " + ((byteArray[cursor] << ((readBannerBytes - 10) * 8))));
                    System.out.println( "real height, chunk cur3 " + ((byteArray[cursor] << ((readBannerBytes - 10) * 8)) >>> 0));

                  // real height
                	banner.setRealHeight(banner.getRealHeight() + (((byteArray[cursor]& 0xFF) << ((readBannerBytes - 10) * 8)) >>> 0));
                  break;
                case 14:
                case 15:
                case 16:
                case 17:
                	System.out.println( "before virtual width, chunk cur " + byteArray[cursor]);
                    System.out.println( "virtual width, cur " + cursor);
                	System.out.println( "virtual width, chunk cur " + byteArray[cursor]);
                	System.out.println( "virtual width, chunk cur1 (readBannerBytes - 14) = " + (readBannerBytes - 14));
                    System.out.println( "virtual width, chunk cur2 " + ((byteArray[cursor] << ((readBannerBytes - 14) * 8)) ));
                    System.out.println( "virtual width, chunk cur3 " + ((byteArray[cursor] << ((readBannerBytes - 14) * 8)) >>> 0));

                  // virtual width
                	banner.setVirtualWidth( banner.getVirtualWidth() + (((byteArray[cursor]& 0xFF) << ((readBannerBytes - 14) * 8)) >>> 0) );
                	break;
                case 18:
                case 19:
                case 20:
                case 21:
                	System.out.println( "before virtual height, chunk cur " + byteArray[cursor]);
                    System.out.println( "virtual height, cur " + cursor);
                	System.out.println( "virtual height, chunk cur " + byteArray[cursor]);
                	System.out.println( "virtual height, chunk cur1 (readBannerBytes - 18) = " + (readBannerBytes - 18));
                    System.out.println( "virtual height, chunk cur2 " + ((byteArray[cursor] << ((readBannerBytes - 18) * 8)) ));
                    System.out.println( "virtual height, chunk cur3 " + ((byteArray[cursor] << ((readBannerBytes - 18) * 8)) >>> 0));

                  // virtual height
                	banner.setVirtualHeight( banner.getVirtualHeight() + (((byteArray[cursor]& 0xFF) << ((readBannerBytes - 18) * 8)) >>> 0) ); 
                	break;
                case 22:
                	System.out.println( "before orientation, chunk cur " + byteArray[cursor]);
                    System.out.println( "orientation, cur " + cursor);
                	System.out.println( "orientation, chunk cur " + byteArray[cursor]);
                    System.out.println( "orientation, chunk cur2 " + (byteArray[cursor] * 90));

                  // orientation
                	banner.setOrientation(banner.getOrientation() + ((byteArray[cursor]& 0xFF) * 90) );
                	break;
                case 23:
                    System.out.println( "quirks, cur " + cursor);
                  // quirks
                	banner.setQuirks(byteArray[cursor]& 0xFF);
                  break;
                }

                cursor += 1;
                readBannerBytes += 1;

                if (readBannerBytes == bannerLength) {
                  System.out.println("banner = " + banner);
                }
              }
    	}
		return banner;
             
    }
    
    public byte[] getScreenShot(BufferedInputStream bis){
    	byte imageDataLength[] = new byte[4];
    	
    	byte imageData[] = null;
    	
    	int chunk = 0;
		try {
			chunk = bis.read(imageDataLength);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		int frameBodyLength = 0;
		
		for (int readFrameBytes = 0; chunk > readFrameBytes ; readFrameBytes ++ ) {
			frameBodyLength += (((imageDataLength[readFrameBytes]& 0xFF) << (readFrameBytes * 8)) >>> 0);
		}
		
		System.out.println("headerbyte" + readFrameBytes +"(val=" + frameBodyLength + ")");
    	
    	imageData = new byte[frameBodyLength];
    	
    	int readImageData = 0;
    	
    	while(true){
    		
    		try {
    			chunk = bis.read(imageData, readImageData, frameBodyLength - readImageData);
    			readImageData += chunk;
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		
    		if(readImageData == frameBodyLength)
    			break;

    	}
    	
    	return imageData;
    	
    	
    }
    
    
    
    public static byte[] toByteArray(String name, byte[] data) throws IOException { 
    	   ByteArrayOutputStream out = new ByteArrayOutputStream(); 
    	   boolean threw = true; 
    	   FileOutputStream outfile = new FileOutputStream(new File(name)); 
    	   try { 
    	    
    	       out.write(data, 0, data.length); 
    	       out.writeTo(outfile);
    	       
    	   } finally { 
    	     try { 
    	       out.close(); 
    	       outfile.close();
    	     } catch (IOException e) { 
    	       if (threw) { 
    	         System.out.println("IOException thrown while closing, " + e); 
    	       } else {
    	         throw e;
    	       } 
    	     } 
    	   } 
    	   return out.toByteArray(); 
    	 }
    
    
}


