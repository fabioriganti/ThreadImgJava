import java.util.concurrent.CountDownLatch;

public class ReadRGB extends Thread{
    private int[] rgb;
    private Integer bluemax;
    private CountDownLatch latch;

    public ReadRGB(int[] rgb, CountDownLatch latch, String name){
        super(name);
        this.rgb=rgb;
        this.latch = latch;
        bluemax = null;
    }

    public void run(){
        System.out.println(Thread.currentThread().getName() +" è iniziato.");
        bluemax = (rgb[0])&255;
        int tmp;
        for(int i=1; i<rgb.length; i++){
            tmp=(rgb[i])&255;
            if(tmp > bluemax) bluemax = tmp;
        }
        System.out.println(Thread.currentThread().getName() +" è finito.");
        latch.countDown();

    }

    public int getBluemax() {
        return bluemax;
    }
}