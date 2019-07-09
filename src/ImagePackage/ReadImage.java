package ImagePackage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

public class ReadImage {
    private BufferedImage img;
    private ReadRGB[] thread;
    private CountDownLatch latch;
    private Pixel[] sharedBuffer;

    public ReadImage(String str)  {
        try {
            URL url = getClass().getResource(str);
            File file = new File(url.getPath());
            img = ImageIO.read(file);
            int height = img.getHeight();
            sharedBuffer = new Pixel[height];
            latch = new CountDownLatch(height);
            thread = new ReadRGB[height];
            for(int i=0; i<height; i++){
                thread[i] = new ReadRGB(i, sharedBuffer, img, latch);
            }
        } catch (IOException e){
            System.err.println("Errore lettura immagine.");
            System.exit(2);
        }
    }

    public void maxBlue() {
        try {
            for (int i = 0; i < img.getHeight(); i++) {
                thread[i].start();
            }
            latch.await();
            System.out.println("Numero Thread totale: " + img.getHeight());

            Pixel max = sharedBuffer[0];
            int maxBlue;
            for (int i = 1; i < img.getHeight(); i++) {
                maxBlue = max.getBlue();
                if (maxBlue < sharedBuffer[i].getBlue())
                    max = sharedBuffer[i];
            }
            System.out.println("Il valore di blu massimo trovato nella immagine è "+max.getBlue()+" " +
                    "alla posizione ("+max.getRow()+", "+max.getColumn()+").");

        } catch (InterruptedException e) {
            System.err.println("Threading interrotto.");
            System.exit(1);
        }
    }
}