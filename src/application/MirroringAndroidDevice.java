package application;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
/**
*
* @author zoranpavlovic.blogspot.com
*/
public class MirroringAndroidDevice extends Application {
	
	Socket clientSocket = null;
	 final Canvas canvas = new Canvas(720, 1280);
     final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
     
	
        /**
        * @param args the command line arguments
        */
        public static void main(String[] args) {
        Application.launch(args);
        }
        @Override
        public void start(Stage primaryStage) {
        	
      
        	try {
				System.out.println("hostname = " + (InetAddress.getLocalHost()).getCanonicalHostName());
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	try {
				System.out.println("host address = " + InetAddress.getLocalHost().getHostAddress());
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	
        	clientSocket = new Socket();
            try {
				clientSocket.connect(new InetSocketAddress("localhost", 1717));
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            initDraw(graphicsContext);
            
            Group root = new Group();
            Scene scene = new Scene(root);
            scene.setFill(Color.BLACK);
            HBox box = new HBox();
            box.getChildren().addAll(canvas);
//            
//            ImageView iv1 = new ImageView();
//            box.getChildren().add(iv1);
            root.getChildren().add(box);
    
            primaryStage.setTitle("minicap mirroring the screen~");
            primaryStage.setWidth(720);
            primaryStage.setHeight(1280);
            primaryStage.setScene(scene); 
            primaryStage.sizeToScene(); 
            primaryStage.show();

            ClientReader clientReader = new ClientReader(clientSocket, primaryStage, graphicsContext);
            clientReader.start();
            
 
        }
        
        private void initDraw(GraphicsContext gc){
        	 
            double canvasWidth = gc.getCanvas().getWidth();
            double canvasHeight = gc.getCanvas().getHeight();
             
            gc.setFill(Color.LIGHTGRAY);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(5);
     
            gc.fill();
            gc.strokeRect(
                    0,              //x of the upper left corner
                    0,              //y of the upper left corner
                    canvasWidth,    //width of the rectangle
                    canvasHeight);  //height of the rectangle
     
            gc.setLineWidth(1);

        }
           
        
}
