import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;


/**
*A class to implement median filter
*@author Bina Mukuyamba
*/
public class MedianFilterParallel extends RecursiveAction{

   /**
   *Constructor to instantiate class
   *
   */
    BufferedImage the_image;
   
       int window;
       int w;
       int h;
       int Xoff;
       int Yoff;
       int Sequential_cutoff=1000000; //experiment
       int PixelSurround;
       
   public MedianFilterParallel(int Xdis, int Ydis, int image_w, int image_h, int Window, BufferedImage image){
      the_image = image;
      Xoff = Xdis;
      Yoff = Ydis;
      w = image_w;
      h = image_h;
      window = Window;
      PixelSurround =(window-1)/2; 

      
   
   }
   
   /**
   *Do calculation in this thread
   *
   */
   public void computeDirect(){
   
       for(int x=Xoff;x<(w-window)+Xoff;x++){
                for(int y=PixelSurround+Yoff;y<h+Yoff;y++){
                    ArrayList<Integer> the_pixels = new ArrayList<>();
                    for(int column = x;column<=x+window;column++){ 
                        for (int mi = -PixelSurround; mi <= PixelSurround; mi++) { 
                            int ColumnIndex = Math.min(Math.max(mi + y, 0),Yoff+h - 1);
                            the_pixels.add(the_image.getRGB(column,ColumnIndex));
                          
                        }  
                    }
                    Collections.sort(the_pixels);
                    int dpixel = the_pixels.get(the_pixels.size()/2);
                    the_image.setRGB(x, y,dpixel);
                }
            } //end of outer loop
   
   
   }

   /**
   *equivaleent to running a thread
   *
   */
   public void compute(){
      if(window%2!=0 && window>=3){
         int Area = w*h;
         if (Area<Sequential_cutoff){
            computeDirect();
            }
      
         else{
            int SplitRow = (int)Math.floor((double)w/2);
            int SplitCol = (int)Math.floor((double)h/2);
            MedianFilterParallel left = new MedianFilterParallel(0,0,SplitRow,SplitCol,window,the_image);
            MedianFilterParallel right = new MedianFilterParallel(SplitRow,SplitCol,w-SplitRow,h-SplitCol,window,the_image);
            left.fork();
            right.compute();
            left.join();         
             }
        }
      else{
      
         System.exit(0);
      
      }
   
   }
  
   
   /**
   *start the threads
   */
   public static void main(String[] args){
   
  
       
            
      BufferedImage the_image=null;
       //for testing on jgrasp remove later
      // String input = "data/apple.jpeg";
     // String input = "snap.jpg";
     // String output = "outputMedianParallel.jpeg";
       // String output = "data/MedianParallelOutput.jpeg";
       int window = 0; //change later to 0
       int w = 0;
       int h =0;
       
       if (args.length>0){
        String input=args[0];
        String  output=args[1];
        window = Integer.parseInt(args[2]);
         
      else
         {
            System.exit(0);
         }*/
       
       
        try{
            File f = new File(input);
            BufferedImage data = ImageIO.read(f);
            w = data.getWidth();
            h = data.getHeight();
            
            
            the_image = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
            the_image = ImageIO.read(f);
            
        }catch(IOException e){
            System.out.println("Error processing image.");
            e.printStackTrace();}


            
            MedianFilterParallel mfp = new MedianFilterParallel(0,0,w,h,window,the_image);
            ForkJoinPool thread_pool = new ForkJoinPool();
            long start = System.currentTimeMillis();
            thread_pool.invoke(mfp);
            
            
        System.out.println("execution time: "+(double)(System.currentTimeMillis()-start)/1000+"s for image width: "+w+" height: "+h+" window: "+window); 
        try{
            File out = new File(output); //create image to store output
            ImageIO.write(the_image,"jpg",out);
        }catch (IOException e){
            System.out.println("Error writing to output file.");
            e.printStackTrace();
        }


   
   
   }//end of main


}//end of class