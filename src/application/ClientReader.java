package application;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.apache.http.util.ByteArrayBuffer;

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
    ByteArrayBuffer frameBody = null;
    Banner banner = null;
    Stage primaryStage = null;
    
    GraphicsContext graphicsContext = null;
    
    ClientReader(Socket clientSocket, Stage primaryStage, GraphicsContext graphicsContext) {
    	readBannerBytes = 0;
    	bannerLength = 2;
    	readFrameBytes = 0;
    	frameBodyLength = 0;
    	//initialize frameBody 
    	frameBody = new ByteArrayBuffer(20000);
    	banner = new Banner();
        this.clientSocket = clientSocket;
        this.primaryStage = primaryStage;
        this.graphicsContext = graphicsContext;
    }

    @Override
    public void run() {
        try {
            while (true) {
                InputStream inputStream = clientSocket.getInputStream();
                byte[] byteArray = new byte[20000];
                
               // int size = inputStream.read(byteArray);
                
                for (int chunk = inputStream.read(byteArray);  ;) {
                    System.out.println("chunk(length = "+ chunk + " )");
                    for (int cursor = 0; chunk > cursor ;  ) {
                    	
                      if (readBannerBytes < bannerLength) {
                        switch (readBannerBytes) {
                        case 0:
                          // version
                          if(byteArray[cursor] < 0)
                        	  byteArray[cursor] = 0;
                          banner.setVersion(byteArray[cursor]);
                          break;
                        case 1:
                          // length
                          if(byteArray[cursor] < 0)
                          	byteArray[cursor] = 0;
                          bannerLength = byteArray[cursor];
                          banner.setLength(bannerLength);
                          break;
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                            if(byteArray[cursor] < 0)
                          	  byteArray[cursor] = 0;
                            System.out.println( "pid, chunk cur " + byteArray[cursor]);
                            System.out.println( "pid, chunk cur1 (readBannerBytes - 2) = " + (readBannerBytes - 2));
                            System.out.println( "pid, chunk cur2 " + ((byteArray[cursor] << ((readBannerBytes - 2) * 8))));
                            System.out.println( "pid, chunk cur3 " + ((byteArray[cursor] << ((readBannerBytes - 2) * 8)) >>> 0));
                          // pid
                        	banner.setPid(banner.getPid() + ((byteArray[cursor] << ((readBannerBytes - 2) * 8)) >>> 0));                            
                          break;
                        case 6:
                        case 7:
                        case 8:
                        case 9:
                            if(byteArray[cursor] < 0)
                          	  byteArray[cursor] = 0;
                            System.out.println( "real width, chunk cur " + byteArray[cursor]);
                            System.out.println( "real width, chunk cur1 (readBannerBytes - 6) = " + (readBannerBytes - 6));
                            System.out.println( "real width, chunk cur2 " + ((byteArray[cursor] << ((readBannerBytes - 6) * 8))));
                            System.out.println( "real width, chunk cur3 " + ((byteArray[cursor] << ((readBannerBytes - 6) * 8)) >>> 0));

                          // real width
                        	banner.setRealWidth(banner.getRealWidth() + ((byteArray[cursor] << ((readBannerBytes - 6) * 8)) >>> 0));
                          break;
                        case 10:
                        case 11:
                        case 12:
                        case 13:
                            if(byteArray[cursor] < 0)
                          	  byteArray[cursor] = 0;
                            System.out.println( "real height, chunk cur " + byteArray[cursor]);
                            System.out.println( "real height, chunk cur1 (readBannerBytes - 10) = " + (readBannerBytes - 10));
                            System.out.println( "real height, chunk cur2 " + ((byteArray[cursor] << ((readBannerBytes - 10) * 8))));
                            System.out.println( "real height, chunk cur3 " + ((byteArray[cursor] << ((readBannerBytes - 10) * 8)) >>> 0));

                          // real height
                        	banner.setRealHeight(banner.getRealHeight() + ((byteArray[cursor] << ((readBannerBytes - 10) * 8)) >>> 0));
                          break;
                        case 14:
                        case 15:
                        case 16:
                        case 17:
                            if(byteArray[cursor] < 0)
                          	  byteArray[cursor] = 0;
                        	System.out.println( "virtual width, chunk cur " + byteArray[cursor]);
                        	System.out.println( "virtual width, chunk cur1 (readBannerBytes - 14) = " + (readBannerBytes - 14));
                            System.out.println( "virtual width, chunk cur2 " + ((byteArray[cursor] << ((readBannerBytes - 14) * 8)) ));
                            System.out.println( "virtual width, chunk cur3 " + ((byteArray[cursor] << ((readBannerBytes - 14) * 8)) >>> 0));

                          // virtual width
                        	banner.setVirtualWidth( banner.getVirtualWidth() + ((byteArray[cursor] << ((readBannerBytes - 14) * 8)) >>> 0) );
                        	break;
                        case 18:
                        case 19:
                        case 20:
                        case 21:
                            if(byteArray[cursor] < 0)
                          	  byteArray[cursor] = 0;
                        	System.out.println( "virtual height, chunk cur " + byteArray[cursor]);
                        	System.out.println( "virtual height, chunk cur1 (readBannerBytes - 18) = " + (readBannerBytes - 18));
                            System.out.println( "virtual height, chunk cur2 " + ((byteArray[cursor] << ((readBannerBytes - 18) * 8)) ));
                            System.out.println( "virtual height, chunk cur3 " + ((byteArray[cursor] << ((readBannerBytes - 18) * 8)) >>> 0));

                          // virtual height
                        	banner.setVirtualHeight( banner.getVirtualHeight() + ((byteArray[cursor] << ((readBannerBytes - 18) * 8)) >>> 0) ); 
                        	break;
                        case 22:
                            if(byteArray[cursor] < 0)
                          	  byteArray[cursor] = 0;
                        	System.out.println( "orientation, chunk cur " + byteArray[cursor]);
                            System.out.println( "orientation, chunk cur2 " + (byteArray[cursor] * 90));

                          // orientation
                        	banner.setOrientation(banner.getOrientation() + (byteArray[cursor] * 90) );
                        	break;
                        case 23:
                            if(byteArray[cursor] < 0)
                          	  byteArray[cursor] = 0;
                          // quirks
                        	banner.setQuirks(byteArray[cursor]);
                          break;
                        }

                        cursor += 1;
                        readBannerBytes += 1;

                        if (readBannerBytes == bannerLength) {
                          System.out.println("banner = " + banner);
                        }
                      }
                      else if (readFrameBytes < 4) {
                          if(byteArray[cursor] < 0)
                        	  byteArray[cursor] = 0;
                    	System.out.println("headerbyte : " + byteArray[cursor]);
                    	System.out.println("headerbyte1 : " + ((byteArray[cursor] << (readFrameBytes * 8))));
                    	System.out.println("headerbyte2 : " + ((byteArray[cursor] << (readFrameBytes * 8)) >>> 0));
                        frameBodyLength += ((byteArray[cursor] << (readFrameBytes * 8)) >>> 0);
                        cursor += 1;
                        readFrameBytes += 1;
                        System.out.println("headerbyte" + readFrameBytes +"(val=" + frameBodyLength + ")");
                      }
                      else {
                        if ( (chunk - cursor) >= frameBodyLength) {
                        	System.out.println("bodyfin(len=" + frameBodyLength + ",cursor=" + cursor + ")");
                        	frameBody.append(byteArray, cursor, cursor + frameBodyLength);
                        	
                          // Sanity check for JPG header, only here for debugging purposes.
                          if (frameBody.byteAt(0) != 0xFF || frameBody.byteAt(1) != 0xD8) {
                        	  System.out.println("Frame body does not start with JPG header', frameBody");
                        	  return;
                        	  //process.exit(1)
                          }
                          
                          displayImage(frameBody.buffer());

                          cursor += frameBodyLength;
                          frameBodyLength = readFrameBytes = 0;
                          frameBody = new ByteArrayBuffer(4096);
                        }
                        else {
                          System.out.println("body(len=" + (chunk - cursor) + ")"); 
                          frameBody.append(byteArray, cursor, chunk);
                         
                          frameBodyLength -= (chunk - cursor);
                          readFrameBytes += (chunk - cursor);
                          cursor = chunk;
                        }
                      }
                    }
                  }
         
            }
        } catch (Exception e) {                
            e.printStackTrace();
        }
    }
    
    public void displayImage(byte img[]){
        StackPane sp = new StackPane();
        
        
//        Image img = new Image("javafx.jpg");
//        BufferedImage bufferedImage = null;
//        try {
//			bufferedImage = ImageIO.read(new ByteArrayInputStream(image));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        
        Image im = new Image( new ByteArrayInputStream(img) );
        
        
        
        //Image img = Toolkit.getDefaultToolkit().createImage(bufferedImage.getSource());
        graphicsContext.drawImage(im, 0, 0, 720, 1280);
        
        primaryStage.show();
        
        
        
    }
    
    
}


