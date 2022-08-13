import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Color;

/**
*A class to implement median filter
*@author Bina Mukuyamba
*/
public class  MedianFilterSerial{
   public static void main (String[] args){
       BufferedImage image = null;
       //for testing on jgrasp remove later
       String input = "data/apple.jpeg";
       String output = "data/output.jpeg";
       int window = 9; //change later
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
        
        if(window%2!=0 && window>=3){   //window must be odd
            int pixelSurround =(window-1)/2; 
            for(int x=0;x<w-window;x++){
                for(int y=pixelSurround;y<h;y++){
                   // ArrayList<Integer> the_pixels = new ArrayList<>();
                    ArrayList<Integer> r = new ArrayList<>();
                    ArrayList<Integer> g = new ArrayList<>();
                    ArrayList<Integer> b = new ArrayList<>();

                    for(int column = x;column<=x+window;column++){ 
                        for (int mi = -pixelSurround; mi <= pixelSurround; mi++) { 
                            int ColumnIndex = Math.min(Math.max(mi + y, 0),h - 1);
                           // the_pixels.add(image.getRGB(column,ColumnIndex));
                           int pixel = image.getRGB(column,ColumnIndex);
                           r.add((pixel & 0x00ff0000) >> 16));
                           g.add((pixel & 0x0000ff00) >> 8));
                           b.add((pixel & 0x000000ff) >> 0));
                        }  
                    }
                    Collections.sort(r);
                    Collections.sort(g);
                    Collections.sort(b);
                    int red =r.get(r.size()/2); //arraylist sorted
                    int green=g.get(g.size()/2);
                    int blue=b.get(b.size()/2);
                    int dpixel = (0xff000000) |(red << 16) |(green << 8) |(blue << 0);
                    image.setRGB(x, y,dpixel);
                }
            }
        }
        else{
            System.exit(0);}

         
        try{
            File out = new File(output); //create image to store output
            ImageIO.write(image,"jpg",out);
        }catch (IOException e){
            System.out.println("Error writing to output file.");
            e.printStackTrace();
        }


   




   }
}