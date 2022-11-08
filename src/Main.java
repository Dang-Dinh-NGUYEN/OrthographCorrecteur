import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        CorrecteurOrthograph correcteurOrthograph = new CorrecteurOrthograph("C:\\Users\\Dang Dinh NGUYEN\\Documents\\L3_INFO\\Algo\\Orthograph\\src\\dico.txt");
        long begin = System.nanoTime();
        correcteurOrthograph.correctAll("C:\\Users\\Dang Dinh NGUYEN\\Documents\\L3_INFO\\Algo\\Orthograph\\src\\fautes.txt");
        long end = System.nanoTime();
        System.out.println("temps total à corrigé: " + (end - begin) + " nanoseconds = " + (end - begin)/1000000000.0 + " second");
    }
}