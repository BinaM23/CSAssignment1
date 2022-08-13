import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Color;

/**
*A class to implement mean filter serial
*@author Bina Mukuyamba
*/
public class MeanFilterSerial{
   public static void main(String[]args){
       BufferedImage image = null;
       //for testing on jgrasp remove later
       String input = "data/apple.jpeg";
       String output = "data/outputMeanS.jpeg";
       int window = 3; //change later
       int w = 0;
       int h =0;
        
      /*  if(args.length>0){ //args need to make sense
            String input = args[0];
            String output = args[1];
            int window = Integer.parseInt(args[2]);
        }
        else{
            
            System.exit(0);
        }*/
        try{
            File f = new File(input);
            BufferedImage data = ImageIO.read(f);
            w = data.getWidth();
            h = data.getHeight();
            
            image = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
            image = ImageIO.read(f);
            
        }catch(IOException e){
            System.out.println("Error processing image.");
            e.printStackTrace();
        }
        long start = System.currentTimeMillis();        
        if(window%2!=0 && window>=3){   //window must be odd
            int pixelSurround =(window-1)/2; 
            for(int x=0;x<w-window;x++){
                for(int y=pixelSurround;y<h;y++){
                    float r=0;
                    float g=0; //using floats to prevent loss of accuracy during divison 
                    float b=0;
                    for(int column = x;column<=x+window;column++){ 
                        for (int mi = -pixelSurround; mi <= pixelSurround; mi++) { 
                            int ColumnIndex = Math.min(Math.max(mi + y, 0),h - 1);
                            int pixel=image.getRGB(column,ColumnIndex);
                              r += (float)((pixel & 0x00ff0000) >> 16)/(window*window);
                              g += (float)((pixel & 0x0000ff00) >>  8)/(window*window);
                              b += (float)((pixel & 0x000000ff) >>  0)/(window*window);
                        }  
                    }
                    int dpixel=0;   
                    dpixel=(0xff000000) |((int)r << 16) |((int)g << 8) |((int)b << 0);
                    image.setRGB(x, y,dpixel);
                    
                }
            }
        }
        else{
            System.exit(0);}

       System.out.println("execution time: "+(double)(System.currentTimeMillis()-start)/1000+"s for image width: "+w+" height: "+h+" window: "+window);  
        try{
            File out = new File(output); //create image to store output
            ImageIO.write(image,"jpg",out);
        }catch (IOException e){
            System.out.println("Error writing to output file.");
            e.printStackTrace();
        }


 

 }
}