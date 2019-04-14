import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ReadImage {
    private BufferedImage img;
    private ReadRGB[] thread;
    private int maxblue;
    private CountDownLatch latch;

    public ReadImage(String file) throws IOException {
        img = ImageIO.read(new File(file));
        int width = img.getWidth();
        int height = img.getHeight();
        latch = new CountDownLatch(height);
        thread = new ReadRGB[height];
        for(int i=0; i<height; i++){
            thread[i] = new ReadRGB(GetRowRGB(i), latch, "Thread"+i);
        }
        try {
            maxblue = MaxBlue();
        } catch (InterruptedException e) {
            System.err.println("Errore threading.");
            System.exit(1);
        }
    }

    private int MaxBlue() throws InterruptedException {
        int max = 0;
        for(int i=0; i<img.getHeight(); i++){
            thread[i].start();
        }
        latch.await();

        max = thread[0].getBluemax();
        for(int i=1; i<img.getHeight(); i++){
            if(max < thread[i].getBluemax()) max=thread[i].getBluemax();
        }
        return max;
    }

    private int[] GetRowRGB(int row){
        int height = img.getHeight();
        int result[] = new int[height];
        for(int col=0; col<height; col++){
            result[col] = img.getRGB(col,row);
        }
        return result;
    }

    public int getMaxblue() {
        return maxblue;
    }
}