import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

public class CorrecteurOrthograph {
    private static HashSet<String> dictionary = new HashSet<>();
    private static HashMap<String, HashSet<String>> trigramsOfEach = new HashMap<>();


    public CorrecteurOrthograph(String filename) throws IOException {
        File file = new File(filename);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null) {
            dictionary.add(st.toLowerCase(Locale.ROOT));
            st = "<" + st +">";
            for(int i = 0; i < st.length() - 2; i++) {
                if(!trigramsOfEach.containsKey(st)){
                    HashSet<String> trigram = new HashSet<>();
                    trigram.add(st.substring(i, i + 3));
                    trigramsOfEach.put(st,trigram);
                }else{
                    trigramsOfEach.get(st).add(st.substring(i, i + 3));
                }
            }
        }
        br.close();
    }

    public String correct(String word){
        if(dictionary.contains(word))return word;
        Trigram trigram = new Trigram(trigramsOfEach);
        ArrayList<String> mostCommons = trigram.mostCommons(word);
        return trigram.correct(word,mostCommons);
    }

    public void correctAll(String text) throws IOException {
        File file = new File(text);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null) {
            System.out.println(correct(st));
        }
        br.close();
    }
}
