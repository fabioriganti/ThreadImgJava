package ImagePackage;

import java.awt.image.BufferedImage;
import java.util.concurrent.CountDownLatch;

public class ReadRGB extends Thread{
    private final int row;
    private final CountDownLatch latch;
    private final BufferedImage img;
    private final Pixel[] sharedBuffer;

    public ReadRGB(int row, Pixel[] sharedBuffer, BufferedImage img, CountDownLatch latch){
        super("Thread"+row);
        this.row=row;
        this.sharedBuffer = sharedBuffer;
        this.latch = latch;
        this.img = img;
    }

    public void run(){
        int[] rgb = getRGBFromRow(row);
        System.out.println(Thread.currentThread().getName() +" è iniziato.");
        int maxBlue = (rgb[0])&255;
        int column = 0;
        int tmp;
        for(int i=1; i<rgb.length; i++){
            tmp=(rgb[i])&255;
            if(tmp > maxBlue){
                maxBlue = tmp;
                column = i;
            }
        }
        Pixel p = new Pixel(maxBlue, row, column);
        sharedBuffer[row] = p;
        System.out.println(Thread.currentThread().getName() +" è finito.");
        latch.countDown();
    }

    private int[] getRGBFromRow(int row){
        int height = img.getHeight();
        int[] result = new int[height];
        for(int col=0; col<height; col++){
            result[col] = img.getRGB(row, col);
        }
        return result;
    }
}