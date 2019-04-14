import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        try {
            ReadImage read = new ReadImage("C:\\Users\\fabio\\IdeaProjects\\ProvaThread\\src\\image.jpg");
            System.out.println("Il valore di blu massimo trovato è "+read.getMaxblue()+".");
        } catch (IOException e) {
            System.err.println("Errore lettura immagine.");
        }
    }
}
